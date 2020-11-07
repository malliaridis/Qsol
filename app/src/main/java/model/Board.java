package model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;

import java.util.List;

import lombok.Data;

@Data
public class Board {

    @DocumentId
    private String id;
    private String key;
    private String name;
    private String url;
    private List<DocumentReference> branches;
    private long papersCount;
}
