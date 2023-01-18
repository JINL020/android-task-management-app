package at.ac.univie.se2_team_0308.models;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

// test children state
@RunWith(AndroidJUnit4.class)
public class SubtaskTest {

    ASubtask subtask;

    @Before
    public void setUp() {
        subtask = new SubtaskList("sub");
        subtask.addSubtask(new SubtaskItem("sub1"));
        subtask.addSubtask(new SubtaskItem("sub2"));
    }

    @Test
    public void changeParentState_childrenShouldHaveSameState(){
        subtask.setState(EStatus.COMPLETED);
        assertTrue(subtask.getSubtasks().get(0).getState() == EStatus.COMPLETED);
        assertTrue(subtask.getSubtasks().get(1).getState() == EStatus.COMPLETED);
    }
}
