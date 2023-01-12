package at.ac.univie.se2_team_0308.viewmodels;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;
import static java.util.Arrays.asList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ActivityController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.models.ATask;
import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.views.MainActivity;

@RunWith(RobolectricTestRunner.class)
public class SelectDeselectTest {

    private TaskListAdapter adapter;
    private TaskViewModel viewModel;
    private ATask task;
    private TaskListAdapter.ViewHolder viewHolder;
    private List<Integer> selectedTasksAppointment;
    private java.util.List<Integer> selectedTasksChecklist;
    private ATask firstTask;
    private ATask secondTask;
    private boolean first;
    private List<ATask> tasks;

    @Before
    public void setup() {
        adapter = Mockito.mock(TaskListAdapter.class);
        viewModel = Mockito.mock(TaskViewModel.class);
        View view = Mockito.mock(View.class);
        first = true;

        selectedTasksAppointment = new ArrayList<>();
        selectedTasksChecklist = new ArrayList<>();


        firstTask = new TaskAppointment(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                new Date(2020, 5, 12),
                new ArrayList<>(),
                new byte[0],
                ""
        );
        firstTask.setId(0);
        secondTask = new TaskChecklist(
                "taskName",
                "taskName",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.APPOINTMENT,
                new ArrayList<>(),
                new ArrayList<>(),
                new byte[0],
                ""
        );
        secondTask.setId(1);
        adapter.setTasks(asList(firstTask, secondTask));

    }

//    @Test
//    public void itemCount() {
//        Assert.assertEquals(2, adapter.getItemCount());
//    }

