package com.bru.cs.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bru.cs.models.SearcModel;
import com.bru.cs.models.UserModel;

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

@Service
public class ReportAdminPage {

    @Autowired
    private UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    NotifictionService notifictionService;

    @Autowired
    private org.springframework.context.ApplicationContext context;
    
    public void genStudenPDF(HttpServletResponse response)  {


        Resource resource = context.getResource("classpath:reports/ReportStudent.jrxml");
        InputStream inputStream = null;
        final SearcModel model = new SearcModel();
        try {
            inputStream = resource.getInputStream();
            JasperReport report = JasperCompileManager.compileReport(inputStream);

            Map<String, Object> params = new HashMap<>();
            List<UserModel> data = userService.searcProNoStudent(model);

            params.put("reportSource",null);
            JRDataSource dataSource = null;
            if(data!=null && data.size()>0){
                dataSource = new JRBeanCollectionDataSource(data);
            }else {
                dataSource = new JREmptyDataSource();
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=รายงานบัญชีโครงงาน.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        } catch (JRException e) {
            System.out.println(e);
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }


    }
    public void genReportTecherPDF(HttpServletResponse response) {

        Resource resource = context.getResource("classpath:reports/ReportTecher.jrxml");
        InputStream inputStream = null;
        final SearcModel model = new SearcModel();
        try {
            inputStream = resource.getInputStream();
            JasperReport report = JasperCompileManager.compileReport(inputStream);

            Map<String, Object> params = new HashMap<>();
            List<UserModel> data = userService.searcProNoTeacher(model);

            params.put("reportSource",null);
            JRDataSource dataSource = null;
            if(data!=null && data.size()>0){
                dataSource = new JRBeanCollectionDataSource(data);
            }else {
                dataSource = new JREmptyDataSource();
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, dataSource);
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=FUND_" + ".pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
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

}