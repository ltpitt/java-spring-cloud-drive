package it.davidenastri.clouddrive.controller;

import it.davidenastri.clouddrive.model.Note;
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
public class NoteController {

    Logger logger = LoggerFactory.getLogger(NoteController.class);

    private final CredentialService credentialService;
    private final NoteService noteService;
    private final FileService fileService;

    public NoteController(CredentialService credentialService, NoteService noteService, FileService fileService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @PostMapping("/notes")
    public String createOrUpdate(Note note, Principal principal, Model model) {

        String createOrUpdateError = null;
        String createOrUpdateSuccess = null;

        Optional<Integer> noteidOptional = Optional.ofNullable(note.getNoteid());

        if (noteidOptional.isPresent()) {
            noteService.update(note, principal.getName());
            int rowsEdited = noteService.update(note, principal.getName());
            if (rowsEdited < 0) {
                createOrUpdateError = "There was an error editing your note. Please try again.";
            } else {
                createOrUpdateSuccess = "Note edited successfully.";
            }
        } else {
            int rowsAdded = noteService.create(note, principal.getName());
            if (rowsAdded < 0) {
                createOrUpdateError = "There was an error creating your note. Please try again.";
            } else {
                createOrUpdateSuccess = "Note created successfully.";
            }
        }
        if (createOrUpdateError == null) {
            model.addAttribute("noteSuccess", createOrUpdateSuccess);
        } else {
            model.addAttribute("noteError", createOrUpdateError);
        }
        model = refreshModelAttributes(model, principal);
        model.addAttribute("activeTab", "notes");
        return "home";
    }

    @GetMapping("/notes/delete")
    public String delete(@RequestParam("id") int noteid, Model model, Principal principal) {
        int rowsDeleted = noteService.delete(noteid);
        if (rowsDeleted > 0) {
            model.addAttribute("noteSuccess", "Note deleted successfully.");
        } else {
            model.addAttribute("noteError", "There was an error deleting your note. Please try again.");
        }
        model = refreshModelAttributes(model, principal);
        model.addAttribute("activeTab", "notes");
        return "home";
    }

    public Model refreshModelAttributes(Model model, Principal principal) {
        model.addAttribute("files", fileService.getAll(principal.getName()));
        model.addAttribute("notes", noteService.getAll(principal.getName()));
        model.addAttribute("credentials", credentialService.getAll(principal.getName()));
        return model;
    }

}
