package at.ac.univie.se2_team_0308.viewmodels;

public class OpenAttachmentException extends Exception {
    public OpenAttachmentException(String message) {
        super(message);
    }

    public OpenAttachmentException(){
        super("Cannot open attachment file");
    }
}
