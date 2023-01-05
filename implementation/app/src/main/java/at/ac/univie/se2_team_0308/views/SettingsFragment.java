package at.ac.univie.se2_team_0308.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private SettingsNotifierViewModel settingsNotifierViewModel;

    private CheckBox onCreatePopupCheckBox;
    private CheckBox onCreateBasicCheckBox;
    private CheckBox onUpdatePopupCheckBox;
    private CheckBox onUpdateBasicCheckBox;
    private CheckBox onDeletePopupCheckBox;
    private CheckBox onDeleteBasicCheckBox;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        settingsNotifierViewModel = new ViewModelProvider(getActivity()).get(SettingsNotifierViewModel.class);

        initViews();
        initLayout();
        initCheckboxListeners();

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

    private void initLayout() {
        settingsNotifierViewModel.getAllSettingsNotifier().observe(getViewLifecycleOwner(), new Observer<List<SettingsNotifier>>() {
            @Override
            public void onChanged(List<SettingsNotifier> settingsNotifiers) {
                for (SettingsNotifier sn : settingsNotifiers) {
                    if (sn.getEvent() == ENotificationEvent.CREATE) {
                        if (sn.isPopup()) {
                            onCreatePopupCheckBox.setChecked(true);
                        }
                        if (sn.isBasic()) {
                            onCreateBasicCheckBox.setChecked(true);
                        }
                    }
                    if (sn.getEvent() == ENotificationEvent.UPDATE) {
                        if (sn.isPopup()) {
                            onUpdatePopupCheckBox.setChecked(true);
                        }
                        if (sn.isBasic()) {
                            onUpdateBasicCheckBox.setChecked(true);
                        }
                    }
                    if (sn.getEvent() == ENotificationEvent.DELETE) {
                        if (sn.isPopup()) {
                            onDeletePopupCheckBox.setChecked(true);
                        }
                        if (sn.isBasic()) {
                            onDeleteBasicCheckBox.setChecked(true);
                        }
                    }
                }

                for (SettingsNotifier notifier : settingsNotifiers) {
                    Log.d("settings", settingsNotifiers.toString());
                }
            }
        });
    }

    private void initCheckboxListeners(){
        onCreatePopupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnCreateNotifier();
            }
        });

        onCreateBasicCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnCreateNotifier();
            }
        });

        onUpdatePopupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnUpdateNotifier();
            }
        });

        onUpdateBasicCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnUpdateNotifier();
            }
        });

        onDeletePopupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnDeleteNotifier();
            }
        });

        onDeleteBasicCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnDeleteNotifier();
            }
        });

    }

    private void updateOnCreateNotifier() {
        boolean onCreatePopup = onCreatePopupCheckBox.isChecked();
        boolean onCreateBasic = onCreateBasicCheckBox.isChecked();
        IObserver onCreateNotifier = buildNotifier(onCreatePopup, onCreateBasic);
        settingsNotifierViewModel.update(new SettingsNotifier(ENotificationEvent.CREATE, onCreateNotifier));
    }

    private void updateOnUpdateNotifier() {
        boolean onCreatePopup = onUpdatePopupCheckBox.isChecked();
        boolean onCreateBasic = onUpdateBasicCheckBox.isChecked();
        IObserver onUpdateNotifier = buildNotifier(onCreatePopup, onCreateBasic);
        settingsNotifierViewModel.update(new SettingsNotifier(ENotificationEvent.UPDATE, onUpdateNotifier));
    }

    private void updateOnDeleteNotifier() {
        boolean onDeletePopup = onDeletePopupCheckBox.isChecked();
        boolean onDeleteBasic = onDeleteBasicCheckBox.isChecked();
        IObserver onDeleteNotifier = buildNotifier(onDeletePopup, onDeleteBasic);
        settingsNotifierViewModel.update(new SettingsNotifier(ENotificationEvent.DELETE, onDeleteNotifier));
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