package com.bru.cs.service;

import java.util.List;

import com.bru.cs.models.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserModel searchNotiProNo(Integer idSearch){

        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT * FROM user_profile WHERE user_id ='"+idSearch+"'");
        sb.append(" ORDER BY created_date DESC");

        List<UserModel> searchIdList = jdbcTemplate.query(sb.toString(),
                new BeanPropertyRowMapper(UserModel.class));

                UserModel model = new UserModel();
                if (searchIdList.size()>0) {
                    model =searchIdList.get(0);
                }        
        
        return model;
    }

}