package com.nhnacademy.jdbc.club.repository.impl;

import com.nhnacademy.jdbc.club.domain.ClubStudent;
import com.nhnacademy.jdbc.club.repository.ClubRegistrationRepository;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class ClubRegistrationRepositoryImpl implements ClubRegistrationRepository {

    @Override
    public int save(Connection connection, String studentId, String clubId) {
        //todo#11 - 핵생 -> 클럽 등록, executeUpdate() 결과를 반환
        String sql = "INSERT INTO jdbc_club_registrations (student_id, club_id) values (?, ?)";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, studentId);
            pstmt.setString(2, clubId);

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
    public int deleteByStudentIdAndClubId(Connection connection, String studentId, String clubId) {
        //todo#12 - 핵생 -> 클럽 탈퇴, executeUpdate() 결과를 반환
        String sql = "DELETE FROM jdbc_club_registrations WHERE club_id = ? and student_id = ?";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, clubId);
            pstmt.setString(2, studentId);

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
    public List<ClubStudent> findClubStudentsByStudentId(Connection connection, String studentId) {
        //todo#13 - 핵생 -> 클럽 등록, executeUpdate() 결과를 반환
        String sql = "SELECT A.id, A.name, C.club_id, C.club_name " +
                "FROM jdbc_students A " +
                "INNER JOIN jdbc_club_registrations B ON A.id = B.student_id " +
                "INNER JOIN jdbc_club C ON B.club_id = C.club_id " +
                "WHERE A.id = ?";

        PreparedStatement pstmt = null;
        List<ClubStudent> list = new ArrayList<>();

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, studentId);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                String student_id = rs.getString(1);
                String student_name = rs.getString(2);
                String club_id = rs.getString(3);
                String club_name = rs.getString(4);

                list.add(new ClubStudent(student_id, student_name, club_id, club_name));
            }

            return list;
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
    public List<ClubStudent> findClubStudents(Connection connection) {
        //todo#21 - join
        String sql = "SELECT A.id, A.name, C.club_id, C.club_name " +
                "FROM jdbc_students A " +
                "INNER JOIN jdbc_club_registrations B ON A.id = B.student_id " +
                "INNER JOIN jdbc_club C ON B.club_id = C.club_id";

        PreparedStatement pstmt = null;
        List<ClubStudent> list = new ArrayList<>();

        try {
            pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                String student_id = rs.getString(1);
                String student_name = rs.getString(2);
                String club_id = rs.getString(3);
                String club_name = rs.getString(4);

                ClubStudent clubStudent = new ClubStudent(student_id, student_name, club_id, club_name);
                list.add(clubStudent);
            }

            return list;
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
    public List<ClubStudent> findClubStudents_left_join(Connection connection) {
        //todo#22 - left join
        String sql = "SELECT A.id, A.name, C.club_id, C.club_name " +
                "FROM jdbc_students A " +
                "LEFT JOIN jdbc_club_registrations B ON A.id = B.student_id " +
                "LEFT JOIN jdbc_club C ON B.club_id = C.club_id";

        PreparedStatement pstmt = null;
        List<ClubStudent> list = new ArrayList<>();

        try {
            pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                String student_id = rs.getString(1);
                String student_name = rs.getString(2);
                String club_id = rs.getString(3);
                String club_name = rs.getString(4);

                ClubStudent clubStudent = new ClubStudent(student_id, student_name, club_id, club_name);
                list.add(clubStudent);
            }

            return list;
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
    public List<ClubStudent> findClubStudents_right_join(Connection connection) {
        //todo#23 - right join
        String sql = "SELECT A.id, A.name, C.club_id, C.club_name " +
                "FROM jdbc_students A " +
                "RIGHT JOIN jdbc_club_registrations B ON A.id = B.student_id " +
                "RIGHT JOIN jdbc_club C ON B.club_id = C.club_id";

        PreparedStatement pstmt = null;
        List<ClubStudent> list = new ArrayList<>();

        try {
            pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                String student_id = rs.getString(1);
                String student_name = rs.getString(2);
                String club_id = rs.getString(3);
                String club_name = rs.getString(4);

                ClubStudent clubStudent = new ClubStudent(student_id, student_name, club_id, club_name);
                list.add(clubStudent);
            }

            return list;
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
    public List<ClubStudent> findClubStudents_full_join(Connection connection) {
        //todo#24 - full join = left join union right join
        String sql = "SELECT A.id, A.name, C.club_id, C.club_name " +
                "FROM jdbc_students A " +
                "LEFT JOIN jdbc_club_registrations B ON A.id = B.student_id " +
                "LEFT JOIN jdbc_club C ON B.club_id = C.club_id " +
                "UNION " +
                "SELECT A.id, A.name, C.club_id, C.club_name " +
                "FROM jdbc_students A " +
                "RIGHT JOIN jdbc_club_registrations B ON A.id = B.student_id " +
                "RIGHT JOIN jdbc_club C ON B.club_id = C.club_id";

        PreparedStatement pstmt = null;
        List<ClubStudent> list = new ArrayList<>();

        try {
            pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                String student_id = rs.getString(1);
                String student_name = rs.getString(2);
                String club_id = rs.getString(3);
                String club_name = rs.getString(4);

                ClubStudent clubStudent = new ClubStudent(student_id, student_name, club_id, club_name);
                list.add(clubStudent);
            }

            return list;
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
    public List<ClubStudent> findClubStudents_left_excluding_join(Connection connection) {
        //todo#25 - left excluding join
        String sql = "SELECT A.id, A.name, C.club_id, C.club_name " +
                "FROM jdbc_students A " +
                "LEFT JOIN jdbc_club_registrations B ON A.id = B.student_id " +
                "LEFT JOIN jdbc_club C ON B.club_id = C.club_id " +
                "WHERE B.student_id IS NULL";

        PreparedStatement pstmt = null;
        List<ClubStudent> list = new ArrayList<>();

        try {
            pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                String student_id = rs.getString(1);
                String student_name = rs.getString(2);
                String club_id = rs.getString(3);
                String club_name = rs.getString(4);

                ClubStudent clubStudent = new ClubStudent(student_id, student_name, club_id, club_name);
                list.add(clubStudent);
            }

            return list;
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
    public List<ClubStudent> findClubStudents_right_excluding_join(Connection connection) {
        //todo#26 - right excluding join
        String sql = "SELECT A.id, A.name, C.club_id, C.club_name " +
                "FROM jdbc_students A " +
                "RIGHT JOIN jdbc_club_registrations B ON A.id = B.student_id " +
                "RIGHT JOIN jdbc_club C ON B.club_id = C.club_id " +
                "WHERE A.id IS NULL";

        PreparedStatement pstmt = null;
        List<ClubStudent> list = new ArrayList<>();

        try {
            pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                String student_id = rs.getString(1);
                String student_name = rs.getString(2);
                String club_id = rs.getString(3);
                String club_name = rs.getString(4);

                ClubStudent clubStudent = new ClubStudent(student_id, student_name, club_id, club_name);
                list.add(clubStudent);
            }

            return list;
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
    public List<ClubStudent> findClubStudents_outher_excluding_join(Connection connection) {
        //todo#27 - outher_excluding_join = left excluding join union right excluding join
        String sql = "SELECT A.id, A.name, C.club_id, C.club_name " +
                "FROM jdbc_students A " +
                "LEFT JOIN jdbc_club_registrations B ON A.id = B.student_id " +
                "LEFT JOIN jdbc_club C ON B.club_id = C.club_id " +
                "WHERE C.club_id IS NULL " +
                "UNION " +
                "SELECT A.id, A.name, C.club_id, C.club_name " +
                "FROM jdbc_students A " +
                "RIGHT JOIN jdbc_club_registrations B ON A.id = B.student_id " +
                "RIGHT JOIN jdbc_club C ON B.club_id = C.club_id " +
                "WHERE A.id IS NULL ";

        PreparedStatement pstmt = null;
        List<ClubStudent> list = new ArrayList<>();

        try {
            pstmt = connection.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                String student_id = rs.getString(1);
                String student_name = rs.getString(2);
                String club_id = rs.getString(3);
                String club_name = rs.getString(4);

                ClubStudent clubStudent = new ClubStudent(student_id, student_name, club_id, club_name);
                list.add(clubStudent);
            }

            return list;
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