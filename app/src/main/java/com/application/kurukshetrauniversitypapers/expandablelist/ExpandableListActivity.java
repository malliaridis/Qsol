package com.application.kurukshetrauniversitypapers.expandablelist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.kurukshetrauniversitypapers.MainActivity;
import com.application.kurukshetrauniversitypapers.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import model.Branch;

public class ExpandableListActivity extends AppCompatActivity {

    private static final String TAG = "ExpandableListActivity";

    private TextView refreshTextView;
    private RecyclerView expandableRecyclerView;
    private ExpandableListAdapter adapter;
    private List<Branch> branches = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list);

        Log.d(TAG, "onCreate: Started");

        refreshTextView = findViewById(R.id.tv_refresh);
        expandableRecyclerView = findViewById(R.id.rv_expandable_list);

        db = FirebaseFirestore.getInstance();
        // TODO Replace string extra key with constant
        String group = getIntent().getStringExtra("reference");
        getBranchesByGroup(group);

        adapter = new ExpandableListAdapter(this, branches);
        expandableRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        expandableRecyclerView.setAdapter(adapter);

        // TODO See what to do with the refresh
//        refreshTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ExpandableListActivity.this, Expendable_loader.class);
//                intent.putExtra("reference", "btech");
//                startActivity(intent);
//            }
//        });
    }

    /**
     * @param group the group of the branches to show
     */
    private void getBranchesByGroup(String group) {
        if (group == null) return;

        // TODO Replace strings of collections with constants
        db.collection("branches").whereEqualTo("group", group).get()
                .addOnSuccessListener(snapshots -> {
                    List<Branch> dbBranches = snapshots.toObjects(Branch.class);
                    for (Branch branch : dbBranches) {
                        if (branch != null) {
                            branches.add(branch);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }).addOnFailureListener(e -> Log.e(TAG, "onFailure: failed.", e));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("run counter", "no");
        intent.putExtra("EXIT", true);
        startActivity(intent);
        finish();
    }
}
