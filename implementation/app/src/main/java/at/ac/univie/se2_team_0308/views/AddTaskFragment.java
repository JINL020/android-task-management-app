package at.ac.univie.se2_team_0308.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public class AddTaskFragment extends DialogFragment {

    public static final String TAG = "addtaskfragment";

    public interface SendDataFromAddDialog {
        void sendDataResult(String taskName, String taskDescription, EPriority priorityEnum, EStatus statusEnum, Date deadline, Boolean isSelectedAppointment);
    }

    public interface AddTaskDialogListener {
        void onDialogPositiveClick(DialogFragment dialogFragment);
        void onDialogNegativeClick(DialogFragment dialogFragment);
    }

    private EditText editTaskName;
    private RadioButton radioBtnAppointment;
    private RadioButton radioBtnChecklist;

    private AddTaskDialogListener listener;
    private SendDataFromAddDialog inputListener;
    private DatePicker spinnerDatePicker;

    private RelativeLayout relLayoutCard;
    private RelativeLayout relLayoutChooseDeadline;

    private TaskViewModel viewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_task_dialog_fragment, null);
        initViews(view);

        radioBtnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(relLayoutChooseDeadline.getVisibility() == View.GONE) {
                    relLayoutChooseDeadline.setVisibility(View.VISIBLE);
                }
            }
        });

        radioBtnChecklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relLayoutChooseDeadline.setVisibility(View.GONE);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String taskName = editTaskName.getText().toString();
                        Date deadline = Calendar.getInstance().getTime();
                        Boolean isSelectedAppointment = false;

                        if(radioBtnAppointment.isChecked()) {
                            isSelectedAppointment = true;
                            int day = spinnerDatePicker.getDayOfMonth();
                            int month = spinnerDatePicker.getMonth();
                            int year = spinnerDatePicker.getYear();

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, day);
                            deadline = new Date(calendar.getTimeInMillis());
                        }

                        Log.d(TAG, "onClick: task Name " + taskName);
                        inputListener.sendDataResult(
                                taskName,
                                "" ,
                                EPriority.LOW,
                                EStatus.NOT_STARTED,
                                deadline,
                                isSelectedAppointment
                        );
                        listener.onDialogPositiveClick(AddTaskFragment.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onDialogNegativeClick(AddTaskFragment.this);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddTaskDialogListener) context;
            inputListener = (SendDataFromAddDialog) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString());
        }
    }

    private void initViews(View view) {
        editTaskName = view.findViewById(R.id.editTaskName);

        radioBtnAppointment = view.findViewById(R.id.radioButtonAppointment);
        radioBtnAppointment.setChecked(false);

        radioBtnChecklist = view.findViewById(R.id.radioButtonChecklist);
        radioBtnChecklist.setChecked(false);

        spinnerDatePicker = view.findViewById(R.id.spinnerDatePicker);

        relLayoutChooseDeadline = view.findViewById(R.id.relLayoutChooseDeadline);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
    }
}