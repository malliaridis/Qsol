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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Subject;

import static com.application.kurukshetrauniversitypapers.filelist.FileListActivity.KEY_FILES;
import static com.application.kurukshetrauniversitypapers.filelist.FileListActivity.KEY_SUBJECTS;

public class SubjectListActivity extends AppCompatActivity implements SubjectListAdapter.OnSubjectItemClickListener {

    public static final String KEY_TITLE = "key";
    List<String> subjectPaths;
    List<Subject> subjectList;
    SubjectListAdapter adapter;
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

        subjectPaths = intent.getStringArrayListExtra(KEY_SUBJECTS);
    }

    private void getSubjects() {
        // TODO Replace strings of collections with constants
        for (String path : subjectPaths) {
            db.document(path).get().addOnSuccessListener(documentSnapshot -> {
                subjectList.add(documentSnapshot.toObject(Subject.class));
                Collections.sort(subjectList, (s1, s2) -> s1.getName().compareTo(s2.getName()));
                adapter.notifyDataSetChanged();
            });
        }
    }

    @Override
    public void onClick(Subject subject) {
        ArrayList<String> filePaths = new ArrayList<>();
        for (DocumentReference ref : subject.getPapers()) {
            filePaths.add(ref.getPath());
        }
        Intent intent = new Intent(SubjectListActivity.this, FileListActivity.class);
        intent.putExtra(KEY_FILES, filePaths);
        startActivity(intent);
    }
}
