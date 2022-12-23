package at.ac.univie.se2_team_0308.views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ATaskFactory;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskAppointmentFactory;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.models.TaskChecklistFactory;
import at.ac.univie.se2_team_0308.utils.export.EFormat;
import at.ac.univie.se2_team_0308.utils.export.Exporter;
import at.ac.univie.se2_team_0308.viewmodels.TaskListAdapter;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;
import at.ac.univie.se2_team_0308.views.AddTaskFragment.SendDataFromAddDialog;

public class MainActivity extends AppCompatActivity implements AddTaskFragment.AddTaskDialogListener, SendDataFromAddDialog{

    public static final String TAG = "main act";

    private RecyclerView recViewTasks;
    private FloatingActionButton fabAdd;
    private TaskListAdapter adapter;
    private TaskViewModel viewModel;

    private Button btnSelect;
    private boolean selectedPressed;
    private RelativeLayout layoutSelected;
    private Button btnDelete;
    private Button btnHide;
    private Button btnExport;
    private Button btnUpdate;

    private RelativeLayout layoutExport;
    private Button btnExportJson;
    private Button btnExportXml;
    private Exporter exporter = new Exporter(); //TODO should I move this somewhere else

    private static ATaskFactory taskFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initViewModel();
        initRecyclerViews();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new AddTaskFragment();
                fragment.show(getSupportFragmentManager(), "addtask");
            }
        });



        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                selectedPressed = !selectedPressed;

                if(selectedPressed){
                    fabAdd.setVisibility(View.GONE);
                    layoutSelected.setVisibility(View.VISIBLE);
                }
                else {
                    fabAdd.setVisibility(View.VISIBLE);
                    layoutSelected.setVisibility(View.GONE);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getSelectedTaskAppointmentIds() != null && viewModel.getSelectedTaskChecklistIds() != null) {
                    viewModel.deleteAllSelectedTasks(viewModel.getSelectedTaskAppointmentIds(), viewModel.getSelectedTaskChecklistIds());
                }
            }
        });


        // TODO add logger
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutSelected.setVisibility(View.GONE);
                layoutExport.setVisibility(View.VISIBLE);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getSelectedTaskAppointmentIds() != null && viewModel.getSelectedTaskChecklistIds() != null) {
                    viewModel.updateAllSelectedTasksPriorities(viewModel.getSelectedTaskAppointmentIds(), viewModel.getSelectedTaskChecklistIds(), EPriority.HIGH);
                }
            }
        });


        btnExportJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getSelectedTaskAppointmentIds() != null && viewModel.getSelectedTaskChecklistIds() != null) {

                selectedPressed = false;
                layoutExport.setVisibility(View.GONE);
                fabAdd.setVisibility(View.VISIBLE);

                try {
                    Context applicationContext = getApplicationContext();
                    List<TaskChecklist> taskChecklist = viewModel.getSelectedTaskChecklistNotLiveData(viewModel.getSelectedTaskChecklistIds());
                    List<TaskAppointment> taskAppointment = viewModel.getSelectedTaskAppointmentNotLiveData(viewModel.getSelectedTaskAppointmentIds());
                    exporter.exportTasks(taskAppointment, taskChecklist, EFormat.JSON, applicationContext);
                }
                catch (Exception e){
                    Log.d(TAG, e.toString());
                }

                // TODO add pop-up that notifies success
                }
            }
        });

        btnExportXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPressed = false;
                layoutExport.setVisibility(View.GONE);
                fabAdd.setVisibility(View.VISIBLE);

                try {
                    Context applicationContext = getApplicationContext();
                    List<TaskChecklist> taskChecklist = viewModel.getSelectedTaskChecklistNotLiveData(viewModel.getSelectedTaskChecklistIds());
                    List<TaskAppointment> taskAppointment = viewModel.getSelectedTaskAppointmentNotLiveData(viewModel.getSelectedTaskAppointmentIds());
                    exporter.exportTasks(taskAppointment, taskChecklist, EFormat.XML, applicationContext);
                }
                catch (Exception e){
                    Log.d(TAG, e.toString());
                }
                // TODO add pop-up that notifies success
            }
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        viewModel.init(getApplication());
        viewModel.getAllLiveTasks().observe(this, new Observer<Pair<List<TaskAppointment>, List<TaskChecklist>>>() {
            @Override
            public void onChanged(Pair<List<TaskAppointment>, List<TaskChecklist>> taskModels) {
                adapter.setTasks(viewModel.getAllTasks());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerViews() {
        adapter = new TaskListAdapter(this, viewModel.getAllTasks(), new TaskListAdapter.onSelectItemListener() {
            @Override
            public void onItemSelected(ATask taskModel) {
                if (taskModel.isSelected()) {
                    if (taskModel.getCategory() == ECategory.APPOINTMENT) {
                        viewModel.selectTaskAppointment(taskModel);
                    } else {
                        viewModel.selectTaskChecklist(taskModel);
                    }
                    Log.d(TAG, "onItemSelected: item is selected");
                } else {
                    Log.d(TAG, "onItemSelected: item is deselected");
                }
            }
        });
        recViewTasks.setAdapter(adapter);
        recViewTasks.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void initViews() {
        fabAdd = findViewById(R.id.fabAdd);
        recViewTasks = findViewById(R.id.recViewTasks);
        btnSelect = findViewById(R.id.btnSelect);
        layoutSelected = findViewById(R.id.layoutSelect);
        layoutSelected.setVisibility(View.GONE); // TODO move this
        btnDelete = findViewById(R.id.btnDelete);
        btnHide = findViewById(R.id.btnHide);
        btnExport = findViewById(R.id.btnExport);
        btnUpdate = findViewById(R.id.btnUpdate);

        layoutExport = findViewById(R.id.layoutExport);
        btnExportJson = findViewById(R.id.btnExportJson);
        btnExportXml = findViewById(R.id.btnExportXml);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment, Boolean wantToCloseDialog) {
        if (wantToCloseDialog) {
            dialogFragment.dismiss();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment, Boolean wantToCloseDialog) {
        if (wantToCloseDialog) {
            dialogFragment.dismiss();
        }
    }

    @Override
    public void sendDataResult(String taskName, String taskDescription, EPriority priorityEnum, EStatus statusEnum, Date deadline, Boolean isSelectedAppointment, Boolean isSelectedChecklist) {
        Log.d(TAG, "sendDataResult: taskName" + taskName);
        Log.d(TAG, "sendDataResult: taskDescription" + taskDescription);
        Log.d(TAG, "sendDataResult: priorityEnum" + priorityEnum.toString());
        Log.d(TAG, "sendDataResult: statusEnum" + statusEnum.toString());
        Log.d(TAG, "sendDataResult: deadline" + deadline.toString());
        if (isSelectedAppointment) {
            taskFactory = new TaskAppointmentFactory();
            viewModel.insertAppointment((TaskAppointment) taskFactory.getNewTask(taskName, taskDescription, priorityEnum, statusEnum, deadline, new ArrayList<>()));
        } else if (isSelectedChecklist) {
            taskFactory = new TaskChecklistFactory();
            viewModel.insertChecklist((TaskChecklist) taskFactory.getNewTask(taskName, taskDescription, priorityEnum, statusEnum, deadline, new ArrayList<>()));
        }
        recViewTasks.smoothScrollToPosition(viewModel.getAllTasks().size());
    }

}