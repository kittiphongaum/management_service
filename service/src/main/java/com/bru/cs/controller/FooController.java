package com.bru.cs.controller;

import java.util.List;

import com.bru.cs.models.ConcaptModel01;
import com.bru.cs.models.ConcaptModel02;
import com.bru.cs.models.ConceptPaperModel;
import com.bru.cs.models.NotificationModel;
import com.bru.cs.models.ProjectModel;
import com.bru.cs.models.UserModel;
import com.bru.cs.service.NotifictionService;
import com.bru.cs.service.ProjectService;
import com.bru.cs.service.ReportPDF01Service;
import com.bru.cs.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/user/{userid}")
    public UserModel listUser(@PathVariable Integer userid) {

        return userService.searchNotiProNo(userid);
    }

    @GetMapping(value = "/project/{prono}")
    public List<ProjectModel> listProject(@PathVariable String prono) {

        return projectService.searchProjectNo(prono);
    }

    @GetMapping(value = "/noti/{prono}")
    public List<NotificationModel> listNoti(@PathVariable String prono) {

        return notifictionService.searchNotiProNo(prono);
    }

    @GetMapping(value = "/concapt/{prono}")
    public List <ConcaptModel02> listConcapt(@PathVariable String prono) {
        
        return reportPDF01Service.page2(prono);
    }

}