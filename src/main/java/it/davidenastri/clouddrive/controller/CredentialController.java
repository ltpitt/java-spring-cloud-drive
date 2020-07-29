package it.davidenastri.clouddrive.controller;

import it.davidenastri.clouddrive.model.Credential;
import it.davidenastri.clouddrive.services.CredentialService;
import it.davidenastri.clouddrive.services.FileService;
import it.davidenastri.clouddrive.services.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class CredentialController {

    Logger logger = LoggerFactory.getLogger(CredentialController.class);

    private final CredentialService credentialService;
    private final NoteService noteService;
    private final FileService fileService;

    public CredentialController(CredentialService credentialService, NoteService noteService, FileService fileService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @PostMapping("/credential")
    public String createOrUpdate(Credential credential, Principal principal, Model model) {

        String createOrUpdateError = null;
        String createOrUpdateSuccess = null;

        Optional<Integer> credentialidOptional = Optional.ofNullable(credential.getCredentialid());

        if (credentialidOptional.isPresent()) {
            int rowsEdited = credentialService.update(credential, principal.getName());
            if (rowsEdited < 0) {
                createOrUpdateError = "There was an error editing your credential. Please try again.";
            } else {
                createOrUpdateSuccess = "Credential edited successfully.";
            }
        } else {
            int rowsAdded = credentialService.create(credential, principal.getName());
            if (rowsAdded < 0) {
                createOrUpdateError = "There was an error creating your credential. Please try again.";
            } else {
                createOrUpdateSuccess = "Credential created successfully.";
            }
        }
        if (createOrUpdateError == null) {
            model.addAttribute("credentialSuccess", createOrUpdateSuccess);
        } else {
            model.addAttribute("credentialError", createOrUpdateError);
        }
        model = refreshModelAttributes(model, principal);
        model.addAttribute("activeTab", "credential");
        return "home";
    }

    @GetMapping("/credential/delete")
    public String deleteNote(@RequestParam("id") int credentialid, Model model, Principal principal) {
        int rowsDeleted = credentialService.delete(credentialid);
        if (rowsDeleted > 0) {
            model.addAttribute("credentialSuccess", "Credential deleted successfully.");
        } else {
            model.addAttribute("credentialError", "There was an error deleting your credential. Please try again.");
        }
        model = refreshModelAttributes(model, principal);
        model.addAttribute("activeTab", "credential");
        return "home";
    }

    @GetMapping("/credential/decrypt")
    @ResponseBody
    public Map<String, String> decryptPassword(@RequestParam("id") int credentialid, Principal principal) {
        Map<String, String> response = new HashMap<>();
        if (credentialid > 0) {
            response.put("decryptedPassword", credentialService.decryptPassword(credentialService.get(credentialid, principal.getName())).getPassword());
        }
        return response;
    }

    public Model refreshModelAttributes(Model model, Principal principal) {
        model.addAttribute("files", fileService.getAll(principal.getName()));
        model.addAttribute("notes", noteService.getAll(principal.getName()));
        model.addAttribute("credentials", credentialService.getAll(principal.getName()));
        return model;
    }

}
