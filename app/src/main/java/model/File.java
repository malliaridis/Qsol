package model;

import com.google.firebase.firestore.DocumentId;

import lombok.Data;

@Data
public class File {

    @DocumentId
    private String id;
    private String name;
    private String fileName;
    private String code;
    private String url;
    private String year;
    private boolean isSyllabus;
}
