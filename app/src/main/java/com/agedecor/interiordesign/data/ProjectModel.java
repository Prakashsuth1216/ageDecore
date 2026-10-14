package com.agedecor.interiordesign.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectModel implements Serializable {
    private int id;
    private String title;
    private String category;
    private String mumbaiLocality;
    private int carpetAreaSqFt;
    private String priceRange;
    private String ratePerSqFt;
    private int durationDays;
    private String designStyle;
    private String description;
    private List<String> highlights;
    private boolean isFavorite;

    public ProjectModel(int id, String title, String category, String mumbaiLocality,
                        int carpetAreaSqFt, String priceRange, String ratePerSqFt,
                        int durationDays, String designStyle, String description,
                        List<String> highlights) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.mumbaiLocality = mumbaiLocality;
        this.carpetAreaSqFt = carpetAreaSqFt;
        this.priceRange = priceRange;
        this.ratePerSqFt = ratePerSqFt;
        this.durationDays = durationDays;
        this.designStyle = designStyle;
        this.description = description;
        this.highlights = highlights != null ? highlights : new ArrayList<>();
        this.isFavorite = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public String getMumbaiLocality() { return mumbaiLocality; }
    public int getCarpetAreaSqFt() { return carpetAreaSqFt; }
    public String getPriceRange() { return priceRange; }
    public String getRatePerSqFt() { return ratePerSqFt; }
    public int getDurationDays() { return durationDays; }
    public String getDesignStyle() { return designStyle; }
    public String getDescription() { return description; }
    public List<String> getHighlights() { return highlights; }
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}
