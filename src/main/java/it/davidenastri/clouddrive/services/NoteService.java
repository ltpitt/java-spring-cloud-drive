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

    public int create(Note note, String username) {
        return noteMapper.create(new Note(null, note.getNotetitle(), note.getNotedescription(), userMapper.getUser(username).getUserId()));
    }

    public int update(Note note, String username) {
        return noteMapper.update(new Note(note.getNoteid(), note.getNotetitle(), note.getNotedescription(), userMapper.getUser(username).getUserId()));
    }

    public Note get(int noteid, String username) {
        return noteMapper.get(noteid, userMapper.getUser(username).getUserId());
    }

    public List<Note> getAll(String username) {
        return noteMapper.getAll(userMapper.getUser(username).getUserId());
    }

    public int delete(int noteid) {
        return noteMapper.delete(noteid);
    }

}
