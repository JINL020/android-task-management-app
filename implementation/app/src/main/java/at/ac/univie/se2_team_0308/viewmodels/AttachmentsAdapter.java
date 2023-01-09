package at.ac.univie.se2_team_0308.viewmodels;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import at.ac.univie.se2_team_0308.R;
import at.ac.univie.se2_team_0308.models.Attachment;

public class AttachmentsAdapter  extends RecyclerView.Adapter<AttachmentsAdapter.ViewHolder> {
    public static final String TAG = "AttachmentsAdapter";
    private Context context;
    List<Attachment> attachments;

    public AttachmentsAdapter(Context context){
        this.context = context;
        this.attachments = new ArrayList<>();
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        if(attachments == null) return;
        this.attachments = attachments;
        notifyDataSetChanged();
    }

    public void addAttachment(Attachment a){
        this.attachments.add(a);
        notifyDataSetChanged();
    }

    public void removeAttachment(Attachment a){
        this.attachments.remove(a);
        notifyDataSetChanged();
    }

    public void removeAttachment(int index){
        this.attachments.remove(index);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AttachmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_attachment, parent, false);
        return new AttachmentsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AttachmentsAdapter.ViewHolder holder, int position) {
        holder.txtFileName.setText(this.attachments.get(position).getBaseName());
        holder.textExtension.setText(this.attachments.get(position).getExtension().toUpperCase(Locale.ROOT));
        holder.deleteFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAttachment(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.attachments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textExtension;
        private TextView txtFileName;
        private ImageButton deleteFileBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textExtension = itemView.findViewById(R.id.textExtension);
            txtFileName = itemView.findViewById(R.id.txtFileName);
            deleteFileBtn = itemView.findViewById(R.id.deleteFileBtn);
        }
    }

}













