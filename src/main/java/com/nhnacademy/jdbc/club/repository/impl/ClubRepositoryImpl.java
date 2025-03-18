package com.nhnacademy.jdbc.club.repository.impl;

import com.nhnacademy.jdbc.club.domain.Club;
import com.nhnacademy.jdbc.club.repository.ClubRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class ClubRepositoryImpl implements ClubRepository {

    @Override
    public Optional<Club> findByClubId(Connection connection, String clubId) {
        //todo#3 club 조회
        String sql = "SELECT * FROM jdbc_club WHERE club_id = ?";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, clubId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                String club_id = rs.getString(1);
                String club_name = rs.getString(2);
                LocalDateTime club_created_at = rs.getTimestamp(3).toLocalDateTime();

                Club club = new Club(club_id, club_name, club_created_at);

                return Optional.of(club);
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
    public int save(Connection connection, Club club) {
        //todo#4 club 생성, executeUpdate() 결과를 반환
        String sql = "INSERT INTO jdbc_club (club_id, club_name, club_created_at) values (?, ?, ?)";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, club.getClubId());
            pstmt.setString(2, club.getClubName());
            pstmt.setTimestamp(3, Timestamp.valueOf(club.getClubCreatedAt()));

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
    public int update(Connection connection, Club club) {
        //todo#5 club 수정, clubName을 수정합니다. executeUpdate()결과를 반환
        String sql = "UPDATE jdbc_club SET club_name = ? WHERE club_id = ?";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, club.getClubName());
            pstmt.setString(2, club.getClubId());

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
    public int deleteByClubId(Connection connection, String clubId) {
        //todo#6 club 삭제, executeUpdate()결과 반환
        String sql = "DELETE FROM jdbc_club WHERE club_id = ?";

        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, clubId);

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
    public int countByClubId(Connection connection, String clubId) {
        //todo#7 clubId에 해당하는 club의 count를 반환
        String sql = "SELECT count(*) FROM jdbc_club WHERE club_id = ?";

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, clubId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                count = rs.getInt(1);
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
}
