package at.ac.univie.se2_team_0308.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
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
import at.ac.univie.se2_team_0308.viewmodels.TaskListAdapter;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;
import at.ac.univie.se2_team_0308.views.AddTaskFragment.SendDataFromAddDialog;

public class MainActivity extends AppCompatActivity implements AddTaskFragment.AddTaskDialogListener, SendDataFromAddDialog, PropertyToBeUpdated.SelectPropertyToUpdateDialogListener, PropertyToBeUpdated.SendDataFromSelectPropertyUpdateDialog {

    public static final String TAG = "main act";

    private RecyclerView recViewTasks;
    private FloatingActionButton fabAdd;
    private TaskListAdapter adapter;
    private TaskViewModel viewModel;

    // Variables influenced by the press of the "Select" button
    private Button btnSelect;
    private boolean selectedPressed;
    private RelativeLayout layoutSelected;
    private Button btnDelete;
    private Button btnHide;
    private Button btnExport;
    private Button btnUpdate;

    private static final int FILE_SELECT_CODE = 0;
    private Button btnImport;

    private static ATaskFactory taskFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initViewModel();
        initRecyclerViews();

        // Add new task
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new AddTaskFragment();
                fragment.show(getSupportFragmentManager(), "addtask");
            }
        });

        // Initiate selection
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                selectedPressed = !selectedPressed;

                if(selectedPressed){
                    adapter.setSelectModeOn(true);
                    fabAdd.setVisibility(View.GONE);
                    layoutSelected.setVisibility(View.VISIBLE);
                }
                else {
                    adapter.setSelectModeOn(false);
                    fabAdd.setVisibility(View.VISIBLE);
                    layoutSelected.setVisibility(View.GONE);
                }
            }
        });

        // Delete selected tasks
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getSelectedTasksAppointment() != null && viewModel.getSelectedTasksChecklist() != null) {
                    viewModel.deleteAllSelectedTasks(viewModel.getSelectedTasksAppointment(), viewModel.getSelectedTasksChecklist());
                }
            }
        });

        // Update selected tasks
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new PropertyToBeUpdated();
                fragment.show(getSupportFragmentManager(), "update_property");
                /*if (viewModel.getSelectedTasksAppointment() != null && viewModel.getSelectedTasksChecklist() != null) {
                    viewModel.updateAllSelectedTasksPriorities(viewModel.getSelectedTasksAppointment(), viewModel.getSelectedTasksChecklist(), EPriority.HIGH);
                }*/
            }
        });

        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
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
        layoutSelected.setVisibility(View.GONE);
        btnDelete = findViewById(R.id.btnDelete);
        btnHide = findViewById(R.id.btnHide);
        btnExport = findViewById(R.id.btnExport);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnImport = findViewById(R.id.btnImport);
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
        if (viewModel.getSelectedTasksAppointment() != null && viewModel.getSelectedTasksChecklist() != null) {
            viewModel.updateAllSelectedTasksPriorities(viewModel.getSelectedTasksAppointment(), viewModel.getSelectedTasksChecklist(), EPriority.HIGH);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void chooseFile(){



        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {
                    "*/*"
            });

            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.e(TAG, e.toString());
        }

    }
}