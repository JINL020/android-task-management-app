package at.ac.univie.se2_team_0308.views;

import androidx.fragment.app.DialogFragment;

import android.content.Context;

import androidx.fragment.app.Fragment;

import android.widget.Toast;

/**
 * Abstract class for Task List fragments on main activity
 */
public abstract class ATaskListFragment extends Fragment implements AddTaskFragment.AddTaskDialogListener,
        AddTaskFragment.SendDataFromAddDialog, PropertyToBeUpdated.SelectPropertyToUpdateDialogListener,
        PropertyToBeUpdated.SendDataFromSelectPropertyUpdateDialog {
    /**
     * Layout initialization function with steps that are overwritten on specific fragments
     */
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