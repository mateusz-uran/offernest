package io.github.mateuszuran.offernest.v2.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResumeEntity {
    private String note;
    private String pdfPath;
    private List<String> offers;

    @JsonCreator
    public ResumeEntity(@JsonProperty("pdfPath") String pdfPath,
                        @JsonProperty("offers") List<String> offers) {
        this.pdfPath = pdfPath;
        this.offers = offers;
    }

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

    @Override
    public String toString() {
        return "ResumeEntity{" +
                "note='" + note + '\'' +
                ", pdfPath='" + pdfPath + '\'' +
                ", offers=" + offers +
                '}';
    }
}
