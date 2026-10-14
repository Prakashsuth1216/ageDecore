package com.agedecor.interiordesign.data;

import java.io.Serializable;

public class InquiryModel implements Serializable {
    private long id;
    private String clientName;
    private String phone;
    private String email;
    private String projectTitle;
    private String mumbaiLocality;
    private String propertyType;
    private String carpetArea;
    private String budgetRange;
    private String specialRequirements;
    private String timestamp;
    private String status;

    public InquiryModel(long id, String clientName, String phone, String email,
                        String projectTitle, String mumbaiLocality, String propertyType,
                        String carpetArea, String budgetRange, String specialRequirements,
                        String timestamp, String status) {
        this.id = id;
        this.clientName = clientName;
        this.phone = phone;
        this.email = email;
        this.projectTitle = projectTitle;
        this.mumbaiLocality = mumbaiLocality;
        this.propertyType = propertyType;
        this.carpetArea = carpetArea;
        this.budgetRange = budgetRange;
        this.specialRequirements = specialRequirements;
        this.timestamp = timestamp;
        this.status = status;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getClientName() { return clientName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getProjectTitle() { return projectTitle; }
    public String getMumbaiLocality() { return mumbaiLocality; }
    public String getPropertyType() { return propertyType; }
    public String getCarpetArea() { return carpetArea; }
    public String getBudgetRange() { return budgetRange; }
    public String getSpecialRequirements() { return specialRequirements; }
    public String getTimestamp() { return timestamp; }
    public String getStatus() { return status; }
}
