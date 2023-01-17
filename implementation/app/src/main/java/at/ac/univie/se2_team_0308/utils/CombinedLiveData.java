package at.ac.univie.se2_team_0308.utils;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.Collections;
import java.util.List;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

/**
 * This class combines two sources of LiveData into one.
 */
public class CombinedLiveData extends MediatorLiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> {
    private List<TaskAppointment> taskAppointmentList = Collections.emptyList();
    private List<TaskChecklist> taskChecklistList = Collections.emptyList();

    //START https://stackoverflow.com/questions/55044298/is-it-possible-to-make-one-livedata-of-two-livedatas
    public CombinedLiveData(LiveData<List<TaskAppointment>> ld1, LiveData<List<TaskChecklist>> ld2) {
        setValue(Pair.create(taskAppointmentList, taskChecklistList));

        addSource(ld1, taskAppointmentList -> {
            if(taskAppointmentList != null) {
                this.taskAppointmentList = taskAppointmentList;
            }
            setValue(Pair.create(taskAppointmentList, taskChecklistList));
        });

        addSource(ld2, taskChecklistList -> {
            if(taskChecklistList != null) {
                this.taskChecklistList = taskChecklistList;
            }
            setValue(Pair.create(taskAppointmentList, taskChecklistList));
        });
    }
    //END https://stackoverflow.com/questions/55044298/is-it-possible-to-make-one-livedata-of-two-livedatas
}
