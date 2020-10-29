package com.app.covidfree.service.dto;

public class PhoneNumberDto {

    private Integer citizenId;
    private String phoneNumber;
    private String code;

    public PhoneNumberDto(Integer citizenId, String phoneNumber) {
        this.citizenId = citizenId;
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumberDto(Integer citizenId, String phoneNumber, String code) {
        this.citizenId = citizenId;
        this.phoneNumber = phoneNumber;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return String return the citizenId
     */
    public Integer getCitizenId() {
        return citizenId;
    }

    /**
     * @param citizenId the citizenId to set
     */
    public void setCitizenId(Integer citizenId) {
        this.citizenId = citizenId;
    }

    /**
     * @return String return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}