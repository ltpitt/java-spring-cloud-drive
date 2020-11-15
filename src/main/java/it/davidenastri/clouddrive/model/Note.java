package it.davidenastri.clouddrive.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Note {

    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private Integer userid;

    public Note() {
    }

    public Note(Integer noteid, String notetitle, String notedescription, Integer userid) {
        this.noteid = noteid;
        this.notetitle = notetitle;
        this.notedescription = notedescription;
        this.userid = userid;
    }

}