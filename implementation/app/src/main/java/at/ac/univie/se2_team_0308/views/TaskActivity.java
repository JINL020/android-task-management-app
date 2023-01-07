package at.ac.univie.se2_team_0308.views;

import static at.ac.univie.se2_team_0308.viewmodels.TaskListAdapter.TASK_ITEM_CATEGORY;
import static at.ac.univie.se2_team_0308.viewmodels.TaskListAdapter.TASK_ITEM_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.Subtask;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.utils.DisplayClass;
import at.ac.univie.se2_team_0308.viewmodels.SubtaskListAdapter;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class TaskActivity extends AppCompatActivity {

    private EditText editTaskName;
    private EditText editTaskDescription;
    private Spinner editTaskPriority;
    private Spinner editTaskStatus;

    private Button btnUpdateTask;
    private Button btnCancelUpdate;

    private DisplayClass incomingTask;
    private TaskAppointment incomingAppointment;
    private TaskChecklist incomingChecklist;
    private ECategory incomingCategory;

    private RelativeLayout deadlineRelLayout;
    private DatePicker deadlineSpinnerPicker;

    private TaskViewModel viewModel;

    private RecyclerView subtasksRecView;
    private RelativeLayout subtasksRelLayout;
    private Button addSubtaskButton;

    public static final String TAG = "TaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        initViews();
        initViewModel();

        final Intent intent = getIntent();
        if (intent != null) {
            incomingCategory = ECategory.valueOf(intent.getStringExtra(TASK_ITEM_CATEGORY));
            if (incomingCategory == ECategory.APPOINTMENT) {
                incomingAppointment = intent.getParcelableExtra(TASK_ITEM_KEY);
                incomingTask = new DisplayClass(incomingAppointment);
                setViews(incomingTask);
            }
            if (incomingCategory == ECategory.CHECKLIST) {
                incomingChecklist = intent.getParcelableExtra(TASK_ITEM_KEY);
                incomingTask = new DisplayClass(incomingChecklist);
                setViews(incomingTask);
            }
        }

        btnCancelUpdate.setOnClickListener(view -> {
            Intent intentBack = new Intent(TaskActivity.this, MainActivity.class);
            startActivity(intentBack);
        });

        addSubtaskButton.setOnClickListener(view -> {
            System.out.print("ADD new subtask request");
        });
    }

    private void setViews(DisplayClass incomingTask) {

        if (incomingTask.getTaskName() != null) {
            editTaskName.setText(incomingTask.getTaskName());
        }

        if (incomingTask.getDescription() != null) {
            editTaskDescription.setText(incomingTask.getDescription());
        }

        if (incomingTask.getPriority() != null) {
            switch (incomingTask.getPriority().toString()) {
                case "MEDIUM":
                    editTaskPriority.setSelection(1);
                    break;
                case "HIGH":
                    editTaskPriority.setSelection(2);
                    break;
                default:
                    editTaskPriority.setSelection(0);
                    break;
            }
        }

        if (incomingTask.getStatus() != null) {
            switch (incomingTask.getStatus().toString()) {
                case "IN_PROGRESS":
                    editTaskStatus.setSelection(1);
                    break;
                case "COMPLETED":
                    editTaskStatus.setSelection(2);
                    break;
                default:
                    editTaskStatus.setSelection(0);
                    break;
            }
        }

        switch (incomingTask.getCategoryEnum().toString()) {
            case "APPOINTMENT":
                Log.d(TAG, "onCreate: isappointment");
                subtasksRelLayout.setVisibility(View.GONE);
                addSubtaskButton.setVisibility(View.GONE);
                deadlineRelLayout.setVisibility(View.VISIBLE);

                Date incomingDate = incomingTask.getDeadline();
                incomingTask.setDeadline(incomingDate);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(incomingDate);

                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                deadlineSpinnerPicker.updateDate(year, month, day);
                break;
            case "CHECKLIST":
                subtasksRelLayout.setVisibility(View.VISIBLE);
                addSubtaskButton.setVisibility(View.VISIBLE);
                deadlineRelLayout.setVisibility(View.GONE);
                Log.d(TAG, "onBindViewHolder: the subtasks is not null");
                if (incomingTask.getSubtasks() != null && !incomingTask.getSubtasks().isEmpty()) {
                    setSubtasksView(incomingTask);
                }
                break;
        }

        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EPriority newPriority;
                switch (editTaskPriority.getSelectedItem().toString()) {
                    case "MEDIUM":
                        newPriority = EPriority.MEDIUM;
                        break;
                    case "HIGH":
                        newPriority = EPriority.HIGH;
                        break;
                    default:
                        newPriority = EPriority.LOW;
                        break;
                }

                EStatus newStatus;
                switch (editTaskStatus.getSelectedItem().toString()) {
                    case "IN_PROGRESS":
                        newStatus = EStatus.IN_PROGRESS;
                        break;
                    case "COMPLETED":
                        newStatus = EStatus.COMPLETED;
                        break;
                    default:
                        newStatus = EStatus.NOT_STARTED;
                        break;
                }
                incomingTask.setTaskName(editTaskName.getText().toString());
                incomingTask.setDescription(editTaskDescription.getText().toString());
                incomingTask.setPriority(newPriority);
                incomingTask.setStatus(newStatus);

                if (incomingTask.getCategoryEnum() == ECategory.APPOINTMENT) {
                    incomingAppointment.setTaskName(incomingTask.getTaskName());
                    incomingAppointment.setDescription(incomingTask.getDescription());
                    incomingAppointment.setPriority(incomingTask.getPriority());
                    incomingAppointment.setStatus(incomingTask.getStatus());

                    int day = deadlineSpinnerPicker.getDayOfMonth();
                    int month = deadlineSpinnerPicker.getMonth();
                    int year = deadlineSpinnerPicker.getYear();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    incomingTask.setDeadline(new Date(calendar.getTimeInMillis()));

                    incomingAppointment.setDeadline(incomingTask.getDeadline());
                    viewModel.updateAppointment(incomingAppointment);
                }
                if (incomingTask.getCategoryEnum() == ECategory.CHECKLIST){
                    incomingChecklist.setTaskName(incomingTask.getTaskName());
                    incomingChecklist.setDescription(incomingTask.getDescription());
                    incomingChecklist.setPriority(incomingTask.getPriority());
                    incomingChecklist.setStatus(incomingTask.getStatus());
                    viewModel.updateChecklist(incomingChecklist);
                }
                Intent intentBack = new Intent(TaskActivity.this, MainActivity.class);
                startActivity(intentBack);
            }
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        viewModel.init(getApplication());
    }

    private void setSubtasksView(DisplayClass incomingTask){
        SubtaskListAdapter adapter = new SubtaskListAdapter(this, incomingTask.getSubtasks(), new SubtaskListAdapter.onSubtaskClickListener(){
            @Override
            public void onDelete(Subtask taskModel) {
                System.out.print("subtask DELETE request");
            }
            @Override
            public void onAdd(Subtask taskModel) {
                System.out.print("subtask ADD request");
            }
            @Override
            public void onSubtaskChecked(Subtask taskModel, boolean isChecked) {
                System.out.print("subtask CHECKED request: " + isChecked);
            }
        });
        subtasksRecView.setAdapter(adapter);
        subtasksRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }
    private void initViews() {
        editTaskName = findViewById(R.id.updateTaskName);
        editTaskDescription = findViewById(R.id.updateTaskDescription);
        editTaskPriority = findViewById(R.id.spinnerUpdateTaskPriority);
        editTaskStatus = findViewById(R.id.spinnerUpdateTaskStatus);
        btnUpdateTask = findViewById(R.id.btnUpdate);
        btnCancelUpdate = findViewById(R.id.btnCancelUpdate);
        deadlineRelLayout = findViewById(R.id.relLayoutTaskDeadlineEdit);
        deadlineSpinnerPicker = findViewById(R.id.datePickerEdit);
        subtasksRelLayout = findViewById(R.id.subtasksRelLayout);
        subtasksRecView = findViewById(R.id.subtasksRecView);
        addSubtaskButton = findViewById(R.id.btnAddSubtask);
    }
}