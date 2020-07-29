package it.davidenastri.clouddrive.controller;

import it.davidenastri.clouddrive.services.CredentialService;
import it.davidenastri.clouddrive.services.FileService;
import it.davidenastri.clouddrive.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final CredentialService credentialService;
    private final NoteService noteService;
    private final FileService fileService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String loginView(Model model, Principal principal) {
        model.addAttribute("files", fileService.getAll(principal.getName()));
        model.addAttribute("notes", noteService.getAll(principal.getName()));
        model.addAttribute("credentials", credentialService.getAll(principal.getName()));
        model.addAttribute("activeTab", "files");
        return "home";
    }

}