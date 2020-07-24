package it.davidenastri.clouddrive.mapper;

import it.davidenastri.clouddrive.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getAllNotes(Integer userid);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid} AND userid = #{userid}")
    Note getNote(Integer noteid, Integer userid);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int deleteNote(Integer noteid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription}, userid = #{userid} WHERE noteid = #{noteid}")
    int update(Note note);

}