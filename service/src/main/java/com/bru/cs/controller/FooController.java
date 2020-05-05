package com.bru.cs.controller;

import java.util.List;

import com.bru.cs.models.ConcaptModel01;
import com.bru.cs.models.ConcaptModel02;
import com.bru.cs.models.ConceptPaperModel;
import com.bru.cs.models.NotificationModel;
import com.bru.cs.models.ProjectModel;
import com.bru.cs.models.SearcModel;
import com.bru.cs.models.UserModel;
import com.bru.cs.service.NotifictionService;
import com.bru.cs.service.ProjectService;
import com.bru.cs.service.ReportPDF01Service;
import com.bru.cs.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FooController {

    @Autowired
    private UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    NotifictionService notifictionService;

    @Autowired
    ReportPDF01Service reportPDF01Service;

    private MessageSource messageSource;

    public FooController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";
    @GetMapping(value = "/user/{userid}")
    public UserModel listUser(@PathVariable final Integer userid) {

        return userService.searchNotiProNo(userid);
    }

    @GetMapping(value = "/project/{prono}")
    public List<ProjectModel> listProject(@PathVariable final String prono) {

        return projectService.searchProjectNo(prono);
    }

    @GetMapping(value = "/noti/{prono}")
    public List<NotificationModel> listNoti(@PathVariable final String prono) {

        return notifictionService.searchNotiProNo(prono);
    }

    @GetMapping(value = "/concapt/{prono}")
    public List<ConcaptModel02> listConcapt(@PathVariable final String prono) {

        return reportPDF01Service.page2(prono);
    }

    @GetMapping(value = "/listsearcProNoStudent",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<UserModel> listsearcProNoStudent() {
        final SearcModel model = new SearcModel();
        return userService.searcProNoStudent(model);
    }

    @GetMapping(value = "/searcProNoTeacher",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<UserModel> listssearcProNoTeacher() {

        final SearcModel model = new SearcModel();
        return userService.searcProNoTeacher(model);
    }
}