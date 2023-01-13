package at.ac.univie.se2_team_0308.viewmodels;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.models.ASubtask;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.SubtaskItem;
import at.ac.univie.se2_team_0308.models.SubtaskList;

public class SubtaskListAdapter extends RecyclerView.Adapter<SubtaskListAdapter.ViewHolder> {

    public static final String TAG = "SubtaskListAdapter";

    private List<ASubtask> tasks = new ArrayList<>();;
    private Context context;
    private boolean hasAnotherLevelOfSubtasks = true;

    public SubtaskListAdapter(Context context, List<ASubtask> tasks) {
        this.context = context;
        if(tasks != null){
            this.tasks = tasks;
        }
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
            // subtask row which has a list of other subtasks
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
                Log.d(TAG, "Subtask set new title: " + tasks.get(adapterPosition).getName() + " -> " + editable.toString());
                tasks.get(adapterPosition).setName(editable.toString());
            }
        });
        holder.checkBox.setChecked(tasks.get(position).getState() == EStatus.COMPLETED);

        holder.checkBox.setOnClickListener(view -> {
            ASubtask currentSubtask = tasks.get(holder.getAdapterPosition());
            Log.d(TAG, "Subtask CheckBox clicked: " + currentSubtask.getName() + " -> " + holder.checkBox.isChecked());
            EStatus state = holder.checkBox.isChecked() ? EStatus.COMPLETED : EStatus.NOT_STARTED;
            currentSubtask.setState(state);
        });

        holder.deleteButton.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            Log.d(TAG, "Remove subtask " + tasks.get(adapterPosition).getName());
            tasks.get(adapterPosition).removeAllSubtasks();
            tasks.remove(tasks.get(adapterPosition));
            notifyDataSetChanged();
        });

        if(hasAnotherLevelOfSubtasks){

            SubtaskListAdapter adapter = new SubtaskListAdapter(context, tasks.get(position).getSubtasks());
            // we don't want to have infinite subtask levels
            adapter.setHasAnotherLevelOfSubtasks(false);
            holder.subSubtasksRecView.setAdapter(adapter);
            holder.subSubtasksRecView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            // add button is displayed only for subtask which can have another level
            holder.addButton.setOnClickListener(view -> {
                ASubtask currentSubtask = tasks.get(holder.getAdapterPosition());
                Log.d(TAG, "Add subtask to " + currentSubtask.getName());
                currentSubtask.addSubtask(new SubtaskItem(""));
                notifyDataSetChanged();
            });
        }

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public List<ASubtask>  getTasks(){
        return this.tasks;
    }

    public void addTask(ASubtask task){
        this.tasks.add(task);
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
