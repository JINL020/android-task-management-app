package at.ac.univie.se2_team_0308;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ECategory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class TaskChecklistCreator {
    public static List<TaskChecklist> createTaskChecklist(){
        TaskChecklist taskChecklist1 = new TaskChecklist(
                "taskName1",
                "taskDescription1",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.CHECKLIST,
                new ArrayList<>()
        );

        TaskChecklist taskChecklist2 = new TaskChecklist(
                "taskName2",
                "taskDescription",
                EPriority.LOW,
                EStatus.NOT_STARTED,
                ECategory.CHECKLIST,
                new ArrayList<>()
        );
        return  Arrays.asList(taskChecklist1, taskChecklist2);
    }
}
