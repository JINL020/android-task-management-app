package at.ac.univie.se2_team_0308.utils.import_tasks;

import android.util.Pair;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskChecklist;

public interface TaskImporter {
    public Pair<TaskAppointment, TaskChecklist> importTasks() throws IOException, ParserConfigurationException, SAXException;
}
