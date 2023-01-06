package at.ac.univie.se2_team_0308.utils.export;

import android.os.Parcel;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.ac.univie.se2_team_0308.TaskAppointmentCreator;
import at.ac.univie.se2_team_0308.TaskChecklistCreator;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

@RunWith(AndroidJUnit4.class)
public class TaskToJSONConverterTest extends TestCase {
    private ITaskConverter taskConverter = new TaskToJSONConverter();
    @Test
    public void convertMultipleTaskAppointment_() {
        List<TaskAppointment> taskAppointment = TaskAppointmentCreator.createTaskAppointment();
        List<TaskChecklist> taskChecklist = new ArrayList<>();
        String expectedConverted = "{\"AllTasks\": {\n" +
                "{\"deadline\":\"Jan 1, 1970, 7:33:20 AM\",\"id\":1,\"taskName\":\"taskName1\",\"description\":\"description1\",\"priority\":\"LOW\",\"status\":\"NOT_STARTED\",\"isSelected\":false,\"category\":\"APPOINTMENT\",\"creationDate\":\"Jan 1, 1970, 7:33:20 AM\"}\n" +
                "{\"deadline\":\"Jan 13, 1970, 10:38:31 PM\",\"id\":2,\"taskName\":\"taskName2\",\"description\":\"description2\",\"priority\":\"LOW\",\"status\":\"NOT_STARTED\",\"isSelected\":false,\"category\":\"APPOINTMENT\",\"creationDate\":\"Jan 13, 1970, 10:38:31 PM\"}\n" +
                "}";

        String actualConverted = taskConverter.convertTasks(taskAppointment, taskChecklist);

//        Assert.assertEquals(expectedConverted, actualConverted);
//        assertThat("First Line\nSecond Line\n").isEqualToNormalizingNewlines("First Line\r\nSecond Line\r\n");
//        assertTrue(expectedConverted.equals(actualConverted));
    }



    @Test
    public void exportTasks_ExportJsonTaskAppointmentTaskChecklist() {
        List<TaskAppointment> taskAppointment = TaskAppointmentCreator.createTaskAppointment();
        List<TaskChecklist> taskChecklist = TaskChecklistCreator.createTaskChecklist();

    }

    @Test
    public void exportTasks_ExportXmlTaskAppointmentTaskChecklist() {
        List<TaskAppointment> taskAppointment = TaskAppointmentCreator.createTaskAppointment();
        List<TaskChecklist> taskChecklist = TaskChecklistCreator.createTaskChecklist();

    }

    @Test
    public void exportTasks_ExportJsonMultipleTaskChecklist() {
        List<TaskAppointment> taskAppointment = new ArrayList<>();
        List<TaskChecklist> taskChecklist = TaskChecklistCreator.createTaskChecklist();

    }

    @Test
    public void exportTasks_ExportXmlMultipleTaskChecklist() {
        List<TaskAppointment> taskAppointment = new ArrayList<>();
        List<TaskChecklist> taskChecklist = TaskChecklistCreator.createTaskChecklist();


    }

}