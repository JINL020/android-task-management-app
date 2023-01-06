package at.ac.univie.se2_team_0308.utils;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.Subtask;

public class SubtasksConverter {

    @TypeConverter
    public String subtasksToJson(List<Subtask> subtasks) {
        Gson gson = new Gson();
        return gson.toJson(subtasks);
    }

    @TypeConverter
    public ArrayList<Subtask> jsonToSubtasks(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Subtask>>() {}.getType();
        return gson.fromJson(json, type);
    }

}
