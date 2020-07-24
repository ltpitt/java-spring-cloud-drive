package it.davidenastri.clouddrive.model;

import java.sql.Blob;


public class File {

    private Integer fileId;
    private String filename;
    private String contenttype;
    private String filesyze;
    private Integer userid;
    private Blob filedata;

    public File(Integer fileId, String filename, String contenttype, String filesyze, Integer userid, Blob filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesyze = filesyze;
        this.userid = userid;
        this.filedata = filedata;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesyze() {
        return filesyze;
    }

    public void setFilesyze(String filesyze) {
        this.filesyze = filesyze;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Blob getFiledata() {
        return filedata;
    }

    public void setFiledata(Blob filedata) {
        this.filedata = filedata;
    }

}