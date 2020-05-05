package com.bru.cs.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import javax.servlet.http.HttpServletResponse;

import com.bru.cs.service.NotifictionService;
import com.bru.cs.service.ProjectService;
import com.bru.cs.service.ReportAdminPage;
import com.bru.cs.service.ReportPDF01Service;
import com.bru.cs.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    ReportPDF01Service reportPDF01Service;

    @Autowired
    private UserService userService;

    @Autowired
    ReportAdminPage reportAdminPage;

    @Autowired
    ProjectService projectService;

    @Autowired
    NotifictionService notifictionService;

    @GetMapping(value = "/ReportStudent", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void getPurchaseTaxPDF(HttpServletResponse response) throws Exception {
        reportAdminPage.genStudenPDF(response);
    }

    @GetMapping(value = "/concept-paper/{prono}")
    @ResponseBody
    public void getPuPDF(HttpServletResponse response,@PathVariable String prono) throws Exception {
        reportPDF01Service.genPdfConceptPaper(response,prono);
    }

    @GetMapping(value = "/genReportTecherPDF")
    @ResponseBody
    public void getTecherPDF(HttpServletResponse response) throws Exception {
        reportAdminPage.genReportTecherPDF(response);
    }
}