package com.example.demo.repository.impl;

import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import com.example.demo.repository.SpecialRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SpecialRepositoryImpl implements SpecialRepository<User> {

    private final JdbcTemplate jdbcTemplate;

    public SpecialRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> runQuerySql(String sql,  Object... parameters) {
        return jdbcTemplate.query(sql,  (rs, rn) -> {
                String username = rs.getString("username");
                String password= rs.getString("password");
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setGender(gender);
                user.setEmail(email);
                user.setPhone(phone);
                return user;
        }, parameters);
    }
}
