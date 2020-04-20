package com.bru.cs.models;

/**
 * NotificationModel
 */
public class NotificationModel {

    private String id;
    private String proNo;
    private String message;
    private String statusCode;
    private String createdDate;
    private String createdBy;
    private String createdByTeach;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByTeach() {
        return createdByTeach;
    }

    public void setCreatedByTeach(String createdByTeach) {
        this.createdByTeach = createdByTeach;
    }

}