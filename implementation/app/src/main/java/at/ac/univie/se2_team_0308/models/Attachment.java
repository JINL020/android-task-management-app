package at.ac.univie.se2_team_0308.models;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

import java.io.File;
import java.util.Objects;

@Entity(tableName = "file_attachments")
public class Attachment implements Parcelable {

    private String filePath;
    private String baseName;
    private String extension;

    public Attachment(String filePath){
        this.filePath = filePath;
        File f = new File(filePath);
        String fileName = f.getName();
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            this.extension = fileName.substring(index + 1);
            this.baseName = fileName.substring(0, index);
        }
    }

    protected Attachment(Parcel in) {
        filePath = in.readString();
        baseName = in.readString();
        extension = in.readString();
    }

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(Parcel in) {
            return new Attachment(in);
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.filePath);
        parcel.writeString(this.baseName);
        parcel.writeString(this.extension);
    }
    @Override
    public String toString() {
        return "Attachment{" +
                "filePath=" + this.filePath +
                "baseName=" + this.baseName +
                "extension=" + this.extension +
                "}";
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment attachment = (Attachment) o;
        return this.filePath == attachment.filePath && this.extension == attachment.getExtension() && this.baseName == attachment.getBaseName();
    }
}
