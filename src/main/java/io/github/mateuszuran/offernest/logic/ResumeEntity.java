package main.java.io.github.mateuszuran.offernest.logic;

public class ResumeEntity {

    public ResumeEntity(String note, String pdfDirUri) {
        this.note = note;
        this.pdfDirUri = pdfDirUri;
    }

    private final String note;
    private final String pdfDirUri;

    public String getNote() {
        return note;
    }

    public String getPdfDirUri() {
        return pdfDirUri;
    }

    public void showCreatedResume() {
        System.out.println("Created resume: " + this.note + " with url: " + this.pdfDirUri);
    }
}
