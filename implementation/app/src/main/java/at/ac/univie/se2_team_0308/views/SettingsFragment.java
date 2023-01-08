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
import at.ac.univie.se2_team_0308.utils.notifications.EventNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.INotifier;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;
import at.ac.univie.se2_team_0308.utils.notifications.PopupNotifier;
import at.ac.univie.se2_team_0308.viewmodels.EventNotifierViewModel;

public class SettingsFragment extends Fragment {
    private static final String TAG = "SETTINGS_FRAGMENT";

    private FragmentSettingsBinding binding;
    private EventNotifierViewModel eventNotifierViewModel;

    private CheckBox onCreatePopupCheckBox;
    private CheckBox onCreateBasicCheckBox;
    private CheckBox onUpdatePopupCheckBox;
    private CheckBox onUpdateBasicCheckBox;
    private CheckBox onDeletePopupCheckBox;
    private CheckBox onDeleteBasicCheckBox;
    private CheckBox onAppointmentPopupCheckBox;
    private CheckBox onAppointmentBasicCheckBox;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        eventNotifierViewModel = new ViewModelProvider(getActivity()).get(EventNotifierViewModel.class);

        initViews();
        initCheckboxListeners();
        initCheckboxLayout();

        return binding.getRoot();
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
        onAppointmentPopupCheckBox = binding.checkBoxOnAppointmentPopup;
        onAppointmentBasicCheckBox = binding.checkBoxOnAppointmentBasic;
    }

    private void initCheckboxListeners() {
        onCreatePopupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnCreateNotifier();
                Log.d(TAG,"(!)selected and updated onCreate popup Notification Settings");
            }
        });

        onCreateBasicCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnCreateNotifier();
                Log.d(TAG,"(!)selected and updated onCreate basic Notification Settings");
            }
        });

        onUpdatePopupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnUpdateNotifier();
                Log.d(TAG,"(!)selected and updated onUpdate popup Notification Settings");
            }
        });

        onUpdateBasicCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnUpdateNotifier();
                Log.d(TAG,"(!)selected and updated onUpdate basic Notification Settings");
            }
        });

        onDeletePopupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnDeleteNotifier();
                Log.d(TAG,"(!)selected and updated onDelete popup Notification Settings");
            }
        });

        onDeleteBasicCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnDeleteNotifier();
                Log.d(TAG,"(!)selected and updated onDelete basic Notification Settings");
            }
        });

        onAppointmentPopupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnAppointmentNotifier();
                Log.d(TAG,"(!)selected and updated onAppointment popup Notification Settings");
            }
        });

        onAppointmentBasicCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnAppointmentNotifier();
                Log.d(TAG,"(!)selected and updated onAppointment popup Notification Settings");
            }
        });
    }

    private void initCheckboxLayout() {
        eventNotifierViewModel.getAllNotifiers().observe(getViewLifecycleOwner(), new Observer<List<EventNotifier>>() {
            @Override
            public void onChanged(List<EventNotifier> eventNotifiers) {
                for (EventNotifier notifier : eventNotifiers) {
                    boolean isPopup = notifier.isPopup();
                    boolean isBasic = notifier.isBasic();
                    if (notifier.getEvent() == ENotificationEvent.CREATE) {
                        onCreatePopupCheckBox.setChecked(isPopup);
                        onCreateBasicCheckBox.setChecked(isBasic);
                    }
                    if (notifier.getEvent() == ENotificationEvent.UPDATE) {
                        onUpdatePopupCheckBox.setChecked(isPopup);
                        onUpdateBasicCheckBox.setChecked(isBasic);
                    }
                    if (notifier.getEvent() == ENotificationEvent.DELETE) {
                        onDeletePopupCheckBox.setChecked(isPopup);
                        onDeleteBasicCheckBox.setChecked(isBasic);
                    }
                    if (notifier.getEvent() == ENotificationEvent.APPOINTMENT) {
                        onAppointmentPopupCheckBox.setChecked(isPopup);
                        onAppointmentBasicCheckBox.setChecked(isBasic);
                    }
                }
            }
        });
    }

    private void updateOnCreateNotifier() {
        boolean onCreatePopup = onCreatePopupCheckBox.isChecked();
        boolean onCreateBasic = onCreateBasicCheckBox.isChecked();
        INotifier onCreateNotifier = buildNotifier(onCreatePopup, onCreateBasic);
        eventNotifierViewModel.update(new EventNotifier(ENotificationEvent.CREATE, onCreateNotifier));
    }

    private void updateOnUpdateNotifier() {
        boolean onCreatePopup = onUpdatePopupCheckBox.isChecked();
        boolean onCreateBasic = onUpdateBasicCheckBox.isChecked();
        INotifier onUpdateNotifier = buildNotifier(onCreatePopup, onCreateBasic);
        eventNotifierViewModel.update(new EventNotifier(ENotificationEvent.UPDATE, onUpdateNotifier));
    }

    private void updateOnDeleteNotifier() {
        boolean onDeletePopup = onDeletePopupCheckBox.isChecked();
        boolean onDeleteBasic = onDeleteBasicCheckBox.isChecked();
        INotifier onDeleteNotifier = buildNotifier(onDeletePopup, onDeleteBasic);
        eventNotifierViewModel.update(new EventNotifier(ENotificationEvent.DELETE, onDeleteNotifier));
    }

    private void updateOnAppointmentNotifier() {
        boolean onAppointmentPopup = onAppointmentPopupCheckBox.isChecked();
        boolean onAppointmentBasic = onAppointmentBasicCheckBox.isChecked();
        INotifier notifier = buildNotifier(onAppointmentPopup, onAppointmentBasic);
        eventNotifierViewModel.update(new EventNotifier(ENotificationEvent.APPOINTMENT, notifier));
    }

    private INotifier buildNotifier(boolean popup, boolean basic) {
        INotifier notifier = new LoggerCore();
        if (popup) {
            notifier = new PopupNotifier(notifier);
        }
        if (basic) {
            notifier = new BasicNotifier(notifier);
        }
        return notifier;
    }

}