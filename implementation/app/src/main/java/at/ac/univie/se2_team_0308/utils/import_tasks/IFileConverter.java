package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Pair;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

/**
 * IFileConverter is an interface that defines a method for converting a string in a format defined
 * by the implementations of the interface to a list of TaskAppointment and a list of TaskChecklist.
 */
public interface IFileConverter {
    /**
     * Imports tasks as string from a file and converts them into tasks.
     * @return a Pair object containing  a list of TaskAppointment and a list of TaskChecklist.
     */
    public Pair<List<TaskAppointment>, List<TaskChecklist>> convertTasks() throws IOException, ParserConfigurationException, SAXException;
}
