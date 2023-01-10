package at.ac.univie.se2_team_0308.views;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.io.FileOutputStream;
import at.ac.univie.se2_team_0308.R;

public class SketchFragment extends DialogFragment {
    private SketchView sketchView;
    private Button undoBtn;

    private SendDataFromSketchDialog inputListener;
    public interface SendDataFromSketchDialog {
        void sendDataResult(Bitmap bitmap);
    }

    public SketchFragment(TaskActivity activity){
        inputListener = activity;
    }
    public SketchFragment(AddTaskFragment fragment){
        inputListener = fragment;
    }
    private void initViews(View view){
        sketchView = view.findViewById(R.id.sketchViewId);
        undoBtn = view.findViewById(R.id.undoBtn);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_sketch, null, false);

        initViews(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view).setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputListener.sendDataResult(sketchView.getDrawingCache());
                dismiss();
            }
        });
        return dialog;
    }
}
