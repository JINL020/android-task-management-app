package at.ac.univie.se2_team_0308.views;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.models.ASubtask;
import at.ac.univie.se2_team_0308.models.Attachment;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.SubtaskList;
import at.ac.univie.se2_team_0308.utils.import_tasks.UnsupportedDocumentFormatException;
import at.ac.univie.se2_team_0308.viewmodels.AttachmentsAdapter;
import at.ac.univie.se2_team_0308.viewmodels.SubtaskListAdapter;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class AddTaskFragment extends DialogFragment implements SketchFragment.SendDataFromSketchDialog, AttachmentsAdapter.OpenFileListener {
    public static final String TAG = "addtaskfragment";

    public interface SendDataFromAddDialog {
        void sendDataResult(String taskName, String taskDescription, EPriority priorityEnum, EStatus statusEnum, Date deadline, List<ASubtask> subtasks, List<Attachment> attachments, byte[] sketchData, Boolean isSelectedAppointment, Boolean isSelectedChecklist, String taskColor);
    }

    public interface AddTaskDialogListener {
        void onDialogPositiveClick(DialogFragment dialogFragment, Boolean wantToCloseDialog);
        void onDialogNegativeClick(DialogFragment dialogFragment, Boolean wantToCloseDialog);
    }

    private EditText editTaskName;
    private EditText editTaskDescription;
    private Spinner editTaskPriority;
    private Spinner editTaskStatus;
    private RadioButton radioBtnAppointment;
    private RadioButton radioBtnChecklist;

    private AddTaskDialogListener listener;
    private SendDataFromAddDialog inputListener;
    private DatePicker spinnerDatePicker;
    private TimePicker timePicker;

    private RelativeLayout relLayoutCard;
    private RelativeLayout relLayoutChooseDeadline;
    private TextView taskTypeValidation;
    Boolean isSelectedAppointment = false;
    Boolean isSelectedChecklist = false;
    Boolean wantToCloseDialog = false;

    private TaskViewModel viewModel;

    private SubtaskListAdapter subtaskListAdapter;
    private RecyclerView subtasksRecView;
    private RelativeLayout subtasksRelLayout;
    private Button addSubtaskButton;

    private Button btnAddAttachment;
    private Button btnCreateSketch;
    private RecyclerView filesListRecView;
    private AttachmentsAdapter attachmentsAdapter;
    private ImageView sketchPlacheholder;
    private byte[] sketchData = new byte[0];

    public AddTaskFragment(ATaskListFragment taskListFragment) {
        try {
            listener = (AddTaskDialogListener) taskListFragment;
            inputListener = (SendDataFromAddDialog) taskListFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_task_dialog_fragment, null, false);
        initViews(view);
        initSubtasksView();
        initAttachmentsView();

        radioBtnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskTypeValidation.setVisibility(View.GONE);
                isSelectedAppointment = true;
                isSelectedChecklist = false;
                relLayoutChooseDeadline.setVisibility(View.VISIBLE);
                subtasksRelLayout.setVisibility(View.GONE);
                addSubtaskButton.setVisibility(View.GONE);
            }
        });

        radioBtnChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskTypeValidation.setVisibility(View.GONE);
                isSelectedChecklist = true;
                isSelectedAppointment = false;
                relLayoutChooseDeadline.setVisibility(View.GONE);
                subtasksRelLayout.setVisibility(View.VISIBLE);
                addSubtaskButton.setVisibility(View.VISIBLE);
            }
        });

        btnCreateSketch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new SketchFragment(AddTaskFragment.this);
                fragment.show(getChildFragmentManager(), "addsketch_fromfragment");
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // nothing, we override it later
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDialogNegativeClick(AddTaskFragment.this, true);
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskName = editTaskName.getText().toString();
                String taskDescription = editTaskDescription.getText().toString();
                EPriority priorityEnum;
                switch (editTaskPriority.getSelectedItem().toString()) {
                    case "MEDIUM":
                        priorityEnum = EPriority.MEDIUM;
                        break;
                    case "HIGH":
                        priorityEnum = EPriority.HIGH;
                        break;
                    default:
                        priorityEnum = EPriority.LOW;
                        break;
                }
                EStatus statusEnum;
                switch (editTaskStatus.getSelectedItem().toString()) {
                    case "IN_PROGRESS":
                        statusEnum = EStatus.IN_PROGRESS;
                        break;
                    case "COMPLETED":
                        statusEnum = EStatus.COMPLETED;
                        break;
                    default:
                        statusEnum = EStatus.NOT_STARTED;
                        break;
                }

                Date deadline = Calendar.getInstance().getTime();

                if (isSelectedAppointment) {
                    int day = spinnerDatePicker.getDayOfMonth();
                    int month = spinnerDatePicker.getMonth();
                    int year = spinnerDatePicker.getYear();
                    int hour = 0;
                    int minute = 0;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        hour = timePicker.getHour();
                        minute = timePicker.getMinute();
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day, hour, minute, 0);
                    deadline = new Date(calendar.getTimeInMillis());
                }

                List<ASubtask> subtasks = new ArrayList<>();
                if(isSelectedChecklist) {
                    subtasks = subtaskListAdapter.getTasks();
                }

                Log.d(TAG, "onClick: task Name " + taskName);

                inputListener.sendDataResult(
                        taskName,
                        taskDescription ,
                        priorityEnum,
                        statusEnum,
                        deadline,
                        subtasks,
                        attachmentsAdapter.getAttachments(),
                        sketchData,
                        isSelectedAppointment,
                        isSelectedChecklist,
                        "#E1E1E1"
                );

                if (!isSelectedAppointment && !isSelectedChecklist) {
                    taskTypeValidation.setVisibility(View.VISIBLE);
                    wantToCloseDialog = false;
                    return;
                } else {
                    taskTypeValidation.setVisibility(View.GONE);
                    wantToCloseDialog = true;
                }

                listener.onDialogPositiveClick(AddTaskFragment.this, wantToCloseDialog);
            }
        });
        return dialog;
    }

    private void initViews(View view) {
        editTaskName = view.findViewById(R.id.editTaskName);
        editTaskDescription = view.findViewById(R.id.editTaskDescription);
        editTaskPriority = view.findViewById(R.id.spinnerTaskPriority);
        editTaskStatus = view.findViewById(R.id.spinnerTaskStatus);
        radioBtnAppointment = view.findViewById(R.id.radioButtonAppointment);
        radioBtnAppointment.setChecked(false);
        radioBtnChecklist = view.findViewById(R.id.radioButtonChecklist);
        radioBtnChecklist.setChecked(false);
        spinnerDatePicker = view.findViewById(R.id.spinnerDatePicker);
        relLayoutChooseDeadline = view.findViewById(R.id.relLayoutChooseDeadline);
        taskTypeValidation = view.findViewById(R.id.taskTypeValidation);
        filesListRecView = view.findViewById(R.id.filesListRecView);
        timePicker = view.findViewById(R.id.timePickerAddTaskDialog);
        timePicker.setIs24HourView(true);
        subtasksRelLayout = view.findViewById(R.id.subtasksRelLayout);
        subtasksRecView = view.findViewById(R.id.subtasksRecView);
        addSubtaskButton = view.findViewById(R.id.btnAddSubtask);
        btnAddAttachment = view.findViewById(R.id.btnAddAttachment);
        btnCreateSketch = view.findViewById(R.id.btnAddSketch);
        sketchPlacheholder = view.findViewById(R.id.sketchPlaceholder);
    }

    private void initAttachmentsView(){
        attachmentsAdapter = new AttachmentsAdapter(requireActivity(), this);
        filesListRecView.setAdapter(attachmentsAdapter);
        filesListRecView.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        btnAddAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch( "*/*");
            }
        });
    }
    private void initSubtasksView(){
        subtaskListAdapter = new SubtaskListAdapter(requireActivity(), new ArrayList<>());
        subtasksRecView.setAdapter(subtaskListAdapter);
        subtasksRecView.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));

        addSubtaskButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                subtaskListAdapter.addTask(new SubtaskList(""));
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
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