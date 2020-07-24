package it.davidenastri.clouddrive.mapper;

import it.davidenastri.clouddrive.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int create(Note note);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid} AND userid = #{userid}")
    Note get(Integer noteid, Integer userid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getAll(Integer userid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription}, userid = #{userid} WHERE noteid = #{noteid}")
    int update(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int delete(Integer noteid);

}