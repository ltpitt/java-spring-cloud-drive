package it.davidenastri.clouddrive.controller;

import it.davidenastri.clouddrive.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {

    public final NoteService noteService;

    public HomeController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping()
    public String loginView(Model model, Principal principal) {
        model.addAttribute("notes", this.noteService.getAllNotes(principal.getName()));
        return "home";
    }

}