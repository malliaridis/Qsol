package com.application.kurukshetrauniversitypapers.expandablelist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.kurukshetrauniversitypapers.R;
import com.application.kurukshetrauniversitypapers.subjectlist.SubjectListActivity;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.Branch;

import static com.application.kurukshetrauniversitypapers.filelist.FileListActivity.KEY_SUBJECTS;

public class ExpandableItemAdapter extends RecyclerView.Adapter<ExpandableItemAdapter.SemesterViewHolder> {

    private Context context;
    private Branch branch;

    public ExpandableItemAdapter(Context context, Branch branch) {
        this.context = context;
        this.branch = branch;
    }

    @NonNull
    @Override
    public SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_expanded_semester, parent, false);
        return new SemesterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterViewHolder holder, int position) {
        String semesterName = context.getString(R.string.semester) + " " + (position + 1);
        holder.titleTextView.setText(semesterName);
        holder.itemView.setOnClickListener(v -> {
            // TODO Handle click event
            Intent i = new Intent(context, SubjectListActivity.class);
            ArrayList<String> subjects = new ArrayList<>();
            List<DocumentReference> subjectRefs = branch.getSubjects().get(position + 1 + "");
            if (subjectRefs == null) return;
            for (DocumentReference ref : subjectRefs) {
                subjects.add(ref.getPath());
            }
            i.putExtra(KEY_SUBJECTS, subjects);
            context.startActivity(i);
        });
        holder.paperCountTextView.setText(String.format(Locale.getDefault(), "%d", this.branch.getPapersCount().get(position + 1 + "")));
    }

    @Override
    public int getItemCount() {
        return this.branch.getSemesters();
    }

    public static class SemesterViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView paperCountTextView;

        public SemesterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTextView = itemView.findViewById(R.id.tv_semester_name);
            this.paperCountTextView = itemView.findViewById(R.id.tv_paper_count);
        }
    }
}
