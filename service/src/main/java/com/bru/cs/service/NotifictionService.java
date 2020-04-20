package com.bru.cs.service;

import java.util.ArrayList;
import java.util.List;

import com.bru.cs.models.NotificationModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service
public class NotifictionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<NotificationModel> searchNotiProNo(String idSearch){

        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT * FROM notification WHERE pro_no ='"+idSearch+"'");
        sb.append(" ORDER BY created_date DESC");

        List<NotificationModel> searchIdList = jdbcTemplate.query(sb.toString(),
                new BeanPropertyRowMapper(NotificationModel.class));
                List<NotificationModel> notificationModels = new ArrayList<>(); 
                if (searchIdList.size()>0) {
                    notificationModels =searchIdList;
                }
               
        return notificationModels;
    }
}