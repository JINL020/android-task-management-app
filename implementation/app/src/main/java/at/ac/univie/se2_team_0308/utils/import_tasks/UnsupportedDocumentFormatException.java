package at.ac.univie.se2_team_0308.utils.import_tasks;

public class UnsupportedDocumentFormatException extends Exception {
    public UnsupportedDocumentFormatException(String message) {
        super(message);
    }

    public UnsupportedDocumentFormatException(){
        super("Document format not supported");
    }
}
