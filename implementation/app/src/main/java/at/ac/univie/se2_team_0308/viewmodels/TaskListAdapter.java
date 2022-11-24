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

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    public static final String TAG = "";
    public static final String TASK_ITEM_KEY = "clicked_task";
    public static final String TASK_ITEM_CATEGORY = "clicked_task_category";

    private List<ATask> tasks = new ArrayList<ATask>();
    private Context context;

    public TaskListAdapter(Context context, List<ATask> tasks) {
        this.context = context;
        this.tasks = tasks;
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
                context.startActivity(intent);
            }
        });

        if (tasks.get(holder.getAdapterPosition()).getCategory() == ECategory.APPOINTMENT) {
            holder.taskTypeImageAppointment.setVisibility(View.VISIBLE);
        }
        //TODO
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RadioButton buttonSelect;
        private TextView taskTitle;
        private ImageView taskTypeImageAppointment;
        private ImageView taskTypeImageChecklist;
        private ImageView dragButton;
        private MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonSelect = itemView.findViewById(R.id.radioButtonSelect);
            taskTitle = itemView.findViewById(R.id.taskNameText);
            taskTypeImageAppointment = itemView.findViewById(R.id.taskTypeImageAppointment);
            taskTypeImageChecklist = itemView.findViewById(R.id.taskTypeImageChecklist);
            dragButton = itemView.findViewById(R.id.taskDragButton);
            parent = itemView.findViewById(R.id.parent_card);
        }
    }
}
