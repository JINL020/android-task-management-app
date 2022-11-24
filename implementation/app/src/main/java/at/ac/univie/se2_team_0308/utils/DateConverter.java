package at.ac.univie.se2_team_0308.utils;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    //START https://stackoverflow.com/questions/50313525/room-using-date-field
    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
    //END https://stackoverflow.com/questions/50313525/room-using-date-field
}
