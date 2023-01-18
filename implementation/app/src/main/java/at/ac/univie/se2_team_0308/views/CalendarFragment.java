package at.ac.univie.se2_team_0308.views;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.databinding.FragmentCalendarBinding;
import at.ac.univie.se2_team_0308.models.ASubtask;
import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ATaskFactory;
import at.ac.univie.se2_team_0308.models.Attachment;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskAppointmentFactory;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.models.TaskChecklistFactory;
import at.ac.univie.se2_team_0308.utils.export_tasks.EFormat;
import at.ac.univie.se2_team_0308.utils.export_tasks.Exporter;
import at.ac.univie.se2_team_0308.utils.filter.FilterManager;
import at.ac.univie.se2_team_0308.utils.filter.UnhiddenTasksFilter;
import at.ac.univie.se2_team_0308.utils.import_tasks.ImporterFacade;
import at.ac.univie.se2_team_0308.viewmodels.TaskListAdapter;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;


public class CalendarFragment extends ATaskListFragment {
    private FragmentCalendarBinding binding;

    public static final String TAG = "calendar view";

    private RecyclerView recViewTasks;
    private FloatingActionButton fabAdd;
    private TaskListAdapter adapter;
    private TaskViewModel viewModel;

    private CalendarView calendarView;
    private Date selectedDate;

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

    private ConstraintLayout layoutExport;
    private Button btnExportJson;
    private Button btnExportXml;
    private Exporter exporter;

    private static ATaskFactory taskFactory;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initLayout();

