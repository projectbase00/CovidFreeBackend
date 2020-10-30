package com.app.covidfree.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * A MobileUser.
 */
@Entity
@Table(name = "mobile_user")
public class MobileUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "citizen_id")
    private Integer citizenId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hash")
    private String hash;

    @Lob
    @Column(name = "idcard_image")
    private byte[] idcardImage;

    @Column(name = "idcard_image_content_type")
    private String idcardImageContentType;

    @Column(name = "valid")
    private Boolean valid;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @OneToOne
    @JoinColumn(unique = true)
    private OtpCodes otpCodes;

    @OneToMany(mappedBy = "logsByUser")
    private Set<EventLogging> eventLoggings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCitizenId() {
        return citizenId;
    }

    public MobileUser citizenId(Integer citizenId) {
        this.citizenId = citizenId;
        return this;
    }

    public void setCitizenId(Integer citizenId) {
        this.citizenId = citizenId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public MobileUser phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHash() {
        return hash;
    }

    public MobileUser hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public byte[] getIdcardImage() {
        return idcardImage;
    }

    public MobileUser idcardImage(byte[] idcardImage) {
        this.idcardImage = idcardImage;
        return this;
    }

    public void setIdcardImage(byte[] idcardImage) {
        this.idcardImage = idcardImage;
    }

    public String getIdcardImageContentType() {
        return idcardImageContentType;
    }

    public MobileUser idcardImageContentType(String idcardImageContentType) {
        this.idcardImageContentType = idcardImageContentType;
        return this;
    }

    public void setIdcardImageContentType(String idcardImageContentType) {
        this.idcardImageContentType = idcardImageContentType;
    }

    public Boolean isValid() {
        return valid;
    }

    public MobileUser valid(Boolean valid) {
        this.valid = valid;
        return this;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean isStatus() {
        return status;
    }

    public MobileUser status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public MobileUser createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public MobileUser updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public OtpCodes getOtpCodes() {
        return otpCodes;
    }

    public MobileUser otpCodes(OtpCodes otpCodes) {
        this.otpCodes = otpCodes;
        return this;
    }

    public void setOtpCodes(OtpCodes otpCodes) {
        this.otpCodes = otpCodes;
    }

    public Set<EventLogging> getEventLoggings() {
        return eventLoggings;
    }

    public MobileUser eventLoggings(Set<EventLogging> eventLoggings) {
        this.eventLoggings = eventLoggings;
        return this;
    }

    public MobileUser addEventLogging(EventLogging eventLogging) {
        this.eventLoggings.add(eventLogging);
        eventLogging.setLogsByUser(this);
        return this;
    }

    public MobileUser removeEventLogging(EventLogging eventLogging) {
        this.eventLoggings.remove(eventLogging);
        eventLogging.setLogsByUser(null);
        return this;
    }

    public void setEventLoggings(Set<EventLogging> eventLoggings) {
        this.eventLoggings = eventLoggings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MobileUser)) {
            return false;
        }
        return id != null && id.equals(((MobileUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MobileUser{" +
            "id=" + getId() +
            ", citizenId=" + getCitizenId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hash='" + getHash() + "'" +
            ", idcardImage='" + getIdcardImage() + "'" +
            ", idcardImageContentType='" + getIdcardImageContentType() + "'" +
            ", valid='" + isValid() + "'" +
            ", status='" + isStatus() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
