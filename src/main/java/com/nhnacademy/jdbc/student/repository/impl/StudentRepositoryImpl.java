package com.nhnacademy.jdbc.student.repository.impl;

import com.nhnacademy.jdbc.student.domain.Student;
import com.nhnacademy.jdbc.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.Optional;

@Slf4j
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public int save(Connection connection, Student student){
        //todo#2 학생등록
        String url = "INSERT INTO jdbc_students(id, name, gender, age) values(?, ?, ?, ?)";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(url);
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
    public Optional<Student> findById(Connection connection,String id){
        //todo#3 학생조회
        String url = "SELECT * FROM jdbc_students WHERE id = ?";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(url);
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                String userId = rs.getString(1);
                String userName = rs.getString(2);
                String userG = rs.getString(3);
                int age = rs.getInt(4);
                if(userG.equals("M")) {
                    return Optional.of(new Student(userId, userName, Student.GENDER.M, age));
                }
                else {
                    return Optional.of(new Student(userId, userName, Student.GENDER.F, age));
                }
            }
            else {
                return Optional.empty();
            }

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
    public int update(Connection connection,Student student){
        //todo#4 학생수정
        String url = "UPDATE jdbc_students SET id = ?, name = ?, gender = ?, age = ? WHERE id = ?";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(url);
            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getName());
            if(student.getGender() == Student.GENDER.M) {
                pstmt.setString(3, "M");
            }
            else {
                pstmt.setString(3, "F");
            }
            pstmt.setInt(4, student.getAge());
            pstmt.setString(5, student.getId());


            return pstmt.executeUpdate();
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
    public int deleteById(Connection connection,String id){
        //todo#5 학생삭제
        String url = "DELETE FROM jdbc_students WHERE id = ?";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(url);
            pstmt.setString(1, id);

            return pstmt.executeUpdate();
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