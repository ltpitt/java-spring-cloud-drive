package it.davidenastri.clouddrive.controller;

import it.davidenastri.clouddrive.model.Note;
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
public class NoteController {

    private final NoteService noteService;
    Logger logger = LoggerFactory.getLogger(NoteController.class);

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/notes")
    public String createOrUpdateNote(Note note, Principal principal, Model model) {

        String createNoteError = null;

        Optional<Integer> noteidOptional = Optional.ofNullable(note.getNoteid());

        if (noteidOptional.isPresent()) {
            noteService.updateNote(note, principal.getName());
            int rowsEdited = noteService.updateNote(note, principal.getName());
            if (rowsEdited < 0) {
                createNoteError = "There was an error editing your note. Please try again.";
            }
        } else {
            int rowsAdded = noteService.createNote(note, principal.getName());
            if (rowsAdded < 0) {
                createNoteError = "There was an error creating your note. Please try again.";
            }
        }
        if (createNoteError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", createNoteError);
        }
        model.addAttribute("notes", noteService.getAllNotes(principal.getName()));
        model.addAttribute("activeTab", "notes");
        return "home";
    }

    @GetMapping("/notes/delete")
    public String deleteNote(@RequestParam("id") int noteid, Model model, Principal principal) {
        if (noteid > 0) {
            noteService.deleteNote(noteid);
        }
        model.addAttribute("notes", noteService.getAllNotes(principal.getName()));
        model.addAttribute("activeTab", "notes");
        return "home";
    }


}
