package at.ac.univie.se2_team_0308.utils;

import androidx.room.TypeConverter;

import at.ac.univie.se2_team_0308.models.EPriority;

public class EPriorityTypeConverter {
    @TypeConverter
    public String fromPriority(EPriority priority){
        return priority.toString();
    }

    @TypeConverter
    public EPriority toPriority(String priority){
        switch (priority){
            case "MEDIUM":
                return EPriority.MEDIUM;
            case "LOW":
                return EPriority.LOW;
            default:
                return EPriority.HIGH;
        }
    }
}
