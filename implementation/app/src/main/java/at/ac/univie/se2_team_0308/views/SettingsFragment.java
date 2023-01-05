package at.ac.univie.se2_team_0308.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import at.ac.univie.se2_team_0308.databinding.FragmentSettingsBinding;
import at.ac.univie.se2_team_0308.models.ENotificationEvent;
import at.ac.univie.se2_team_0308.utils.notifications.BasicNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.IObserver;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;
import at.ac.univie.se2_team_0308.utils.notifications.PopupNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.SettingsNotifier;
import at.ac.univie.se2_team_0308.viewmodels.SettingsNotifierViewModel;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private SettingsNotifierViewModel settingsNotifierViewModel;

    private CheckBox onCreatePopupCheckBox;
    private CheckBox onCreateBasicCheckBox;
    private CheckBox onUpdatePopupCheckBox;
    private CheckBox onUpdateBasicCheckBox;
    private CheckBox onDeletePopupCheckBox;
    private CheckBox onDeleteBasicCheckBox;

    private IObserver onCreateNotifier = new LoggerCore();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        settingsNotifierViewModel = new ViewModelProvider(getActivity()).get(SettingsNotifierViewModel.class);
        settingsNotifierViewModel.getAllSettingsNotifier().observe(getViewLifecycleOwner(), new Observer<List<SettingsNotifier>>() {
            @Override
            public void onChanged(List<SettingsNotifier> settingsNotifiers) {
                for(SettingsNotifier sn : settingsNotifiers){
                    Log.d("settings", sn.toString());
                }
            }
        });

        initViews();

        onCreatePopupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean onCreatePopup = onCreatePopupCheckBox.isChecked();
                boolean onCreateBasic = onCreateBasicCheckBox.isChecked();
                IObserver onCreateNotifier = buildNotifier(onCreatePopup, onCreateBasic);
                //settingsNotifierViewModel.

            }
        });

        onCreateBasicCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean onCreatePopup = onCreatePopupCheckBox.isChecked();
                boolean onCreateBasic = onCreateBasicCheckBox.isChecked();
                IObserver onCreateNotifier = buildNotifier(onCreatePopup, onCreateBasic);
                settingsNotifierViewModel.deleteAll();
                //settingsNotifierViewModel.insert(new SettingsNotifier(ENotificationEvent.CREATE, onCreateNotifier));
                //settingsNotifierViewModel.update(new SettingsNotifier(ENotificationEvent.CREATE, onCreateNotifier));
                //String s = settingsNotifierViewModel.getAllSettingsNotifier().toString();
                //Log.d("settings",s);
            }
        });

        onUpdatePopupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean onCreatePopup = onUpdatePopupCheckBox.isChecked();
                boolean onCreateBasic = onUpdateBasicCheckBox.isChecked();
                IObserver onUpdateNotifier = buildNotifier(onCreatePopup, onCreateBasic);
            }
        });

        onUpdateBasicCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean onCreatePopup = onUpdatePopupCheckBox.isChecked();
                boolean onCreateBasic = onUpdateBasicCheckBox.isChecked();
                IObserver onUpdateNotifier = buildNotifier(onCreatePopup, onCreateBasic);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initViews() {
        onCreatePopupCheckBox = binding.checkBoxOnCreatePopup;
        onCreateBasicCheckBox = binding.checkBoxOnCreateBasic;
        onUpdatePopupCheckBox = binding.checkBoxOnUpdatePopup;
        onUpdateBasicCheckBox = binding.checkBoxOnUpdateBasic;
        onDeletePopupCheckBox = binding.checkBoxOnDeletePopup;
        onDeleteBasicCheckBox = binding.checkBoxOnDeleteBasic;
    }

    private IObserver buildNotifier(boolean popup, boolean basic) {
        IObserver notifier = new LoggerCore();
        if (popup) {
            notifier = new PopupNotifier(notifier);
        }
        if (basic) {
            notifier = new BasicNotifier(notifier);
        }
        //Log.d("settings", notifier.getNotifierType().toString());
        return notifier;
    }

}