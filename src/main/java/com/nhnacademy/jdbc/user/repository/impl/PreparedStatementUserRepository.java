package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Optional;

@Slf4j
public class PreparedStatementUserRepository implements UserRepository {
    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        //todo#11 -PreparedStatement- 아이디 , 비밀번호가 일치하는 회원조회
        String sql = "SELECT * FROM jdbc_users WHERE user_id = ? and user_password = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DbUtils.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPassword);

            rs = pstmt.executeQuery();
            if(rs.next()) {
                String user_id = rs.getNString(1);
                String user_name = rs.getString(2);
                String user_password = rs.getString(3);

                User user = new User(user_id, user_name, user_password);

                return Optional.of(user);
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
                rs.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Optional<User> findById(String userId) {
        //todo#12-PreparedStatement-회원조회
        String sql = "SELECT * FROM jdbc_users WHERE user_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DbUtils.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                String user_id = rs.getString(1);
                String user_name = rs.getString(2);
                String user_password = rs.getString(3);

                User user = new User(user_id, user_name, user_password);
                log.debug("id:{}", user.getUserId());
                log.debug("name:{}", user.getUserName());
                log.debug("password:{}", user.getUserPassword());

                return Optional.of(user);
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
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int save(User user) {
        //todo#13-PreparedStatement-회원저장
        String sql = "INSERT INTO jdbc_users(user_id, user_name, user_password) values (?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DbUtils.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getUserPassword());

            return pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int updateUserPasswordByUserId(String userId, String userPassword) {
        //todo#14-PreparedStatement-회원정보 수정
        String sql = "UPDATE jdbc_users SET user_password = ? WHERE user_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DbUtils.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, userPassword);
            pstmt.setString(2, userId);

            return pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#15-PreparedStatement-회원삭제
        String sql = "DELETE FROM jdbc_users WHERE user_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DbUtils.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            return pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                pstmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
