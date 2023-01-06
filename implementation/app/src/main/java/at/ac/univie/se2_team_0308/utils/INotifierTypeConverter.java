package at.ac.univie.se2_team_0308.utils;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import at.ac.univie.se2_team_0308.models.ENotifier;
import at.ac.univie.se2_team_0308.utils.notifications.BasicNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.INotifier;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;
import at.ac.univie.se2_team_0308.utils.notifications.PopupNotifier;

public class INotifierTypeConverter {
    private static final String TAG = "TYPECONVERTER";

    @TypeConverter
    public String fromINotifier(INotifier notifier) {
        List<ENotifier> ret = notifier.getNotifierType();

        Gson gson = new Gson();
        String json = gson.toJson(ret);

        Log.d(TAG, "fromINotifier: " + json);
        return json;
    }

    @TypeConverter
    public INotifier toIObserver(String json) {
        Type listType = new TypeToken<List<ENotifier>>() {
        }.getType();

        List<ENotifier> result = new Gson().fromJson(json, listType);

        INotifier notifier = new LoggerCore();
        if (result.contains(ENotifier.POPUP)) {
            notifier = new PopupNotifier(notifier);
        }
        if (result.contains(ENotifier.BASIC)) {
            notifier = new BasicNotifier(notifier);
        }

        Log.d(TAG, "toINotifier: " + notifier.getNotifierType());

        return notifier;
    }
}
