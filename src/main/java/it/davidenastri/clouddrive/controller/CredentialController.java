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

import java.security.Principal;
import java.util.Optional;

@Controller
public class CredentialController {

    private final CredentialService credentialService;
    private final NoteService noteService;
    private final FileService fileService;
    Logger logger = LoggerFactory.getLogger(CredentialController.class);

    public CredentialController(CredentialService credentialService, NoteService noteService, FileService fileService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @PostMapping("/credential")
    public String createOrUpdate(Credential credential, Principal principal, Model model) {

        String createOrUpdateError = null;

        Optional<Integer> credentialidOptional = Optional.ofNullable(credential.getCredentialid());

        if (credentialidOptional.isPresent()) {
            logger.info("Credential already existing");
            credentialService.update(credential, principal.getName());
            int rowsEdited = credentialService.update(credential, principal.getName());
            if (rowsEdited < 0) {
                createOrUpdateError = "There was an error editing your credential. Please try again.";
            }
        } else {
            int rowsAdded = credentialService.create(credential, principal.getName());
            if (rowsAdded < 0) {
                createOrUpdateError = "There was an error creating your credential. Please try again.";
            }
        }
        if (createOrUpdateError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", createOrUpdateError);
        }
        model.addAttribute("files", fileService.getAll(principal.getName()));
        model.addAttribute("notes", noteService.getAll(principal.getName()));
        model.addAttribute("credentials", credentialService.getAll(principal.getName()));
        model.addAttribute("activeTab", "credential");
        return "home";
    }

    @GetMapping("/credential/delete")
    public String deleteNote(@RequestParam("id") int credentialid, Model model, Principal principal) {
        if (credentialid > 0) {
            credentialService.delete(credentialid);
        }
        model.addAttribute("files", fileService.getAll(principal.getName()));
        model.addAttribute("notes", noteService.getAll(principal.getName()));
        model.addAttribute("credentials", credentialService.getAll(principal.getName()));
        model.addAttribute("activeTab", "credential");
        return "home";
    }


}
