package com.app.covidfree.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A EventLogging.
 */
@Entity
@Table(name = "event_logging")
public class EventLogging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "citizen_id")
    private Integer citizenId;

    @Column(name = "log_type")
    private Integer logType;

    @Column(name = "message")
    private String message;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "eventLoggings", allowSetters = true)
    private MobileUser logsByUser;

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

    public EventLogging citizenId(Integer citizenId) {
        this.citizenId = citizenId;
        return this;
    }

    public void setCitizenId(Integer citizenId) {
        this.citizenId = citizenId;
    }

    public Integer getLogType() {
        return logType;
    }

    public EventLogging logType(Integer logType) {
        this.logType = logType;
        return this;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getMessage() {
        return message;
    }

    public EventLogging message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public EventLogging createDate(ZonedDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public MobileUser getLogsByUser() {
        return logsByUser;
    }

    public EventLogging logsByUser(MobileUser mobileUser) {
        this.logsByUser = mobileUser;
        return this;
    }

    public void setLogsByUser(MobileUser mobileUser) {
        this.logsByUser = mobileUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventLogging)) {
            return false;
        }
        return id != null && id.equals(((EventLogging) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventLogging{" +
            "id=" + getId() +
            ", citizenId=" + getCitizenId() +
            ", logType=" + getLogType() +
            ", message='" + getMessage() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            "}";
    }
}
