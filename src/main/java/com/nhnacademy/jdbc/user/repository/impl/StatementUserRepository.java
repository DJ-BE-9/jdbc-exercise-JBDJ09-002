package com.nhnacademy.jdbc.user.repository.impl;

import com.nhnacademy.jdbc.user.domain.User;
import com.nhnacademy.jdbc.user.repository.UserRepository;
import com.nhnacademy.jdbc.util.DbUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class StatementUserRepository implements UserRepository {

    @Override
    public Optional<User> findByUserIdAndUserPassword(String userId, String userPassword) {
        //todo#1 아이디, 비밀번호가 일치하는 User 조회
        String sql = String.format("SELECT * FROM jdbc_users WHERE user_id = '%s' and user_password = '%s'", userId, userPassword);

        Connection con = null;
        Statement stmt = null;

        try {
            con = DbUtils.getConnection();
            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                String user_id = rs.getString(1);
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
                stmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Optional<User> findById(String userId) {
        //#todo#2-아이디로 User 조회
        String sql = String.format("SELECT * FROM jdbc_users WHERE user_id = '%s'", userId);

        Connection con = null;
        Statement stmt = null;

        try {
            con = DbUtils.getConnection();
            stmt = con.createStatement();

            log.info("find SQL:{}", sql);

            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                String user_id = rs.getString(1);
                String user_name = rs.getString(2);
                String user_password = rs.getString(3);

                User user = new User(user_id, user_name, user_password);

                return Optional.of(user);
            }
            else {
                log.info("here");
                return Optional.empty();
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int save(User user) {
        //todo#3- User 저장
        String sql = String.format("INSERT INTO jdbc_users(user_id, user_name, user_password) values ('%s', '%s', '%s')", user.getUserId(), user.getUserName(), user.getUserPassword());

        Connection con = null;
        Statement stmt = null;

        try {
            con = DbUtils.getConnection();
            stmt = con.createStatement();

            log.debug("save SQL : {}", sql);

            return stmt.executeUpdate(sql);
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int updateUserPasswordByUserId(String userId, String userPassword) {
        //todo#4-User 비밀번호 변경
        String sql = String.format("UPDATE jdbc_users SET user_password = '%s' WHERE user_id = '%s'", userPassword, userId);

        Connection con = null;
        Statement stmt = null;

        try {
            con = DbUtils.getConnection();
            stmt = con.createStatement();

            return stmt.executeUpdate(sql);
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int deleteByUserId(String userId) {
        //todo#5 - User 삭제
        String sql = String.format("DELETE FROM jdbc_users WHERE user_id = '%s'", userId);

        Connection con = null;
        Statement stmt = null;

        try {
            con = DbUtils.getConnection();
            stmt = con.createStatement();

            return stmt.executeUpdate(sql);
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
