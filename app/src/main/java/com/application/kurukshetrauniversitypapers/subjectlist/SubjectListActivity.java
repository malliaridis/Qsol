package com.application.kurukshetrauniversitypapers.subjectlist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.kurukshetrauniversitypapers.R;
import com.application.kurukshetrauniversitypapers.filelist.FileListActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Subject;

import static com.application.kurukshetrauniversitypapers.filelist.FileListActivity.KEY_SEMESTER;
import static com.application.kurukshetrauniversitypapers.filelist.FileListActivity.KEY_SUBJECT;

public class SubjectListActivity extends AppCompatActivity implements SubjectListAdapter.OnSubjectItemClickListener {

    public static final String KEY_TITLE = "key";
    List<Subject> subjectList;
    SubjectListAdapter adapter;
    private String semesterId;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        db = FirebaseFirestore.getInstance();

        loadMetaData();

        RecyclerView subjectsRecyclerView = findViewById(R.id.rv_subject_list);

        subjectList = new ArrayList<>();
        adapter = new SubjectListAdapter(this, subjectList, this);
        subjectsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subjectsRecyclerView.setAdapter(adapter);

        getSubjects();
    }

    private void loadMetaData() {
        Intent intent = getIntent();
        String key = intent.getStringExtra(KEY_TITLE);
        ((TextView) findViewById(R.id.tv_title)).setText(key);

        semesterId = intent.getStringExtra(KEY_SEMESTER);
    }

    private void getSubjects() {
        // TODO Replace strings of collections with constants
        DocumentReference semesterRef = db.collection("semesters").document(semesterId);
        db.collection("subjects")
                .whereEqualTo("semester", semesterRef)
                .whereGreaterThan("papersCount", 0).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot snapshots) {
                        subjectList.addAll(snapshots.toObjects(Subject.class));
                        Collections.sort(subjectList, (s1, s2) -> s1.getName().compareTo(s2.getName()));
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onClick(Subject subject) {
        Intent intent = new Intent(SubjectListActivity.this, FileListActivity.class);
        intent.putExtra(KEY_SUBJECT, subject.getId());
        startActivity(intent);
    }
}
