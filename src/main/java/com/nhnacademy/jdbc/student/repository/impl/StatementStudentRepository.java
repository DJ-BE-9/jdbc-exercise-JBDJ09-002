package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class StatementStudentRepository implements StudentRepository {

    @Override
    public int save(Student student){
        //todo#1 insert student
        String sql = "insert into jdbc_students(id, name, gender, age) values (?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DbUtils.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getName());
            if(student.getGender() == Student.GENDER.M) {
                pstmt.setString(3, "M");
            }
            else {
                pstmt.setString(3, "F");
            }
            pstmt.setInt(4, student.getAge());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                con.close();
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Optional<Student> findById(String id){
        //todo#2 student 조회
        String sql = "select * from jdbc_students where id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DbUtils.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);

            rs = pstmt.executeQuery();
            if(rs.next()) {
                String studentId = rs.getString(1);
                String studentName = rs.getString(2);
                String studentGender = rs.getString(3);
                int studentAge = rs.getInt(4);

                Student student = null;
                if(studentGender.equals("M")) {
                    student = new Student(studentId, studentName, Student.GENDER.M, studentAge);
                }
                else {
                    student = new Student(studentId, studentName, Student.GENDER.F, studentAge);
                }

                log.debug("studentId:{}", studentId);
                log.debug("studentName:{}", studentName);
                log.debug("studentGender:{}", studentGender);
                log.debug("studentAge:{}", studentAge);

                return Optional.of(student);
            }
            else {
                log.info("not next");
                return Optional.empty();
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                pstmt.close();
                rs.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public int update(Student student){
        //todo#3 student 수정, name <- 수정합니다.
        String sql = "update jdbc_students set name = ?, gender = ?, age = ? where id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DbUtils.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setString(4, student.getId());
            if(student.getGender() == Student.GENDER.M) {
                pstmt.setString(2, "M");
            }
            else {
                pstmt.setString(2, "F");
            }
            pstmt.setInt(3, student.getAge());

            return pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                con.close();
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public int deleteById(String id){
       //todo#4 student 삭제
        String sql = "delete from jdbc_students where id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DbUtils.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);

            return pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                con.close();
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
