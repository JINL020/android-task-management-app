package at.ac.univie.se2_team_0308.utils;
import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import at.ac.univie.se2_team_0308.models.Attachment;

public class AttachmentConverter {

    @TypeConverter
    public String attachmentsToJson(List<Attachment> subtasks) {
        Gson gson = new Gson();
        return gson.toJson(subtasks);
    }

    @TypeConverter
    public ArrayList<Attachment> jsonToAttachments(String json)  {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Attachment>>() {}.getType();
        return gson.fromJson(json, type);
    }
}