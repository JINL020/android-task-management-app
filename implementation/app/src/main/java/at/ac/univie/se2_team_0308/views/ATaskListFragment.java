package at.ac.univie.se2_team_0308.views;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.databinding.FragmentListBinding;
import at.ac.univie.se2_team_0308.models.ATaskFactory;
import at.ac.univie.se2_team_0308.models.EPriority;
import at.ac.univie.se2_team_0308.models.EStatus;
import at.ac.univie.se2_team_0308.models.TaskAppointment;
import at.ac.univie.se2_team_0308.models.TaskAppointmentFactory;
import at.ac.univie.se2_team_0308.models.TaskChecklist;
import at.ac.univie.se2_team_0308.models.TaskChecklistFactory;
import at.ac.univie.se2_team_0308.utils.export.EFormat;
import at.ac.univie.se2_team_0308.utils.export.Exporter;
import at.ac.univie.se2_team_0308.utils.import_tasks.ImporterFacade;
import at.ac.univie.se2_team_0308.viewmodels.TaskListAdapter;
import at.ac.univie.se2_team_0308.viewmodels.TaskViewModel;

public abstract class ATaskListFragment extends Fragment implements AddTaskFragment.AddTaskDialogListener, AddTaskFragment.SendDataFromAddDialog, PropertyToBeUpdated.SelectPropertyToUpdateDialogListener, PropertyToBeUpdated.SendDataFromSelectPropertyUpdateDialog {

    public void initLayout() {
        initViews();
        initViewModel();
        initRecyclerViews();
        initUtils();
    }

    protected abstract void initViews();
    protected abstract void initViewModel();
    protected abstract void initRecyclerViews();
    protected abstract void initUtils();

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment, Boolean wantToCloseDialog) {
        if (wantToCloseDialog) {
            dialogFragment.dismiss();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialogFragment, Boolean wantToCloseDialog) {
        if (wantToCloseDialog) {
            dialogFragment.dismiss();
        }
    }

    protected void showToast(CharSequence text) {
        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}