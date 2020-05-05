package com.bru.cs.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.bru.cs.models.ConcaptModel01;
import com.bru.cs.models.ConcaptModel02;
import com.bru.cs.models.ConceptPaperModel;
import com.bru.cs.models.NotificationModel;
import com.bru.cs.models.ProjectModel;
import com.bru.cs.models.SearcModel;
import com.bru.cs.models.UserModel;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

/**
 * ReportPDF01
 */
@Service
public class ReportPDF01Service {

    @Autowired
    private UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    NotifictionService notifictionService;

    @Autowired
    private org.springframework.context.ApplicationContext context;

   

    public void genPdfConceptPaper(HttpServletResponse response, String prono) {

        List<JasperPrint> jasperPrintList = new ArrayList<>();

        // String[] jrxmlList = {"succor1PDF.jrxml","succor2PDF.jrxml"};
        Resource resource1 = context.getResource("classpath:reports/conbru1.jrxml");
        Resource resource2 = context.getResource("classpath:reports/conbru2.jrxml");
        Resource[] resources = { resource1, resource2 };
        int index = 0;
        for (Resource resource : resources) {
            // Resource resource =
            // context.getResource("classpath:reports/beneficiaryPDF.jrxml");
            // Resource resource = context.getResource("classpath:reports/"+jrxml);
            InputStream inputStream = null;
            try {
                inputStream = resource.getInputStream();
                JasperReport report = JasperCompileManager.compileReport(inputStream);

                Map<String, Object> params = new HashMap<>();
                JRDataSource dataSource = null;
                if (index == 0) {
                    ConcaptModel01 reportSource = part1(prono);
                    params.put("reportSource", reportSource);
                    dataSource = new JREmptyDataSource();
                } else {

                    List <ConcaptModel02> data = page2(prono);

                    if (data != null && data.size() > 0) {
                    dataSource = new JRBeanCollectionDataSource(data);
                    } else {
                        dataSource = new JREmptyDataSource();
                    }
                    params.put("reportSource", dataSource);
                }

                JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
                jasperPrintList.add(jasperPrint);
                index++;
            } catch (IOException e) {
                System.out.println(e);
            } catch (JRException e) {
                System.out.println(e);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
        }

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            System.out.println(e);
        }

        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=CNK.pdf");

        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));// "employeeReport.pdf"));

        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor("imake");
        exportConfig.setEncrypted(true);
        exportConfig.setAllowedPermissionsHint("PRINTING");

        exportConfig.setCreatingBatchModeBookmarks(true);
        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);

        try {
            exporter.exportReport();
        } catch (JRException e) {
            System.out.println(e);
        }
    }

    public List<ConceptPaperModel> searchConcapt(String pro_on) {

        List<ProjectModel> projectModels = new ArrayList<>();
        List<ConceptPaperModel> conceptPaperModels = new ArrayList<>();
        try {
            projectModels = projectService.searchProjectNo(pro_on);
            for (ProjectModel projectModel : projectModels) {
                UserModel userModels = userService.searchNotiProNo(Integer.parseInt(projectModel.getStudentId()));
                List<NotificationModel> notificationModels = notifictionService.searchNotiProNo(pro_on);
                ConceptPaperModel conceptPaperModel = new ConceptPaperModel();
                if (notificationModels.size() > 0) {
                    conceptPaperModel.setNotificationModel(notificationModels);
                }
                conceptPaperModel.setProjectModel(projectModel);
                conceptPaperModel.setUserModel(userModels);
                conceptPaperModels.add(conceptPaperModel);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return conceptPaperModels;
    }

    public ConcaptModel01 part1(String pro_no) {

        ConcaptModel01 concaptModel01 = new ConcaptModel01();
        List<ProjectModel> projectModels = new ArrayList<>();

        String term = "";
        String generation = "";
        String problemLabel1 = "";
        String problemLabel2 = "";
        String problemLabel3 = "";
        String objectivelabel1 = "";
        String objectivelabel2 = "";
        String objectivelabel3 = "";
        String scopelabel1 = "";
        String scopelabel2 = "";
        String scopelabel3 = "";
        String toollabel1 = "";
        String toollabel2 = "";

        String day = "";
        String mount = "";
        String year = "";

        try {

            projectModels = projectService.searchProjectNo(pro_no);
            for (ProjectModel projectModel : projectModels) {

                UserModel userModels = userService.searchNotiProNo(Integer.parseInt(projectModel.getStudentId()));
                term = "1";
                // generation = projectModel.get;
                problemLabel1 = projectModel.getProblem();
                // problemLabel2 = projectModel.get;
                // problemLabel3 = projectModel.get;
                objectivelabel1 = projectModel.getObjective();
                // objectivelabel2 = projectModel.get;
                // objectivelabel3 = projectModel.get;
                scopelabel1 = projectModel.getScope();
                // scopelabel2 = projectModel.get;
                // scopelabel3 = projectModel.get;
                toollabel1 = projectModel.getTool();
                // toollabel2 = projectModel.get;
                day = "02";
                mount = "มี.ค";
                year = "2563";

                concaptModel01.setUser(userModels);
                concaptModel01.setProject(projectModel);
                concaptModel01.setTerm(term);
                concaptModel01.setGeneration(generation);
                concaptModel01.setProblemLabel1(problemLabel1);
                concaptModel01.setProblemLabel2(problemLabel2);
                concaptModel01.setProblemLabel3(problemLabel3);
                concaptModel01.setObjectivelabel1(objectivelabel1);
                concaptModel01.setObjectivelabel2(objectivelabel2);
                concaptModel01.setObjectivelabel3(objectivelabel3);
                concaptModel01.setScopelabel1(scopelabel1);
                concaptModel01.setScopelabel2(scopelabel2);
                concaptModel01.setScopelabel3(scopelabel3);
                concaptModel01.setToollabel1(toollabel1);
                concaptModel01.setToollabel2(toollabel2);

                concaptModel01.setDay(day);
                concaptModel01.setMount(mount);
                concaptModel01.setYear(year);

            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return concaptModel01;
    }

    public List<ConcaptModel02> page2(String pro_no) {

        ConcaptModel02 concaptModel02 = new ConcaptModel02();
        List<ConcaptModel02> concaptModel02s =new ArrayList<>();
        List<ProjectModel> projectModels = new ArrayList<>();
        String pastLebel1 = "";
        String pastLebel2 = "";
        String pastLebel3 = "";
        String pastLebel4 = "";
        String notLabel1 = "";
        String notLabel2 = "";
        String notLabel3 = "";
        String teachName="";
        String day = "";
        String mount = "";
        String year = "";
        try {
            projectModels = projectService.searchProjectNo(pro_no);

            for (ProjectModel projectModel : projectModels) {
                teachName =projectModel.getTeachName();
                List<NotificationModel> notificationModels = notifictionService.searchNotiProNo(projectModel.getProNo());
                if (notificationModels.size() > 0) {
                   
                    int index = 0;
                    for (NotificationModel noti : notificationModels) {

                        if (noti.getStatusCode().equals("A")) {

                            if (index == 0) {
                                pastLebel1 = noti.getMessage();
                            } else if (index == 1) {
                                pastLebel2 = noti.getMessage();
                            } else if (index == 2) {
                                pastLebel3 = noti.getMessage();
                            }
                        } else {

                            if (index == 0) {
                                notLabel1 = noti.getMessage();
                            } else if (index == 1) {
                                notLabel2 = noti.getMessage();
                            } else if (index == 2) {
                                notLabel3 = noti.getMessage();
                            }
                        }
                        index++;
                    }

                }
            }
            
            concaptModel02.setPastLebel1(pastLebel1);
            concaptModel02.setPastLebel2(pastLebel2);
            concaptModel02.setPastLebel3(pastLebel3);
            concaptModel02.setPastLebel4(pastLebel4);
            concaptModel02.setNotLabel1(notLabel1);
            concaptModel02.setNotLabel2(notLabel2);
            concaptModel02.setNotLabel3(notLabel3);
            concaptModel02.setTeachName(teachName);
            concaptModel02.setDay(day);
            concaptModel02.setMount(mount);
            concaptModel02.setYear(year);

            concaptModel02s.add(concaptModel02);
        } catch (Exception e) {
            System.out.println(e);
        }

        return concaptModel02s;
    }

   
}