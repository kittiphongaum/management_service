package com.bru.cs.service;

import java.util.List;

import com.bru.cs.models.ProjectModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ProjectModel> searchProjectNo(String idSearch){

        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT * FROM tb_project WHERE pro_no ='"+idSearch+"'");
        sb.append(" ORDER BY create_date DESC");

        List<ProjectModel> searchIdList = jdbcTemplate.query(sb.toString(),
                new BeanPropertyRowMapper(ProjectModel.class));
        return searchIdList;
    }
}