package at.ac.univie.se2_team_0308.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.views.TaskActivity;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    public interface onSelectItemListener {
        void onItemSelected(ATask taskModel);
    }

    public static final String TAG = "";
    public static final String TASK_ITEM_KEY = "clicked_task";
    public static final String TASK_ITEM_CATEGORY = "clicked_task_category";

    private List<ATask> tasks = new ArrayList<ATask>();
    private boolean selectModeOn = false;
    private Context context;
    private onSelectItemListener onSelectItemListener;

    public TaskListAdapter(Context context, List<ATask> tasks, onSelectItemListener onSelectItemListener) {
        this.context = context;
        this.tasks = tasks;
        this.onSelectItemListener = onSelectItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.taskTitle.setText(tasks.get(position).getTaskName());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskActivity.class);
                int adapterPosition = holder.getAdapterPosition();
                if (tasks.get(adapterPosition).getCategory() == ECategory.APPOINTMENT) {
                    Bundle extras = new Bundle();
                    extras.putString(TASK_ITEM_CATEGORY, String.valueOf(ECategory.APPOINTMENT));
                    extras.putParcelable(TASK_ITEM_KEY, (TaskAppointment)tasks.get(adapterPosition));
                    intent.putExtras(extras);
                }
                if (tasks.get(adapterPosition).getCategory() == ECategory.CHECKLIST) {
                    Bundle extras = new Bundle();
                    extras.putString(TASK_ITEM_CATEGORY, String.valueOf(ECategory.CHECKLIST));
                    extras.putParcelable(TASK_ITEM_KEY, (TaskChecklist)tasks.get(adapterPosition));
                    intent.putExtras(extras);
                }
                context.startActivity(intent);
            }
        });

        switch (tasks.get(position).getPriority().toString()) {
            case "MEDIUM":
                holder.lowPriorityRelLayout.setVisibility(View.GONE);
                holder.mediumPriorityRelLayout.setVisibility(View.VISIBLE);
                holder.highPriorityRelLayout.setVisibility(View.GONE);
                break;
            case "HIGH":
                holder.lowPriorityRelLayout.setVisibility(View.GONE);
                holder.mediumPriorityRelLayout.setVisibility(View.GONE);
                holder.highPriorityRelLayout.setVisibility(View.VISIBLE);
                break;
            default:
                holder.lowPriorityRelLayout.setVisibility(View.VISIBLE);
                holder.mediumPriorityRelLayout.setVisibility(View.GONE);
                holder.highPriorityRelLayout.setVisibility(View.GONE);
                break;
        }

        switch (tasks.get(position).getStatus().toString()) {
            case "IN_PROGRESS":
                holder.taskImageProgressNotStarted.setVisibility(View.GONE);
                holder.taskImageProgressInProgress.setVisibility(View.VISIBLE);
                holder.taskImageProgressCompleted.setVisibility(View.GONE);
                break;
            case "COMPLETED":
                holder.taskImageProgressNotStarted.setVisibility(View.GONE);
                holder.taskImageProgressInProgress.setVisibility(View.GONE);
                holder.taskImageProgressCompleted.setVisibility(View.VISIBLE);
                break;
            default:
                holder.taskImageProgressNotStarted.setVisibility(View.VISIBLE);
                holder.taskImageProgressInProgress.setVisibility(View.GONE);
                holder.taskImageProgressCompleted.setVisibility(View.GONE);
                break;
        }

        if (this.selectModeOn) {
            holder.buttonSelect.setVisibility(View.VISIBLE);
            holder.buttonSelect.setChecked(false);
            holder.buttonSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tasks.get(holder.getAdapterPosition()).isSelected()) {
                        Log.d(TAG, "onItemSelected: item is deselected");
                        tasks.get(holder.getAdapterPosition()).setSelected(false);
                        holder.buttonSelect.setChecked(false);
                    } else {
                        Log.d(TAG, "onItemSelected: item is selected");
                        tasks.get(holder.getAdapterPosition()).setSelected(true);
                        holder.buttonSelect.setChecked(true);
                    }
                    onSelectItemListener.onItemSelected(tasks.get(holder.getAdapterPosition()));
                }
            });
        } else {
            holder.buttonSelect.setChecked(false);
            tasks.get(holder.getAdapterPosition()).setSelected(false);
            holder.buttonSelect.setVisibility(View.GONE);
        }

        if (tasks.get(holder.getAdapterPosition()).getCategory() == ECategory.APPOINTMENT) {
            holder.taskTypeImageAppointment.setVisibility(View.VISIBLE);
            holder.taskTypeImageChecklist.setVisibility(View.GONE);
        }
        if (tasks.get(holder.getAdapterPosition()).getCategory() == ECategory.CHECKLIST) {
            holder.taskTypeImageChecklist.setVisibility(View.VISIBLE);
            holder.taskTypeImageAppointment.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (tasks == null) {
            return 0;
        }
        return tasks.size();
    }

    public void setTasks(List<ATask> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void setSelectModeOn(boolean mode) {
        this.selectModeOn = mode;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RadioButton buttonSelect;
        private TextView taskTitle;
        private ImageView taskTypeImageAppointment;
        private ImageView taskTypeImageChecklist;
        private ImageView dragButton;
        private ImageView taskImageProgressNotStarted;
        private ImageView taskImageProgressInProgress;
        private ImageView taskImageProgressCompleted;
        private MaterialCardView parent;
        private RelativeLayout lowPriorityRelLayout;
        private RelativeLayout mediumPriorityRelLayout;
        private RelativeLayout highPriorityRelLayout;
       // private TextView subtasksList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonSelect = itemView.findViewById(R.id.radioButtonSelect);
            taskTitle = itemView.findViewById(R.id.taskNameText);
            taskTypeImageAppointment = itemView.findViewById(R.id.taskTypeImageAppointment);
            taskTypeImageChecklist = itemView.findViewById(R.id.taskTypeImageChecklist);
            dragButton = itemView.findViewById(R.id.taskDragButton);
            taskImageProgressNotStarted = itemView.findViewById(R.id.taskImageProgressNotStarted);
            taskImageProgressInProgress = itemView.findViewById(R.id.taskImageProgressInProgress);
            taskImageProgressCompleted = itemView.findViewById(R.id.taskImageProgressCompleted);
            parent = itemView.findViewById(R.id.parent_card);
            lowPriorityRelLayout = itemView.findViewById(R.id.relLayoutLowPriority);
            mediumPriorityRelLayout = itemView.findViewById(R.id.relLayoutMediumPriority);
            highPriorityRelLayout = itemView.findViewById(R.id.relLayoutHighPriority);
           // subtasksList = itemView.findViewById(R.id.subtasksList);
        }
    }
}
