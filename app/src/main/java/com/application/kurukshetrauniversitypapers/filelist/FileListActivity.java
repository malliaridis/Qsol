package com.application.kurukshetrauniversitypapers.filelist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.kurukshetrauniversitypapers.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.File;

public class FileListActivity extends AppCompatActivity {

    public static final String KEY_BOARD = "board";
    public static final String KEY_BRANCH = "branch";
    public static final String KEY_SEMESTER = "semester";
    public static final String KEY_SUBJECTS = "subjects";
    public static final String KEY_FILES = "files";

    private RecyclerView fileListRecyclerView;
    private FirebaseFirestore db;
    private FileListAdapter adapter;
    private List<File> files = new ArrayList<>();
    private FirebaseAuth mAuth;

    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filelist);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        fileListRecyclerView = findViewById(R.id.rv_file_list);

        adapter = new FileListAdapter(this, files);
        fileListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fileListRecyclerView.setAdapter(adapter);

        List<String> filePaths = getIntent().getStringArrayListExtra(KEY_FILES);
        if (filePaths == null) return;
        getFiles(filePaths);
    }

    private void getFiles(@NonNull List<String> filePaths) {
        for (String filePath : filePaths) {
            db.document(filePath).get().addOnSuccessListener(documentSnapshot -> {
                files.add(documentSnapshot.toObject(File.class));
                Collections.sort(files, (f1, f2) -> f1.getName().compareTo(f2.getName()));
                adapter.notifyDataSetChanged();
            });
        }
    }
}