    @Test
    public void SelectTaskDeselectTask_SelectedTasksListGetsUpdated() {

        // START https://stackoverflow.com/questions/11864092/junit-testing-in-android-dialogfragment
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            MainActivity activity = controller.get();
            // END https://stackoverflow.com/questions/11864092/junit-testing-in-android-dialogfragment
            activity.findViewById(R.id.btnSelect).performClick();
            //shadowOf(Looper.getMainLooper()).idle();
            getInstrumentation().waitForIdleSync();

            assertNotNull((TextView) activity.findViewById(R.id.btnUpdateCommonProperties));

            LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listItemView = inflater.inflate(R.layout.task_row_item, null, false);
            viewHolder = new TaskListAdapter.ViewHolder(listItemView);
            getInstrumentation().waitForIdleSync();

            viewHolder.itemView.findViewById(R.id.radioButtonSelect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (first) {
                        selectedTasksAppointment.add(0);
                        viewModel.selectTaskAppointment(firstTask);
                        first = false;
                    } else {
                        selectedTasksAppointment.remove(0);
                        viewModel.deselectTaskAppointment(firstTask);
                    }
                }
            });

            viewHolder.itemView.findViewById(R.id.radioButtonSelect).performClick();
            getInstrumentation().waitForIdleSync();

            Mockito.verify(viewModel, Mockito.times(1)).selectTaskAppointment(firstTask);
            Assert.assertEquals(1, selectedTasksAppointment.size());

            viewHolder.itemView.findViewById(R.id.radioButtonSelect).performClick();
            getInstrumentation().waitForIdleSync();

            Mockito.verify(viewModel, Mockito.times(1)).deselectTaskAppointment(firstTask);
            Assert.assertEquals(0, selectedTasksAppointment.size());

        }
    }

    @Test
    public void SelectTaskDeselectTask_PriorityGetsUpdated() {

        // START https://stackoverflow.com/questions/11864092/junit-testing-in-android-dialogfragment
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            MainActivity activity = controller.get();
            // END https://stackoverflow.com/questions/11864092/junit-testing-in-android-dialogfragment
            activity.findViewById(R.id.btnSelect).performClick();
            //shadowOf(Looper.getMainLooper()).idle();
            getInstrumentation().waitForIdleSync();

            assertNotNull((TextView) activity.findViewById(R.id.btnUpdateCommonProperties));

            activity.findViewById(R.id.btnUpdateCommonProperties).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((selectedTasksAppointment != null)  && (selectedTasksChecklist != null) ) {
                        if(!selectedTasksAppointment.isEmpty() || !selectedTasksChecklist.isEmpty()) {
                            viewModel.updateAllSelectedTasksPriorities(selectedTasksAppointment, selectedTasksChecklist, EPriority.HIGH);
                        }
                    }
                }
            });

            LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listItemView = inflater.inflate(R.layout.task_row_item, null, false);
            viewHolder = new TaskListAdapter.ViewHolder(listItemView);
            getInstrumentation().waitForIdleSync();

            viewHolder.itemView.findViewById(R.id.radioButtonSelect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (first) {
                        selectedTasksAppointment.add(0);
                        viewModel.selectTaskAppointment(firstTask);
                        first = false;
                    } else {
                        selectedTasksAppointment.remove(0);
                        viewModel.deselectTaskAppointment(firstTask);
                    }
                }
            });

            viewHolder.itemView.findViewById(R.id.radioButtonSelect).performClick();
            getInstrumentation().waitForIdleSync();

            Mockito.verify(viewModel, Mockito.times(1)).selectTaskAppointment(firstTask);
            Assert.assertEquals(1, selectedTasksAppointment.size());

            activity.findViewById(R.id.btnUpdateCommonProperties).performClick();

            Mockito.verify(viewModel, Mockito.times(1)).updateAllSelectedTasksPriorities(selectedTasksAppointment, selectedTasksChecklist, EPriority.HIGH);

        }
    }

    @Test
    public void SelectTaskDeselectTask_PriorityStaysTheSame() {

        // START https://stackoverflow.com/questions/11864092/junit-testing-in-android-dialogfragment
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            MainActivity activity = controller.get();
            // END https://stackoverflow.com/questions/11864092/junit-testing-in-android-dialogfragment
            activity.findViewById(R.id.btnSelect).performClick();
            //shadowOf(Looper.getMainLooper()).idle();
            getInstrumentation().waitForIdleSync();

            assertNotNull((TextView) activity.findViewById(R.id.btnUpdateCommonProperties));

            activity.findViewById(R.id.btnUpdateCommonProperties).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((selectedTasksAppointment != null)  && (selectedTasksChecklist != null) ) {
                        if(!selectedTasksAppointment.isEmpty() || !selectedTasksChecklist.isEmpty()) {
                            viewModel.updateAllSelectedTasksPriorities(selectedTasksAppointment, selectedTasksChecklist, EPriority.HIGH);
                        }
                    }
                }
            });

            LayoutInflater inflater = (LayoutInflater) RuntimeEnvironment.application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listItemView = inflater.inflate(R.layout.task_row_item, null, false);
            viewHolder = new TaskListAdapter.ViewHolder(listItemView);
            getInstrumentation().waitForIdleSync();

            viewHolder.itemView.findViewById(R.id.radioButtonSelect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (first) {
                        selectedTasksAppointment.add(0);
                        viewModel.selectTaskAppointment(firstTask);
                        first = false;
                    } else {
                        selectedTasksAppointment.remove(0);
                        viewModel.deselectTaskAppointment(firstTask);
                    }
                }
            });

            viewHolder.itemView.findViewById(R.id.radioButtonSelect).performClick();
            getInstrumentation().waitForIdleSync();

            Mockito.verify(viewModel, Mockito.times(1)).selectTaskAppointment(firstTask);
            Assert.assertEquals(1, selectedTasksAppointment.size());

            viewHolder.itemView.findViewById(R.id.radioButtonSelect).performClick();
            getInstrumentation().waitForIdleSync();

            Mockito.verify(viewModel, Mockito.times(1)).deselectTaskAppointment(firstTask);
            Assert.assertEquals(0, selectedTasksAppointment.size());

            activity.findViewById(R.id.btnUpdateCommonProperties).performClick();

            Mockito.verify(viewModel, Mockito.times(0)).updateAllSelectedTasksPriorities(selectedTasksAppointment, selectedTasksChecklist, EPriority.HIGH);

        }
    }
}
