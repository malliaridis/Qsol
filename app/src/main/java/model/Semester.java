package model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;

import lombok.Data;

@Data
public class Semester {

    @DocumentId
    private String id;
    private String key;
    private DocumentReference board;
    private DocumentReference branch;
    private long subjectsCount;
    private long papersCount;
    private long syllabusesCount;
}
