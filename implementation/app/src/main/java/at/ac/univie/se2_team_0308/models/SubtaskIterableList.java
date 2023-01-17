package at.ac.univie.se2_team_0308.models;

import java.util.ArrayList;
import java.util.List;

public class SubtaskIterableList implements IterableList<ASubtask> {
    private ASubtask[] subtasks;

    public SubtaskIterableList(ASubtask[] subtasks)
    {
        List<ASubtask> allSubtasks = new ArrayList<>();
        for(ASubtask sub: subtasks){
            allSubtasks.add(sub);
            if(sub instanceof SubtaskList){
                SubtaskList list = (SubtaskList) sub;
                allSubtasks.addAll(list.getSubtasks());
            }
        }
        this.subtasks = allSubtasks.toArray(new ASubtask[allSubtasks.size()]);
    }

    @Override
    public Iterator<ASubtask> iterator() {
        return new SubtaskIterator(subtasks);
    }
}
