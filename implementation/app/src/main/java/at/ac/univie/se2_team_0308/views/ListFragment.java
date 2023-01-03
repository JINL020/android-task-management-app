package at.ac.univie.se2_team_0308.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.databinding.FragmentListBinding;
import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ATaskFactory;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskAppointmentFactory;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.models.TaskChecklistFactory;
import at.ac.univie.se2_team_0308.utils.export.Exporter;
import at.ac.univie.se2_team_0308.utils.import_tasks.ImporterFacade;
import at.ac.univie.se2_team_0308.viewmodels.TaskListAdapter;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class ListFragment extends Fragment implements AddTaskFragment.AddTaskDialogListener, AddTaskFragment.SendDataFromAddDialog {
    private FragmentListBinding binding;

    public static final String TAG = "main act";

    private RecyclerView recViewTasks;
    private FloatingActionButton fabAdd;
    private TaskListAdapter adapter;
    private TaskViewModel viewModel;

    // Variables influenced by the press of the "Select" button
    private Button btnSelect;
    private boolean selectedPressed;
    private ConstraintLayout layoutSelected;
    private Button btnDelete;
    private Button btnHide;
    private Button btnExport;
    private Button btnUpdate;

    private static final int FILE_SELECT_CODE = 0;
    private Button btnImport;
    private ImporterFacade importerFacade;

    private RelativeLayout layoutExport;
    private Button btnExportJson;
    private Button btnExportXml;
    private Exporter exporter;

    private static ATaskFactory taskFactory;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initViews();
        initViewModel();
        initRecyclerViews();
        initUtils();

        // Add new task
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new AddTaskFragment(ListFragment.this);
                fragment.show(getChildFragmentManager(), "addtask");
            }
        });

        // Initiate selection
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPressed = !selectedPressed;
                if (selectedPressed) {
                    showLayout(ELayout.SELECTED);
                } else {
                    showLayout(ELayout.ADD);
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initViews() {
        fabAdd = binding.fabAdd;
        recViewTasks = binding.recViewTasks;
        btnSelect = binding.btnSelect;
        layoutSelected = binding.layoutSelect;
        layoutSelected.setVisibility(View.GONE);
        btnDelete = binding.btnDelete;
        btnHide = binding.btnHide;
        btnExport = binding.btnExport;
        btnUpdate = binding.btnUpdate;
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(getActivity()).get(TaskViewModel.class);
        viewModel.init(getActivity().getApplication());// init does the same thing as the constructor, why init?
        viewModel.getAllLiveTasks().observe(getViewLifecycleOwner(), new Observer<Pair<List<TaskAppointment>, List<TaskChecklist>>>() {
            @Override
            public void onChanged(Pair<List<TaskAppointment>, List<TaskChecklist>> taskModels) {
                adapter.setTasks(viewModel.getAllTasks());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerViews() {
        adapter = new TaskListAdapter(getActivity(), viewModel.getAllTasks(), new TaskListAdapter.onSelectItemListener() {
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
                    if (taskModel.getCategory() == ECategory.APPOINTMENT) {
                        viewModel.deselectTaskAppointment(taskModel);
                    } else {
                        viewModel.deselectTaskChecklist(taskModel);
                    }
                    Log.d(TAG, "onItemSelected: item is deselected");
                }
            }
        });
        recViewTasks.setAdapter(adapter);
        recViewTasks.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(), RecyclerView.VERTICAL, false));
    }

    private void initUtils() {
        importerFacade = new ImporterFacade(viewModel, getActivity().getContentResolver());
        exporter = new Exporter();
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

    // Choose which view elements/layouts to make visible
    private void showLayout(ELayout layout) {
        if (layout == ELayout.SELECTED) {
            adapter.setSelectModeOn(true);
            fabAdd.setVisibility(View.GONE);
            layoutSelected.setVisibility(View.VISIBLE);
        }
        if (layout == ELayout.ADD) {
            adapter.setSelectModeOn(false);
            fabAdd.setVisibility(View.VISIBLE);
            layoutSelected.setVisibility(View.GONE);
            selectedPressed = false;
        }
    }
}