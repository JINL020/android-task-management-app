package at.ac.univie.se2_team_0308.utils.typeConverters;

import androidx.room.TypeConverter;

import at.ac.univie.se2_team_0308.models.EStatus;

public class EStatusTypeConverter {
    @TypeConverter
    public String fromStatus(EStatus status){
        return status.toString();
    }

    @TypeConverter
    public EStatus toStatus(String status){
        switch (status) {
            case "IN_PROGRESS":
                return EStatus.IN_PROGRESS;
            case "NOT_STARTED":
                return EStatus.NOT_STARTED;
            default:
                return EStatus.COMPLETED;
        }
    }
}
