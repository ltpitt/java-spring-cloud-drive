package it.davidenastri.clouddrive.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class File {

    private Integer fileid;
    private String filename;
    private String contenttype;
    private String filesize;
    private Integer userid;
    private byte[] filedata;

    public File() {
    }

    public File(Integer fileid, String filename, String contenttype, String filesize, Integer userid, byte[] filedata) {
        this.fileid = fileid;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

}