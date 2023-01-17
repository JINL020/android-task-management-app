package at.ac.univie.se2_team_0308.utils;

import android.util.Log;

import androidx.room.TypeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.ASubtask;
import at.ac.univie.se2_team_0308.models.SubtaskItem;
import at.ac.univie.se2_team_0308.models.SubtaskList;

/**
 * This is responsible for converting a complex subtask tree structure into json.
 */
public class SubtasksConverter {
    public static final String TAG = "SubtasksConverter";
    private EStatusTypeConverter statusConverter;
    public SubtasksConverter(){
        statusConverter = new EStatusTypeConverter();
    }

    private JSONArray getJsonArrSubtasks(List<ASubtask> subtasks) throws JSONException {
        JSONArray arr = new JSONArray();
        for(ASubtask sub: subtasks){
            JSONObject obj = new JSONObject();
            // put common attributes
            obj.put("name", sub.getName());
            obj.put("id", sub.getId());
            obj.put("state", statusConverter.fromStatus(sub.getState()));

            if(sub instanceof SubtaskList){
                obj.put("subtasks", getJsonArrSubtasks(sub.getSubtasks()));
            }
            arr.put(obj);
        }
        return arr;
    }

    @TypeConverter
    public String subtasksToJson(List<ASubtask> subtasks) {
        JSONArray subtasksArr = new JSONArray();
        try{
            subtasksArr = getJsonArrSubtasks(subtasks);
        } catch (JSONException e){
            Log.d(TAG, "Error on converting a list of ASubtask to JSON: " + e.getMessage());
        }
        return subtasksArr.toString();
    }

    private ArrayList<ASubtask> resolveSubtasks(JSONArray subtasksArr) throws JSONException {
        ArrayList<ASubtask> subtasks = new ArrayList<>();
        for (int i = 0; i < subtasksArr.length(); i++) {
            JSONObject obj = subtasksArr.getJSONObject(i);
            // get common attributes
            String name = obj.getString("name");
            int id = obj.getInt("id");
            EStatus state =  statusConverter.toStatus(obj.getString("state"));

            if(obj.has("subtasks")){
                ASubtask listSubtask = new SubtaskList(id, name, state);
                JSONArray arr = obj.getJSONArray("subtasks");
                listSubtask.setSubtasks(resolveSubtasks(arr));
                subtasks.add(listSubtask);
            } else {
                subtasks.add(new SubtaskItem(id, name, state));
            }
        }
        return subtasks;
    }

    @TypeConverter
    public ArrayList<ASubtask> jsonToSubtasks(String json)  {
        ArrayList<ASubtask> subtasks = new ArrayList<>();
        try{
            JSONArray subtasksArr = new JSONArray(json);
            subtasks = resolveSubtasks(subtasksArr);
        } catch (JSONException e){
            Log.d(TAG, "Error on deserializing a list of ASubtask: " + e.getMessage());
        }
        return subtasks;
    }

}
