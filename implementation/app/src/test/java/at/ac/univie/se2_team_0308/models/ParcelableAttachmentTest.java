package at.ac.univie.se2_team_0308.models;

import static org.junit.Assert.assertEquals;

import android.os.Parcel;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ParcelableAttachmentTest {

    @Test
    public void compareParcelableAttachmentToNormal_getSameValues() {
        Attachment test = new Attachment("test_a/test_b/test.xtest");

        Parcel parcel = Parcel.obtain();
        test.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        Attachment createdFromParcel = Attachment.CREATOR.createFromParcel(parcel);
        assertEquals(test, createdFromParcel);
    }
}
