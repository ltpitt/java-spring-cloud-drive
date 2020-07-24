package it.davidenastri.clouddrive.services;

import it.davidenastri.clouddrive.mapper.NoteMapper;
import it.davidenastri.clouddrive.mapper.UserMapper;
import it.davidenastri.clouddrive.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final UserMapper userMapper;
    private final NoteMapper noteMapper;

    public NoteService(UserMapper userMapper, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note, String username) {
        return noteMapper.insert(new Note(null, note.getNotetitle(), note.getNotedescription(), userMapper.getUser(username).getUserId()));
    }

    public int updateNote(Note note, String username) {
        return noteMapper.update(new Note(note.getNoteid(), note.getNotetitle(), note.getNotedescription(), userMapper.getUser(username).getUserId()));
    }

    public Note getNote(int noteid, String username) {
        return noteMapper.getNote(noteid, userMapper.getUser(username).getUserId());
    }

    public List<Note> getAllNotes(String username) {
        return noteMapper.getAllNotes(userMapper.getUser(username).getUserId());
    }

    public int deleteNote(int noteid) {
        return noteMapper.deleteNote(noteid);
    }

}
