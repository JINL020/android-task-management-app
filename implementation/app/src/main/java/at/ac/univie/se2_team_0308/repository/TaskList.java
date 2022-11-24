package at.ac.univie.se2_team_0308.repository;

import android.app.Application;

import androidx.core.util.Pair;
import androidx.lifecycle.LiveData;

import java.util.List;

public interface TaskList {
    LiveData<Pair<List<TaskAppointment>, List<TaskChecklist>>> getAllTasks(Application application);
    TaskAppointmentDao getAppointmentDao();
    ChecklistDao getChecklistDao();
}
