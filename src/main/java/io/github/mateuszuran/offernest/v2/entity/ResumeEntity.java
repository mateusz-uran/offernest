package io.github.mateuszuran.offernest.v2.entity;

import java.util.List;

public class ResumeEntity {
    private String note;
    private String pdfPath;
    private List<String> offers;

    public ResumeEntity(String note, String pdfPath, List<String> offers) {
        this.note = note;
        this.pdfPath = pdfPath;
        this.offers = offers;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public List<String> getOffers() {
        return offers;
    }

    public void setOffers(List<String> offers) {
        this.offers = offers;
    }
}
