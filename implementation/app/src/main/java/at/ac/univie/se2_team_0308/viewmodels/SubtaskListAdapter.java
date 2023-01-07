package at.ac.univie.se2_team_0308.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.Subtask;

public class SubtaskListAdapter extends RecyclerView.Adapter<SubtaskListAdapter.ViewHolder> {

    public interface onSubtaskClickListener {
        void onDelete(Subtask taskModel);
        void onAdd(Subtask taskModel);
        void onSubtaskChecked(Subtask taskModel, boolean isChecked);
    }

    public static final String TAG = "";

    private List<Subtask> tasks;
    private Context context;
    private boolean hasAnotherLevelOfSubtasks = true;
    private onSubtaskClickListener onSubtaskClickListener;

    public SubtaskListAdapter(Context context, List<Subtask> tasks, onSubtaskClickListener onSubtaskClickListener) {
        this.context = context;
        this.tasks = tasks;
        this.onSubtaskClickListener = onSubtaskClickListener;
    }

    public void setHasAnotherLevelOfSubtasks(boolean value){
        this.hasAnotherLevelOfSubtasks = value;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(!hasAnotherLevelOfSubtasks){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_tree_row_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_row_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.subtaskTitle.setText(tasks.get(position).getName());
        holder.subtaskTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int adapterPosition = holder.getAdapterPosition();
                tasks.get(adapterPosition).setName(editable.toString());
            }
        });
        holder.checkBox.setChecked(tasks.get(position).getState() == EStatus.COMPLETED);

        holder.checkBox.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            EStatus state = holder.checkBox.isChecked() ? EStatus.COMPLETED : EStatus.NOT_STARTED;
            tasks.get(adapterPosition).setState(state);
            onSubtaskClickListener.onSubtaskChecked(tasks.get(holder.getAdapterPosition()), holder.checkBox.isChecked());
        });

        holder.deleteButton.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            onSubtaskClickListener.onDelete(tasks.get(adapterPosition));
            tasks.get(adapterPosition).removeAllSubtasks();
            removeTask(tasks.get(adapterPosition));
        });

        if(hasAnotherLevelOfSubtasks){
            // add button is displayed only for subtask which can have another level


            SubtaskListAdapter adapter = new SubtaskListAdapter(context, tasks.get(position).getSubtasks(), new SubtaskListAdapter.onSubtaskClickListener(){
                @Override
                public void onDelete(Subtask taskModel) {
                    System.out.print("subtask DELETE request");
                }
                @Override
                public void onAdd(Subtask taskModel) {
                    return;
                } //
                @Override
                public void onSubtaskChecked(Subtask taskModel, boolean isChecked) {
                    System.out.print("subtask CHECKED request: " + isChecked);
                }
            });
            // we don't want to have infinite subtask levels
            adapter.setHasAnotherLevelOfSubtasks(false);
            holder.subSubtasksRecView.setAdapter(adapter);
            holder.subSubtasksRecView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

            holder.addButton.setOnClickListener(view -> {
                int adapterPosition = holder.getAdapterPosition();
                onSubtaskClickListener.onAdd(tasks.get(adapterPosition));
                tasks.get(adapterPosition).addSubtask(new Subtask(""));
                notifyDataSetChanged();
            });
        }

    }

    @Override
    public int getItemCount() {
        if (tasks == null) {
            return 0;
        }
        return tasks.size();
    }

    public List<Subtask>  getTasks(){
        return this.tasks;
    }
    public void setTasks(List<Subtask> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void addTask(Subtask task){
        this.tasks.add(task);
        notifyDataSetChanged();
    }

    public void removeTask(Subtask task){
        this.tasks.remove(task);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private EditText subtaskTitle;
        private ImageButton addButton;
        private ImageButton deleteButton;
        private RecyclerView subSubtasksRecView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.subtaskCheckBox);
            subtaskTitle = itemView.findViewById(R.id.subtaskEditText);
            addButton = itemView.findViewById(R.id.subtaskAddBtn);
            deleteButton = itemView.findViewById(R.id.subtaskDeleteBtn);
            subSubtasksRecView = itemView.findViewById(R.id.subtaskDeeperRecView);
        }
    }
}
