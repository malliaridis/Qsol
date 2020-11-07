package model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Branch {

    @DocumentId
    private String id;
    private DocumentReference board;
    private String key;
    private String name;
    private String group;
    private int semesters;
    private Map<String, List<DocumentReference>> subjects;
    private Map<String, List<DocumentReference>> syllabuses;
    private Map<String, Long> papersCount;
}
