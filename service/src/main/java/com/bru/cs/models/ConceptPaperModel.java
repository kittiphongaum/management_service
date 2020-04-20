package com.bru.cs.models;

import java.util.List;

public class ConceptPaperModel {


    private UserModel userModel;
    private ProjectModel projectModel;
    private List <NotificationModel> notificationModel;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public ProjectModel getProjectModel() {
        return projectModel;
    }

    public void setProjectModel(ProjectModel projectModel) {
        this.projectModel = projectModel;
    }

    public List<NotificationModel> getNotificationModel() {
        return notificationModel;
    }

    public void setNotificationModel(List<NotificationModel> notificationModel) {
        this.notificationModel = notificationModel;
    }

  
    
}