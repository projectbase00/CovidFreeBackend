package com.app.covidfree.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A OtpCodes.
 */
@Entity
@Table(name = "otp_codes")
public class OtpCodes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "update_date")
    private ZonedDateTime updateDate;

    @OneToOne(mappedBy = "otpCodes")
    @JsonIgnore
    private MobileUser mobileUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public OtpCodes otpCode(String otpCode) {
        this.otpCode = otpCode;
        return this;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public OtpCodes createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public ZonedDateTime getUpdateDate() {
        return updateDate;
    }

    public OtpCodes updateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(ZonedDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public MobileUser getMobileUser() {
        return mobileUser;
    }

    public OtpCodes mobileUser(MobileUser mobileUser) {
        this.mobileUser = mobileUser;
        return this;
    }

    public void setMobileUser(MobileUser mobileUser) {
        this.mobileUser = mobileUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OtpCodes)) {
            return false;
        }
        return id != null && id.equals(((OtpCodes) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OtpCodes{" +
            "id=" + getId() +
            ", otpCode='" + getOtpCode() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
