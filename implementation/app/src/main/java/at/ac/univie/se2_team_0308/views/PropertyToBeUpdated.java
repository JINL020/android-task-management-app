package at.ac.univie.se2_team_0308.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class PropertyToBeUpdated extends DialogFragment {

    public static final String TAG = "select_property_to_update_fragment";

    public PropertyToBeUpdated(ATaskListFragment taskListFragment) {
        try {
            listener = (SelectPropertyToUpdateDialogListener) taskListFragment;
            inputListener = (SendDataFromSelectPropertyUpdateDialog) taskListFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }
    }

    public interface SendDataFromSelectPropertyUpdateDialog {
        void sendDataResult(String propertyName);
    }

    public interface SelectPropertyToUpdateDialogListener {
        void onDialogPositiveClick(DialogFragment dialogFragment, Boolean wantToCloseDialog);
        void onDialogNegativeClick(DialogFragment dialogFragment, Boolean wantToCloseDialog);
    }

    private Spinner selectPropertySpinner;
    private TaskViewModel viewModel;
    private PropertyToBeUpdated.SelectPropertyToUpdateDialogListener listener;
    private PropertyToBeUpdated.SendDataFromSelectPropertyUpdateDialog inputListener;
    private Boolean wantToCloseDialog = false;
    private String toSendBack;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.property_to_be_updated, null, false);
        initViews(view);

        switch (selectPropertySpinner.getSelectedItem().toString()) {
            default:
                toSendBack = "Priority";
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // nothing, we override it later
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onDialogNegativeClick(PropertyToBeUpdated.this, true);
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputListener.sendDataResult(
                        toSendBack
                );
                listener.onDialogPositiveClick(PropertyToBeUpdated.this, true);
            }
        });
        return dialog;
    }

    private void initViews(View view) {
        selectPropertySpinner = view.findViewById(R.id.selectPropertyToUpdateSpinner);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
    }
}
