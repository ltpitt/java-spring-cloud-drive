package it.davidenastri.clouddrive.interceptor;

import it.davidenastri.clouddrive.services.CredentialService;
import it.davidenastri.clouddrive.services.FileService;
import it.davidenastri.clouddrive.services.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@ControllerAdvice
public class ControllerAdvisor {

    Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    private final CredentialService credentialService;
    private final NoteService noteService;
    private final FileService fileService;

    public ControllerAdvisor(CredentialService credentialService, NoteService noteService, FileService fileService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException exc, HttpServletRequest request, HttpServletResponse response, Principal principal) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        mav.addObject("fileError", "File too large! Maximum size allowed is 10Mb.");
        mav.addObject("activeTab", "files");
        mav.addObject("files", fileService.getAll(principal.getName()));
        mav.addObject("notes", noteService.getAll(principal.getName()));
        mav.addObject("credentials", credentialService.getAll(principal.getName()));
        return mav;
    }

}