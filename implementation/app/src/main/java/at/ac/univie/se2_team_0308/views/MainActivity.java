package at.ac.univie.se2_team_0308.views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements AddTaskFragment.AddTaskDialogListener, SendDataFromAddDialog, PropertyToBeUpdated.SelectPropertyToUpdateDialogListener, PropertyToBeUpdated.SendDataFromSelectPropertyUpdateDialog {

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
    private Exporter exporter = new Exporter();

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
                    showLayout(ELayout.SELECTED);
                }
                else {
                    showLayout(ELayout.ADD);
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

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLayout(ELayout.EXPORT);
                Log.d(TAG, "Export tasks");
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (viewModel.getSelectedTaskAppointmentIds() != null && viewModel.getSelectedTaskChecklistIds() != null) {
//                    viewModel.updateAllSelectedTasksPriorities(viewModel.getSelectedTaskAppointmentIds(), viewModel.getSelectedTaskChecklistIds(), EPriority.HIGH);
//                }

                DialogFragment fragment = new PropertyToBeUpdated();
                fragment.show(getSupportFragmentManager(), "update_property");
                /*if (viewModel.getSelectedTasksAppointment() != null && viewModel.getSelectedTasksChecklist() != null) {
                    viewModel.updateAllSelectedTasksPriorities(viewModel.getSelectedTasksAppointment(), viewModel.getSelectedTasksChecklist(), EPriority.HIGH);
                }*/
            }
        });


        btnExportJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getSelectedTaskAppointmentIds() != null && viewModel.getSelectedTaskChecklistIds() != null) {
                    showLayout(ELayout.ADD);
                    try {
                        List<TaskChecklist> taskChecklist = viewModel.getSelectedTaskChecklistNotLiveData(viewModel.getSelectedTaskChecklistIds());
                        List<TaskAppointment> taskAppointment = viewModel.getSelectedTaskAppointmentNotLiveData(viewModel.getSelectedTaskAppointmentIds());

                        if(!taskAppointment.isEmpty() || !taskChecklist.isEmpty()) {
                            Context applicationContext = getApplicationContext();
                            exporter.exportTasks(taskAppointment, taskChecklist, EFormat.JSON, applicationContext);
                            showToast("Tasks exported");
                        }
                    }
                    catch (Exception e){
                        Log.e(TAG, e.toString());
                    }
                }
            }
        });

        btnExportXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLayout(ELayout.ADD);
                try {
                    List<TaskChecklist> taskChecklist = viewModel.getSelectedTaskChecklistNotLiveData(viewModel.getSelectedTaskChecklistIds());
                    List<TaskAppointment> taskAppointment = viewModel.getSelectedTaskAppointmentNotLiveData(viewModel.getSelectedTaskAppointmentIds());
                    if(!taskAppointment.isEmpty() || !taskChecklist.isEmpty()) {
                        Context applicationContext = getApplicationContext();
                        exporter.exportTasks(taskAppointment, taskChecklist, EFormat.XML, applicationContext);
                        showToast("Tasks exported");
                    }
                }
                catch (Exception e){
                    Log.d(TAG, e.toString());
                }
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
        btnDelete = findViewById(R.id.btnDelete);
        btnHide = findViewById(R.id.btnHide);
        btnExport = findViewById(R.id.btnExport);
        btnUpdate = findViewById(R.id.btnUpdate);

        layoutExport = findViewById(R.id.layoutExport);
        btnExportJson = findViewById(R.id.btnExportJson);
        btnExportXml = findViewById(R.id.btnExportXml);
    }

    // Choose which view elements/layouts to make visible
    private void showLayout(ELayout layout){
        if(layout == ELayout.SELECTED){
            adapter.setSelectModeOn(true);
            fabAdd.setVisibility(View.GONE);
            layoutExport.setVisibility(View.GONE);

            layoutSelected.setVisibility(View.VISIBLE);
        }
        else if(layout == ELayout.EXPORT){
            layoutSelected.setVisibility(View.GONE);
            fabAdd.setVisibility(View.GONE);

            layoutExport.setVisibility(View.VISIBLE);
        }
        else if(layout == ELayout.ADD){
            selectedPressed = false;
            adapter.setSelectModeOn(false);
            layoutExport.setVisibility(View.GONE);
            layoutSelected.setVisibility(View.GONE);

            fabAdd.setVisibility(View.VISIBLE);
        }
    }

    private void showToast(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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

    @Override
    public void sendDataResult(String propertyName) {
        if (viewModel.getSelectedTaskAppointmentIds() != null && viewModel.getSelectedTaskChecklistIds() != null) {
            viewModel.updateAllSelectedTasksPriorities(viewModel.getSelectedTaskAppointmentIds(), viewModel.getSelectedTaskChecklistIds(), EPriority.HIGH);
        }
    }
}