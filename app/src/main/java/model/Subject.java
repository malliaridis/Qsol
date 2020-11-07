package model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

import lombok.Data;

@Data
public class Subject {

    @DocumentId
    private String id;
    private String name;
    private List<DocumentReference> papers;
}
