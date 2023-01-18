package at.ac.univie.se2_team_0308.utils.typeConverters;

import androidx.room.TypeConverter;

import at.ac.univie.se2_team_0308.models.ECategory;

public class ECategoryTypeConverter {
    @TypeConverter
    public String fromCategory(ECategory category){
        return category.toString();
    }

    @TypeConverter
    public ECategory toCategory(String category){
        switch(category){
            case "APPOINTMENT":
                return ECategory.APPOINTMENT;
            default:
                return ECategory.CHECKLIST;
        }
    }
}
