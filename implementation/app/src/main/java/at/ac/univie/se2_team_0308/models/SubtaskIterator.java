package at.ac.univie.se2_team_0308.models;

public class SubtaskIterator implements Iterator<ASubtask> {
    private ASubtask[] subtasks;
    private int position;

    public SubtaskIterator(ASubtask[] topics)
    {
        this.subtasks = topics;
        position = 0;
    }

    @Override
    public void reset() {
        position = 0;
    }

    @Override
    public ASubtask next() {
        return subtasks[position++];
    }

    @Override
    public ASubtask currentItem() {
        return subtasks[position];
    }

    @Override
    public boolean hasNext() {
        if(position >= subtasks.length)
            return false;
        return true;
    }
}