        // Add new task
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new AddTaskFragment(CalendarFragment.this);
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

        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLayout(ELayout.EXPORT);
                Log.d(TAG, "Export tasks");

            }
        });

        // Delete selected tasks
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getSelectedTaskAppointmentIds() != null && viewModel.getSelectedTaskChecklistIds() != null) {
                    viewModel.deleteAllSelectedTasks(viewModel.getSelectedTaskAppointmentIds(), viewModel.getSelectedTaskChecklistIds());
                }
            }
        });

        // Update selected tasks
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PropertyToBeUpdated fragment = new PropertyToBeUpdated(CalendarFragment.this);
                fragment.show(getChildFragmentManager(), "update_property");
            }
        });

        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mGetContent.launch( "*/*");
            }
        });


        btnExportJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getSelectedTaskAppointmentIds() != null && viewModel.getSelectedTaskChecklistIds() != null) {
                    showLayout(ELayout.ADD);
                    try {
                        List<TaskChecklist> taskChecklist = viewModel.getSelectedTaskChecklist(viewModel.getSelectedTaskChecklistIds());
                        List<TaskAppointment> taskAppointment = viewModel.getSelectedTaskAppointment(viewModel.getSelectedTaskAppointmentIds());

                        if (!taskAppointment.isEmpty() || !taskChecklist.isEmpty()) {
                            exporter.exportTasks(taskAppointment, taskChecklist, EFormat.JSON);
                            adapter.setSelectModeOn(false);
                            viewModel.deselectAllTaskAppointment();
                            viewModel.deselectAllTaskChecklist();
                            showToast("Tasks exported");
                        }
                    } catch (Exception e) {
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
                    List<TaskChecklist> taskChecklist = viewModel.getSelectedTaskChecklist(viewModel.getSelectedTaskChecklistIds());
                    List<TaskAppointment> taskAppointment = viewModel.getSelectedTaskAppointment(viewModel.getSelectedTaskAppointmentIds());
                    if (!taskAppointment.isEmpty() || !taskChecklist.isEmpty()) {
                        Log.d(TAG, "Export tasks as Xml");
                        exporter.exportTasks(taskAppointment, taskChecklist, EFormat.XML);
                        adapter.setSelectModeOn(false);
                        viewModel.deselectAllTaskAppointment();
                        viewModel.deselectAllTaskChecklist();
                        showToast("Tasks exported");
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
            }
        });

        //Hide selected tasks
        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewModel.getSelectedTaskAppointmentIds() != null && viewModel.getSelectedTaskChecklistIds() != null) {
                    if (!viewModel.getSelectedTaskAppointmentIds().isEmpty() || !viewModel.getSelectedTaskChecklistIds().isEmpty()) {
                        viewModel.hideAllSelectedTasks(viewModel.getSelectedTaskAppointmentIds(), viewModel.getSelectedTaskChecklistIds(), true);
                        adapter.setSelectModeOn(false);
                        viewModel.deselectAllTaskAppointment();
                        viewModel.deselectAllTaskChecklist();
                        showLayout(ELayout.ADD);
                    }
                }
            }
        });

        //Calendar dates change
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                selectedDate = calendar.getTime();
                adapter.setTasks(new FilterManager().applyFilter(viewModel.getAppointmentTasks(selectedDate), new UnhiddenTasksFilter()));
                adapter.notifyDataSetChanged();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    protected void initViews() {
        fabAdd = binding.fabAdd;
        recViewTasks = binding.recViewTasks;
        btnSelect = binding.btnSelect;
        btnDelete = binding.btnDelete;
        btnHide = binding.btnHide;
        btnExport = binding.btnExport;
        btnUpdate = binding.btnUpdateCommonProperties;
        layoutSelected = binding.layoutSelect;
        layoutSelected.setVisibility(View.GONE);
        layoutExport = binding.layoutExport;
        layoutExport.setVisibility(View.GONE);
        btnExportJson = binding.btnExportJson;
        btnExportXml = binding.btnExportXml;
        btnImport = binding.btnImport;
        calendarView = binding.calendarView;
        selectedDate = Calendar.getInstance().getTime();
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(getActivity()).get(TaskViewModel.class);
        viewModel.init(getActivity().getApplication());// init does the same thing as the constructor, why init?
        viewModel.getAllLiveTasks().observe(getViewLifecycleOwner(), new Observer<Pair<List<TaskAppointment>, List<TaskChecklist>>>() {
            @Override
            public void onChanged(Pair<List<TaskAppointment>, List<TaskChecklist>> taskModels) {
                adapter.setTasks(new FilterManager().applyFilter(viewModel.getAppointmentTasks(selectedDate), new UnhiddenTasksFilter()));
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initRecyclerViews() {
        adapter = new TaskListAdapter(getActivity(), viewModel.getAppointmentTasks(selectedDate), new TaskListAdapter.onSelectItemListener() {
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

    @Override
    protected void initUtils() {
        importerFacade = new ImporterFacade(viewModel, getActivity().getContentResolver());
        exporter = new Exporter();
    }

    // Choose which view elements/layouts to make visible
    private void showLayout(ELayout layout) {
        if (layout == ELayout.SELECTED) {
            adapter.setSelectModeOn(true);
            layoutSelected.setVisibility(View.VISIBLE);
            fabAdd.setVisibility(View.GONE);
            layoutExport.setVisibility(View.GONE);
        }
        if (layout == ELayout.ADD) {
            selectedPressed = false;
            adapter.setSelectModeOn(false);
            fabAdd.setVisibility(View.VISIBLE);
            layoutSelected.setVisibility(View.GONE);
            layoutExport.setVisibility(View.GONE);
        }
        if (layout == ELayout.EXPORT) {
            layoutExport.setVisibility(View.VISIBLE);
            layoutSelected.setVisibility(View.GONE);
            fabAdd.setVisibility(View.GONE);
        }
    }

    @Override
    public void sendDataResult(String propertyName) {
        if ((viewModel.getSelectedTaskAppointmentIds() != null) && (viewModel.getSelectedTaskChecklistIds() != null)) {
            if (!viewModel.getSelectedTaskAppointmentIds().isEmpty() || !viewModel.getSelectedTaskChecklistIds().isEmpty()) {
                viewModel.updateAllSelectedTasksPriorities(viewModel.getSelectedTaskAppointmentIds(), viewModel.getSelectedTaskChecklistIds(), EPriority.HIGH);
                adapter.setSelectModeOn(false);
                viewModel.deselectAllTaskAppointment();
                viewModel.deselectAllTaskChecklist();
                showLayout(ELayout.ADD);
            }
        }
    }

    @Override
    public void sendDataResult(String taskName, String taskDescription, EPriority priorityEnum, EStatus statusEnum, Date deadline, List<ASubtask> subtasks, List<Attachment> attachments, byte[] sketchData, Boolean isSelectedAppointment, Boolean isSelectedChecklist, String taskColor) {
        Log.d(TAG, "sendDataResult: taskName" + taskName);
        Log.d(TAG, "sendDataResult: taskDescription" + taskDescription);
        Log.d(TAG, "sendDataResult: priorityEnum" + priorityEnum.toString());
        Log.d(TAG, "sendDataResult: statusEnum" + statusEnum.toString());
        Log.d(TAG, "sendDataResult: deadline" + deadline.toString());
        if (isSelectedAppointment) {
            taskFactory = new TaskAppointmentFactory();
            viewModel.insertAppointment((TaskAppointment) taskFactory.getNewTask(taskName, taskDescription, priorityEnum, statusEnum, deadline, subtasks, attachments,sketchData, taskColor));
        } else if (isSelectedChecklist) {
            taskFactory = new TaskChecklistFactory();
            viewModel.insertChecklist((TaskChecklist) taskFactory.getNewTask(taskName, taskDescription, priorityEnum, statusEnum, deadline, subtasks, attachments, sketchData, taskColor));
        }
        recViewTasks.smoothScrollToPosition(viewModel.getAllTasks().size());
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    Log.d(TAG, "File Uri: " + uri.toString());
                    importerFacade.importTasks(uri);
                }
            });
}