package com.nhnacademy.jdbc.bank.repository.impl;

import com.nhnacademy.jdbc.bank.domain.Account;
import com.nhnacademy.jdbc.bank.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class AccountRepositoryImpl implements AccountRepository {

    public Optional<Account> findByAccountNumber(Connection connection, long accountNumber){
        //todo#1 계좌-조회
        String sql = "SELECT * FROM jdbc_account WHERE account_number = ?";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                long account_number = rs.getLong(1);
                String name = rs.getString(2);
                long balance = rs.getLong(3);

                Account account = new Account(account_number, name, balance);
                return Optional.of(account);
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
    public int save(Connection connection, Account account) {
        //todo#2 계좌-등록, executeUpdate() 결과를 반환 합니다.
        String sql = "INSERT INTO jdbc_account(account_number, name, balance) values(?, ?, ?)";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, account.getAccountNumber());
            pstmt.setString(2, account.getName());
            pstmt.setLong(3, account.getBalance());

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
    public int countByAccountNumber(Connection connection, long accountNumber){
        int count=0;
        //todo#3 select count(*)를 이용해서 계좌의 개수를 count해서 반환
        String sql = "SELECT count(*) FROM jdbc_account WHERE account_number = ?";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                count = (int) rs.getLong(1);
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return count;
    }

    @Override
    public int deposit(Connection connection, long accountNumber, long amount){
        //todo#4 입금, executeUpdate() 결과를 반환 합니다.
        Optional<Account> account = findByAccountNumber(connection, accountNumber);

        String sql = "UPDATE jdbc_account SET balance = ? WHERE account_number = ?";
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            long balance = account.get().getBalance() + amount;
            pstmt.setLong(1, balance);
            pstmt.setLong(2, accountNumber);

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
    public int withdraw(Connection connection, long accountNumber, long amount){
        //todo#5 출금, executeUpdate() 결과를 반환 합니다.
        Optional<Account> account = findByAccountNumber(connection, accountNumber);

        String sql = "UPDATE jdbc_account SET balance = ? WHERE account_number = ?";
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            long balance = account.get().getBalance()-amount;
            pstmt.setLong(1, balance);
            pstmt.setLong(2, accountNumber);

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
    public int deleteByAccountNumber(Connection connection, long accountNumber) {
        //todo#6 계좌 삭제, executeUpdate() 결과를 반환 합니다.
        String sql = "DELETE FROM jdbc_account WHERE account_number = ?";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);

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
