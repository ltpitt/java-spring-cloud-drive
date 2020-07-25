package it.davidenastri.clouddrive.mapper;

import it.davidenastri.clouddrive.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int create(File file);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid} AND userid = #{userid}")
    File get(Integer fileid, Integer userid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getAll(Integer userid);

    @Update("UPDATE FILES SET filename = #{filename}, contenttype = #{contenttype}, filesize = #{filesize}, userid = #{userid}, filedata = #{filedata} WHERE fileid = #{fileid}")
    int update(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    int delete(Integer fileid);

}