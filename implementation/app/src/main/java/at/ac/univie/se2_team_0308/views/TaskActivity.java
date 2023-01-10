package at.ac.univie.se2_team_0308.views;

import static at.ac.univie.se2_team_0308.viewmodels.TaskListAdapter.TASK_ITEM_CATEGORY;
import static at.ac.univie.se2_team_0308.viewmodels.TaskListAdapter.TASK_ITEM_KEY;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.models.ASubtask;
import at.ac.univie.se2_team_0308.models.Attachment;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.SubtaskList;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.utils.DisplayClass;
import at.ac.univie.se2_team_0308.utils.import_tasks.UnsupportedDocumentFormatException;
import at.ac.univie.se2_team_0308.viewmodels.AttachmentsAdapter;
import at.ac.univie.se2_team_0308.viewmodels.SubtaskListAdapter;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class TaskActivity extends AppCompatActivity implements SketchFragment.SendDataFromSketchDialog, AttachmentsAdapter.OpenFileListener {

    public static final String TAG = "TaskActivity";

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
    private TimePicker timePicker;

    private TaskViewModel viewModel;

    private SubtaskListAdapter subtaskListAdapter;
    private RecyclerView subtasksRecView;
    private RelativeLayout subtasksRelLayout;
    private Button addSubtaskButton;

    private Button btnAddAttachment;
    private RecyclerView filesListRecView;
    private AttachmentsAdapter attachmentsAdapter;

    private Button btnCreateSketch;
    private ImageView sketchPlacheholder;
    private byte[] sketchData = new byte[0];

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
            finish();
            //Old implementation: will not work on all screens of navbar
            //Intent intentBack = new Intent(TaskActivity.this, MainActivity.class);
            //startActivity(intentBack);
        });

        addSubtaskButton.setOnClickListener(view -> {
            subtaskListAdapter.addTask(new SubtaskList(""));
        });

        btnCreateSketch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SketchFragment(TaskActivity.this).show(getSupportFragmentManager(), "addsketch_fromactivity");
            }
        });
    }

    private void setViews(DisplayClass incomingTask) {

        if (incomingTask.getTaskName() != null) {
            editTaskName.setText(incomingTask.getTaskName());
        }

        if (incomingTask.getDescription() != null) {
            editTaskDescription.setText(incomingTask.getDescription());
        }

        if(incomingTask.getSketchData() != null){
            sketchData = incomingTask.getSketchData();
            if(sketchData.length > 0){
                Bitmap bmp = BitmapFactory.decodeByteArray(sketchData, 0, sketchData.length);
                sketchPlacheholder.setImageBitmap(bmp);
                sketchPlacheholder.setVisibility(View.VISIBLE);
            }
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

        setAttachmentsView(incomingTask);

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
                int hour = 0;
                int minute = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    hour = calendar.get(Calendar.HOUR_OF_DAY);
                    minute = calendar.get(Calendar.MINUTE);
                }
                deadlineSpinnerPicker.updateDate(year, month, day);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timePicker.setHour(hour);
                    timePicker.setMinute(minute);
                }
                break;
            case "CHECKLIST":
                subtasksRelLayout.setVisibility(View.VISIBLE);
                addSubtaskButton.setVisibility(View.VISIBLE);
                deadlineRelLayout.setVisibility(View.GONE);
                Log.d(TAG, "onBindViewHolder: the subtasks is not null");
                setSubtasksView(incomingTask);
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
                incomingTask.setSketchData(sketchData);

                if (incomingTask.getCategoryEnum() == ECategory.APPOINTMENT) {
                    incomingAppointment.setTaskName(incomingTask.getTaskName());
                    incomingAppointment.setDescription(incomingTask.getDescription());
                    incomingAppointment.setPriority(incomingTask.getPriority());
                    incomingAppointment.setStatus(incomingTask.getStatus());
                    incomingAppointment.setSketchData(incomingTask.getSketchData());

                    int day = deadlineSpinnerPicker.getDayOfMonth();
                    int month = deadlineSpinnerPicker.getMonth();
                    int year = deadlineSpinnerPicker.getYear();
                    int hour = 0;
                    int minute = 0;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        hour = timePicker.getHour();
                        minute = timePicker.getMinute();
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day, hour, minute, 0);
                    incomingTask.setDeadline(new Date(calendar.getTimeInMillis()));

                    incomingAppointment.setDeadline(incomingTask.getDeadline());
                    viewModel.updateAppointment(incomingAppointment);

                }
                if (incomingTask.getCategoryEnum() == ECategory.CHECKLIST){
                    incomingChecklist.setTaskName(incomingTask.getTaskName());
                    incomingChecklist.setDescription(incomingTask.getDescription());
                    incomingChecklist.setPriority(incomingTask.getPriority());
                    incomingChecklist.setStatus(incomingTask.getStatus());
                    incomingChecklist.setSubtasks(subtaskListAdapter.getTasks());
                    incomingChecklist.setSketchData(incomingTask.getSketchData());

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
        subtaskListAdapter = new SubtaskListAdapter(this, incomingTask.getSubtasks());
        subtasksRecView.setAdapter(subtaskListAdapter);
        subtasksRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }
    private void setAttachmentsView(DisplayClass incomingTask){
        attachmentsAdapter = new AttachmentsAdapter(this, this);
        attachmentsAdapter.setAttachments(incomingTask.getAttachments());
        filesListRecView.setAdapter(attachmentsAdapter);
        filesListRecView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        btnAddAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch( "*/*");
            }
        });
    }

    private void initViews() {
        editTaskName = findViewById(R.id.updateTaskName);
        editTaskDescription = findViewById(R.id.updateTaskDescription);
        editTaskPriority = findViewById(R.id.spinnerUpdateTaskPriority);
        editTaskStatus = findViewById(R.id.spinnerUpdateTaskStatus);
        btnUpdateTask = findViewById(R.id.btnUpdateTaskInfo);
        btnCancelUpdate = findViewById(R.id.btnCancelUpdate);
        deadlineRelLayout = findViewById(R.id.relLayoutTaskDeadlineEdit);
        deadlineSpinnerPicker = findViewById(R.id.datePickerEdit);
        timePicker = findViewById(R.id.timePickerTaskActivity);
        timePicker.setIs24HourView(true);
        subtasksRelLayout = findViewById(R.id.subtasksRelLayout);
        subtasksRecView = findViewById(R.id.subtasksRecView);
        addSubtaskButton = findViewById(R.id.btnAddSubtask);
        filesListRecView = findViewById(R.id.filesListRecView);
        btnAddAttachment = findViewById(R.id.btnAddAttachment);
        btnCreateSketch = findViewById(R.id.btnAddSketch);
        sketchPlacheholder = findViewById(R.id.sketchPlaceholder);
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    File file = new File(uri.getPath());
                    String fileName = file.getPath().split(":")[1];
                    attachmentsAdapter.addAttachment(new Attachment(fileName));
                }
            });

    @Override
    public void sendDataResult(Bitmap bitmap) {
        // update sketch data
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        sketchData = bos.toByteArray();
        // update image view
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        sketchPlacheholder.setImageBitmap(mutableBitmap);
        sketchPlacheholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void openFile(String sketchPath) {
        // TODO open file
    }
}