package at.ac.univie.se2_team_0308.utils;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import at.ac.univie.se2_team_0308.utils.notifications.BasicNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.ENotifier;
import at.ac.univie.se2_team_0308.utils.notifications.INotifier;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;
import at.ac.univie.se2_team_0308.utils.notifications.PopupNotifier;

public class IObserverTypeConverter {
    @TypeConverter
    public String fromIObserver(INotifier observer) {
        List<ENotifier> ret = observer.getNotifierType();

        Gson gson = new Gson();
        String json = gson.toJson(ret);

        //Log.d("settings", json);
        return json;
    }

    @TypeConverter
    public INotifier toIObserver(String json) {
        //Log.d("settings", "json");
        Type listType = new TypeToken<List<ENotifier>>() {
        }.getType();

        List<ENotifier> result = new Gson().fromJson(json, listType);
        Log.d("ddd", result.toString());

        INotifier observer = new LoggerCore();
        if (result.contains(ENotifier.POPUP)) {
            observer = new PopupNotifier(observer);
        }
        if (result.contains(ENotifier.BASIC)) {
            observer = new BasicNotifier(observer);
        }

        return observer;
}
}
