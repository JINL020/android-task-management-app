package at.ac.univie.se2_team_0308.utils.typeConverters;

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
            case "LOW":
                return EPriority.LOW;
            case "MEDIUM":
                return EPriority.MEDIUM;
            default:
                return EPriority.HIGH;
        }
    }
}
