package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.common.Page;
import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public int save(Connection connection, Student student){
        String sql = "insert into jdbc_students(id,name,gender,age) values(?,?,?,?)";

        try(
            PreparedStatement statement = connection.prepareStatement(sql);
        ){
            statement.setString(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getGender().toString());
            statement.setInt(4,student.getAge());

            int result = statement.executeUpdate();
            log.debug("save:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Student> findById(Connection connection,String id){
        String sql = "select * from jdbc_students where id=?";
        log.debug("findById:{}",sql);

        ResultSet rs = null;
        try(
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1,id);
            rs = statement.executeQuery();
            if(rs.next()){
                Student student =  new Student(rs.getString("id"),
                        rs.getString("name"),
                        Student.GENDER.valueOf(rs.getString("gender")),
                        rs.getInt("age"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                return Optional.of(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public int update(Connection connection,Student student){
        String sql = "update jdbc_students set name=?, gender=?, age=? where id=?";
        log.debug("update:{}",sql);

        try(
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            int index=0;
            statement.setString(++index, student.getName());
            statement.setString(++index, student.getGender().toString());
            statement.setInt(++index, student.getAge());
            statement.setString(++index, student.getId());

            int result = statement.executeUpdate();
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(Connection connection,String id){
        String sql = "delete from jdbc_students where id=?";

        try(
            PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, id);
            int result = statement.executeUpdate();
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteAll(Connection connection) {
        String sql = "delete from jdbc_students";

        try(
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            int result = statement.executeUpdate();
            log.debug("result:{}",result);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long totalCount(Connection connection) {
        //todo#4 totalCount 구현
        String sql = "SELECT * FROM jdbc_students";
        PreparedStatement pstmt = null;
        long count = 0;

        try {
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                count++;
            }

            return count;
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Page<Student> findAll(Connection connection, int page, int pageSize) {
        //todo#5 페이징 처리 구현
        String sql = "SELECT * FROM jdbc_students LIMIT ?, ?";

        PreparedStatement pstmt = null;
        List<Student> list = new ArrayList<>();

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, (page-1)*pageSize);
            pstmt.setInt(2, pageSize);

            long totalCount = 0;

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                String id = rs.getString(1);
                String name = rs.getString(2);
                String g = rs.getString(3);
                int age = rs.getInt(4);
                LocalDateTime createdAt = rs.getTimestamp(5).toLocalDateTime();

                Student student = null;
                if(g.equals("M")) {
                    student = new Student(id, name, Student.GENDER.M, age, createdAt);
                }
                else {
                    student = new Student(id, name, Student.GENDER.F, age, createdAt);
                }

                list.add(student);
                totalCount++;
            }

            return new Page<>(list, totalCount);
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}