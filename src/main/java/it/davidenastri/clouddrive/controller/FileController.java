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

    private final CredentialService credentialService;
    private final NoteService noteService;
    private final FileService fileService;
    Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileController(CredentialService credentialService, NoteService noteService, FileService fileService) {

        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;

    }

    @PostMapping("/files/create")
    public String createOrUpdate(MultipartFile fileUpload, Principal principal, Model model) throws IOException {

        String createOrUpdateError = null;

        if (fileUpload.isEmpty()) {
            createOrUpdateError = "Please choose a file to upload fist.";
        }

        boolean isFileNameExisting = fileService.getAll(principal.getName()).stream().anyMatch(file -> file.getFilename().equals(fileUpload.getOriginalFilename()));

        if (isFileNameExisting) {
            createOrUpdateError = "File " + fileUpload.getOriginalFilename() + " already exists. Please delete the previous version or upload a different file.";
        }

        if (createOrUpdateError == null) {
            fileService.create(fileUpload, principal.getName());
            model.addAttribute("fileSuccess", true);
        } else {
            model.addAttribute("fileError", createOrUpdateError);
        }

        model.addAttribute("files", fileService.getAll(principal.getName()));
        model.addAttribute("notes", noteService.getAll(principal.getName()));
        model.addAttribute("credentials", credentialService.getAll(principal.getName()));
        model.addAttribute("activeTab", "files");

        return "home";
    }

    @GetMapping("/files/delete")
    public String delete(@RequestParam("id") int fileid, Model model, Principal principal) {

        if (fileid > 0) {
            fileService.delete(fileid);
        }

        model.addAttribute("files", fileService.getAll(principal.getName()));
        model.addAttribute("notes", noteService.getAll(principal.getName()));
        model.addAttribute("credentials", credentialService.getAll(principal.getName()));
        model.addAttribute("activeTab", "files");

        return "home";

    }

    @GetMapping("/files/get")
    public void get(@RequestParam("id") int fileid, Model model, Principal principal, HttpServletResponse response) {

        if (fileid > 0) {
            File file = fileService.get(fileid, principal.getName());
            try {
                response.setHeader("Content-Disposition", "inline;filename=\"" + file.getFilename() + "\"");
                OutputStream out = response.getOutputStream();
                response.setContentType(file.getContenttype());
                InputStream in = new ByteArrayInputStream(file.getFiledata());
                IOUtils.copy(in, out);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
