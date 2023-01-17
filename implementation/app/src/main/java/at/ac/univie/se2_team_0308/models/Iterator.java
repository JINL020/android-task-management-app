package at.ac.univie.se2_team_0308.models;

public interface Iterator<T> {
    boolean hasNext();
    T next();
    T currentItem();
    void reset();
}
