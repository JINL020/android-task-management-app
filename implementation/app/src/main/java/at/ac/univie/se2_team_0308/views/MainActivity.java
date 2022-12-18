package at.ac.univie.se2_team_0308.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.models.ATaskFactory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskAppointmentFactory;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.models.TaskChecklistFactory;
import at.ac.univie.se2_team_0308.viewmodels.TaskListAdapter;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class MainActivity extends AppCompatActivity implements AddTaskFragment.AddTaskDialogListener, AddTaskFragment.SendDataFromAddDialog{

    public static final String TAG = "main act";

    private RecyclerView recViewTasks;
    private FloatingActionButton fabAdd;
    private TaskListAdapter adapter;
    private TaskViewModel viewModel;

    private Button btnSelected;
    private boolean selectedPressed;
    private LinearLayout layoutSelected;
    private Button btnDelete;
    private Button btnHide;
    private Spinner spinnerOptions;

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

        btnSelected.setOnClickListener(new View.OnClickListener() {
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
        adapter = new TaskListAdapter(this, viewModel.getAllTasks());
        recViewTasks.setAdapter(adapter);
        recViewTasks.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void initViews() {
        fabAdd = findViewById(R.id.fabAdd);
        recViewTasks = findViewById(R.id.recViewTasks);
        btnSelected = findViewById(R.id.btnSelected);
        layoutSelected = findViewById(R.id.layoutSelected);
        layoutSelected.setVisibility(View.GONE);
        btnDelete = findViewById(R.id.btnDelete);
        btnHide = findViewById(R.id.btnHide);

        /// Code taken from https://developer.android.com/develop/ui/views/components/spinner#:~:text=Spinners%20provide%20a%20quick%20way,layout%20with%20the%20Spinner%20object.
        spinnerOptions = findViewById(R.id.spinnerOptions);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayOptions, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerOptions.setAdapter(adapter);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment) {
        //Toast.makeText(this, "Clicked on Add" , Toast.LENGTH_SHORT).show();
        dialogFragment.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment) {
        //Toast.makeText(this, "Clicked on Cancel", Toast.LENGTH_SHORT).show();
        dialogFragment.dismiss();
    }

    @Override
    public void sendDataResult(String taskName, String taskDescription, EPriority priorityEnum, EStatus statusEnum, Date deadline, Boolean isSelectedAppointment) {
        Log.d(TAG, "sendDataResult: taskName" + taskName);
        Log.d(TAG, "sendDataResult: taskDescription" + taskDescription);
        Log.d(TAG, "sendDataResult: priorityEnum" + priorityEnum.toString());
        Log.d(TAG, "sendDataResult: statusEnum" + statusEnum.toString());
        Log.d(TAG, "sendDataResult: deadline" + deadline.toString());
        if (isSelectedAppointment) {
            taskFactory = new TaskAppointmentFactory();
            viewModel.insertAppointment((TaskAppointment) taskFactory.getNewTask(taskName, taskDescription, priorityEnum, statusEnum, deadline, new ArrayList<>()));
        } else {
            taskFactory = new TaskChecklistFactory();
//            viewModel.insertChecklist((TaskChecklist) taskFactory.getNewTask(taskName, taskDescription, priorityEnum, statusEnum, deadline, new ArrayList<>()));
        }
        recViewTasks.smoothScrollToPosition(viewModel.getAllTasks().size());
    }

}