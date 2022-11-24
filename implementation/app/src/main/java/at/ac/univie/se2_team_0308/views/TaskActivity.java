package at.ac.univie.se2_team_0308.views;

import static at.ac.univie.se2_team_0308.viewmodels.TaskListAdapter.TASK_ITEM_CATEGORY;
import static at.ac.univie.se2_team_0308.viewmodels.TaskListAdapter.TASK_ITEM_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

import java.util.Calendar;
import java.util.Date;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.utils.DisplayClass;

public class TaskActivity extends AppCompatActivity {

    private EditText editTaskName;
    private Button btnCancelUpdate;

    private DisplayClass incomingTask;
    private TaskAppointment incomingAppointment;
    private ECategory incomingCategory;

    private RelativeLayout deadlineRelLayout;
    private DatePicker deadlineSpinnerPicker;

    private TaskViewModel viewModel;

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
        }

        btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent(TaskActivity.this, MainActivity.class);
                startActivity(intentBack);
            }
        });
    }

    private void setViews(DisplayClass incomingTask) {

        if (incomingTask.getTaskName() != null) {
            editTaskName.setText(incomingTask.getTaskName());
        }

        switch (incomingTask.getCategoryEnum().toString()) {
            case "APPOINTMENT":
                Log.d(TAG, "onCreate: isappointment");

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
                Log.d(TAG, "onCreate: ischecklist");
                deadlineRelLayout.setVisibility(View.GONE);
                break;
        }
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        viewModel.init(getApplication());
    }

    private void initViews() {
        editTaskName = findViewById(R.id.updateTaskName);
        btnCancelUpdate = findViewById(R.id.btnCancelUpdate);
        deadlineRelLayout = findViewById(R.id.relLayoutTaskDeadlineEdit);
        deadlineSpinnerPicker = findViewById(R.id.datePickerEdit);
    }
}