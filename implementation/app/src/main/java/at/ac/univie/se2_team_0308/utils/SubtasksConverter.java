package at.ac.univie.se2_team_0308.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SubtasksConverter {

    @TypeConverter
    public String subtasksToJson(List<String> subtasks) {
        Gson gson = new Gson();
        return gson.toJson(subtasks);
    }

    @TypeConverter
    public ArrayList<String> jsonToSubtasks(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

}
