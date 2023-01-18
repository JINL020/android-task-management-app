package at.ac.univie.se2_team_0308.utils;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ASubtask;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.SubtaskItem;
import at.ac.univie.se2_team_0308.models.SubtaskList;
import at.ac.univie.se2_team_0308.utils.typeConverters.SubtasksConverter;

@RunWith(AndroidJUnit4.class)
public class SubtaskConverterTest {

    private SubtasksConverter converter;
    private List<ASubtask> subtasks;
    private String subtaskJsonString;
    @Before
    public void setUp() {
        converter = new SubtasksConverter();
        subtasks = new ArrayList<>();
        SubtaskList subtaskList1 = new SubtaskList("subtask1");
        SubtaskList subtaskList2 = new SubtaskList("subtask2");
        SubtaskItem subtaskItem21 = new SubtaskItem("subtask2.1");
        subtaskList2.addSubtask(subtaskItem21);
        SubtaskList subtaskList3 = new SubtaskList("subtask3");
        subtaskList2.setState(EStatus.COMPLETED);
        subtasks.add(subtaskList1);
        subtasks.add(subtaskList2);
        subtasks.add(subtaskList3);
        subtaskJsonString = "[{\\\"name\\\":\\\"subtask1\\\",\\\"id\\\":0,\\\"state\\\":\\\"NOT_STARTED\\\",\\\"subtasks\\\":[]},{\\\"name\\\":\\\"subtaks2\\\",\\\"id\\\":0,\\\"state\\\":\\\"COMPLETED\\\",\\\"subtasks\\\":[{\\\"name\\\":\\\"subtask2.1\\\",\\\"id\\\":0,\\\"state\\\":\\\"COMPLETED\\\"}]},{\\\"name\\\":\\\"subtask3\\\",\\\"id\\\":0,\\\"state\\\":\\\"NOT_STARTED\\\",\\\"subtasks\\\":[]}]";
    }

    @Test
    public void deserializeInvalidJson_getEmptyList(){
        ArrayList<ASubtask> converted = converter.jsonToSubtasks("[");
        assertTrue(converted.isEmpty());
    }

    @Test
    public void convertSubtasks_getValidJson(){
        String convertedSubtasksToJson = converter.subtasksToJson(this.subtasks);
        assertTrue(convertedSubtasksToJson.equals(subtaskJsonString));
    }

    @Test
    public void deserializeSubtasksJson_getValidSubtasks(){
        ArrayList<ASubtask> converted = converter.jsonToSubtasks(subtaskJsonString);
        assertTrue(converted.size() == subtasks.size());
        for(int i = 0; i < 3; i++){
            assertTrue(converted.get(i).getSubtasks().size() == subtasks.get(i).getSubtasks().size());
        }
    }
}
