package at.ac.univie.se2_team_0308.utils.export;

import junit.framework.TestCase;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.TaskAppointmentCreator;
import at.ac.univie.se2_team_0308.TaskChecklistCreator;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public class ExporterTest extends TestCase {
    private Exporter exporter = new Exporter();

    @Test
    public void exportJsonMultipleTaskAppointment_FileExists() {
        List<TaskAppointment> taskAppointment = TaskAppointmentCreator.createTaskAppointment();
        List<TaskChecklist> taskChecklist = new ArrayList<>();
        EFormat format = EFormat.JSON;

        exporter.exportTasks(taskAppointment, taskChecklist, format);
//        File file = new File(fileName);
//        public static void main(String[] args) {
//            String fileName = "test.txt";
//
//
//            if (file.exists()) {
//                System.out.println("File " + fileName + " exists on the system.");
//            } else {
//                System.out.println("File " + fileName + " does not exist on the system.");
//            }

    }

    @Test
    public void exportXmlMultipleTaskAppointment_FileExists() {
        List<TaskAppointment> taskAppointment = TaskAppointmentCreator.createTaskAppointment();
        List<TaskChecklist> taskChecklist = new ArrayList<>();
        EFormat format = EFormat.XML;

        exporter.exportTasks(taskAppointment, taskChecklist, format);
    }

    @Test
    public void exportJsonTaskAppointmentTaskChecklist_FileExists() {
        List<TaskAppointment> taskAppointment = TaskAppointmentCreator.createTaskAppointment();
        List<TaskChecklist> taskChecklist = TaskChecklistCreator.createTaskChecklist();
        EFormat format = EFormat.JSON;

        exporter.exportTasks(taskAppointment, taskChecklist, format);
    }

    @Test
    public void exportXmlTaskAppointmentTaskChecklist_FileExists() {
        List<TaskAppointment> taskAppointment = TaskAppointmentCreator.createTaskAppointment();
        List<TaskChecklist> taskChecklist = TaskChecklistCreator.createTaskChecklist();
        EFormat format = EFormat.XML;

        exporter.exportTasks(taskAppointment, taskChecklist, format);
    }

    @Test
    public void exportJsonMultipleTaskChecklist_FileExists() {
        List<TaskAppointment> taskAppointment = new ArrayList<>();
        List<TaskChecklist> taskChecklist = TaskChecklistCreator.createTaskChecklist();
        EFormat format = EFormat.JSON;

        exporter.exportTasks(taskAppointment, taskChecklist, format);
    }

    @Test
    public void exportXmlMultipleTaskChecklist_FileExists() {
        List<TaskAppointment> taskAppointment = new ArrayList<>();
        List<TaskChecklist> taskChecklist = TaskChecklistCreator.createTaskChecklist();
        EFormat format = EFormat.XML;

        exporter.exportTasks(taskAppointment, taskChecklist, format);
    }
}