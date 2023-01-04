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

import at.ac.univie.se2_team_0308.databinding.FragmentSettingsBinding;
import at.ac.univie.se2_team_0308.utils.notifications.BasicNotifier;
import at.ac.univie.se2_team_0308.utils.notifications.IObserver;
import at.ac.univie.se2_team_0308.utils.notifications.LoggerCore;
import at.ac.univie.se2_team_0308.utils.notifications.PopupNotifier;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

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

        initViews();

        onCreatePopupCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean onCreatePopup = onCreatePopupCheckBox.isChecked();
                boolean onCreateBasic = onCreateBasicCheckBox.isChecked();
                IObserver onCreateNotifier = buildNotifier(onCreatePopup, onCreateBasic);
            }
        });

        onCreateBasicCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean onCreatePopup = onCreatePopupCheckBox.isChecked();
                boolean onCreateBasic = onCreateBasicCheckBox.isChecked();
                IObserver onCreateNotifier = buildNotifier(onCreatePopup, onCreateBasic);
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
        Log.d("settings", notifier.getNotifierType().toString());
        return notifier;
    }

}