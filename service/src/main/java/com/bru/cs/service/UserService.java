package com.bru.cs.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bru.cs.models.SearcModel;
import com.bru.cs.models.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    public List <UserModel> searcProNoStudent(SearcModel model){

        SearcModel idSearch =new SearcModel();          
        idSearch =model;
        idSearch.setUserId("6161161");
        
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT u.* FROM user_profile  AS u  WHERE 1=1 ");
  
        if (idSearch.getUserId()!=null) {
            sb.append(" AND u.user_id ="+idSearch.getUserId()+"");
        }
        if (idSearch.getUserName()!=null) {
            sb.append(" AND u.user_name like '%"+idSearch.getUserId()+"%'");
        }
        if (idSearch.getStatus()!=null) {
            sb.append(" AND u.status ='"+idSearch.getUserId()+"'");
        }
        if (idSearch.getEducationYear()!=null) {
            sb.append(" AND u.education_year ='"+idSearch.getUserId()+"'");
        }
        if (idSearch.getReqStatus()!=null) {
            sb.append(" AND u.req_status IN ('"+idSearch.getUserId()+"') ");
        }
       
        sb.append(" ORDER BY u.created_date DESC");

        List<UserModel> searchIdList = jdbcTemplate.query(sb.toString(),
                new BeanPropertyRowMapper(UserModel.class));

             
        return searchIdList;
    }

    public List <UserModel> searcProNoTeacher(SearcModel model1){

        SearcModel idSearch =new SearcModel();          
        idSearch =model1;
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT u.* FROM user_profile  AS u  WHERE 1=1");
  
        if (idSearch.getUserId()!=null) {
            sb.append(" AND u.user_id ='"+idSearch.getUserId()+"'");
        }
        if (idSearch.getUserName()!=null) {
            sb.append(" AND u.user_name like '%"+idSearch.getUserId()+"%'");
        }
        if (idSearch.getStatus()!=null) {
            sb.append(" AND u.status ='"+idSearch.getUserId()+"'");
        }
        if (idSearch.getEducationYear()!=null) {
            sb.append(" AND u.education_year ='"+idSearch.getUserId()+"'");
        }
        if (idSearch.getReqStatus()!=null) {
            sb.append(" AND u.req_status IN ('"+idSearch.getUserId()+"') ");
        }
       
        sb.append(" ORDER BY u.created_date DESC");

        List<UserModel> searchIdList = jdbcTemplate.query(sb.toString(),
                new BeanPropertyRowMapper(UserModel.class));

                UserModel model = new UserModel();
                if (searchIdList.size()>0) {
                    model =searchIdList.get(0);
                }        
        
            List<UserModel> userModels = new ArrayList<>();
           for (UserModel userModel : searchIdList) {

            userModel.setTeachNo(outstbalanceTeach(Integer.parseInt(userModel.getUserId())));
            userModel.setTeachSecondNo(outstbalanceSecond(Integer.parseInt(userModel.getUserId())));
            userModels.add(userModel);
           } 
        return userModels;
    }

    public String outstbalanceTeach(Integer empid){
        StringBuilder sb =new StringBuilder();
        sb.append("SELECT COUNT(teach_id) AS count FROM tb_project WHERE teach_id ="+empid);

        List<String> leveles = jdbcTemplate.query(
                    // , rse)
                    sb.toString(), new RowMapper() {
                    
                        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                                    return rs.getString("count");
                        }
                    });
        String liml = "0";
        if (leveles.size()>0) {
            liml =leveles.get(0);
        }
        return liml;
    }
    public String outstbalanceSecond(Integer empid){
        StringBuilder sb =new StringBuilder();
        sb.append("SELECT COUNT(teach_second_id) AS count FROM tb_project WHERE teach_second_id="+empid);

        List<String> leveles = jdbcTemplate.query(
                    // , rse)
                    sb.toString(), new RowMapper() {
                    
                        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                                    return rs.getString("count");
                        }
                    });
        String liml = "0";
        if (leveles.size()>0) {
            liml =leveles.get(0);
        }
        return liml;
    }

    
}