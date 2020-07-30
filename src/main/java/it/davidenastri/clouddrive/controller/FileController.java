package it.davidenastri.clouddrive.controller;

import it.davidenastri.clouddrive.model.File;
import it.davidenastri.clouddrive.services.CredentialService;
import it.davidenastri.clouddrive.services.FileService;
import it.davidenastri.clouddrive.services.NoteService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;

@Controller
public class FileController {

    Logger logger = LoggerFactory.getLogger(FileController.class);

    private final CredentialService credentialService;
    private final NoteService noteService;
    private final FileService fileService;

    public FileController(CredentialService credentialService, NoteService noteService, FileService fileService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @PostMapping("/files/create")
    public String createOrUpdate(MultipartFile fileUpload, Principal principal, Model model) throws IOException {

        String createOrUpdateError = null;
        String createOrUpdateSuccess = null;

        if (fileUpload.getSize() > 10485760) {
            createOrUpdateError = "File exceeds the maximum upload size of 10485760 (10 Mb). Please try with a smaller file.";
        }

        if (fileUpload.isEmpty()) {
            createOrUpdateError = "Please choose a file to upload first.";
        }

        boolean isFileNameExisting = fileService.getAll(principal.getName()).stream().anyMatch(file -> file.getFilename().equals(fileUpload.getOriginalFilename()));

        if (isFileNameExisting) {
            createOrUpdateError = "File " + fileUpload.getOriginalFilename() + " already exists. Please delete it or upload a different file.";
        }

        if (createOrUpdateError == null) {
            fileService.create(fileUpload, principal.getName());
            model.addAttribute("fileSuccess", "File uploaded successfully.");
        } else {
            model.addAttribute("fileError", createOrUpdateError);
        }

        model = refreshModelAttributes(model, principal);
        model.addAttribute("activeTab", "files");

        return "home";
    }

    @GetMapping("/files/delete")
    public String delete(@RequestParam("id") int fileid, Model model, Principal principal) {
        int rowsDeleted = fileService.delete(fileid);
        if (rowsDeleted > 0) {
            model.addAttribute("fileSuccess", "File deleted successfully.");
        } else {
            model.addAttribute("fileError", "There was an error deleting your file. Please try again.");
        }
        model = refreshModelAttributes(model, principal);
        model.addAttribute("activeTab", "files");
        return "home";
    }

    @GetMapping("/files/get")
    public String get(@RequestParam("id") int fileid, Model model, Principal principal, HttpServletResponse response) {
        if (fileid > 0) {
            File file = fileService.get(fileid, principal.getName());
            try {
                response.setHeader("Content-Disposition", "inline;filename=\"" + file.getFilename() + "\"");
                OutputStream out = response.getOutputStream();
                response.setContentType("application/octet-stream");
                InputStream in = new ByteArrayInputStream(file.getFiledata());
                IOUtils.copy(in, out);
                out.flush();
                out.close();
                model.addAttribute("fileSuccess", "File downloaded successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("fileError", "There was an error downloading your file. Please try again.");
            }
        }
        model = refreshModelAttributes(model, principal);
        model.addAttribute("activeTab", "files");
        return "home";
    }

    public Model refreshModelAttributes(Model model, Principal principal) {
        model.addAttribute("files", fileService.getAll(principal.getName()));
        model.addAttribute("notes", noteService.getAll(principal.getName()));
        model.addAttribute("credentials", credentialService.getAll(principal.getName()));
        return model;
    }

}
