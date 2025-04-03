package janggi.dao;

import janggi.board.Point;
import janggi.camp.Camp;
import janggi.infra.DatabaseConnector;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    private final DatabaseConnector DBConnector;

    public PieceDao(DatabaseConnector DBConnector) {
        this.DBConnector = DBConnector;
    }

    public void addPiece(Piece piece, Point point) {
        String query = "INSERT INTO piece (type, camp, pos_x, pos_y) VALUES (?, ?, ?, ?)";
        Camp camp = piece.getCamp();
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, piece.getPieceType().getName(camp));
            preparedStatement.setString(2, camp.getName());
            preparedStatement.setInt(3, point.getX());
            preparedStatement.setInt(4, point.getY());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 추가 중 오류가 발생했습니다.", e);
        }
    }

    public Piece findByPoint(Point point) {
        String query = "SELECT * FROM piece WHERE pos_x = ? AND pos_y = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, point.getX());
            preparedStatement.setInt(2, point.getY());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String type = resultSet.getString("type");
                Camp camp = Camp.from(resultSet.getString("camp"));
                return PieceType.toPiece(type, camp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("좌표에 해당하는 기물 조회 중 오류가 발생했습니다.", e);
        }
        return null;
    }

    public void updatePieceByPoint(Point point, Piece piece) {
        String query = "UPDATE piece SET type = ?, camp = ? WHERE pos_x = ? AND pos_y = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Camp camp = piece.getCamp();
            preparedStatement.setString(1, piece.getPieceType().getName(camp));
            preparedStatement.setString(2, camp.getName());
            preparedStatement.setInt(3, point.getX());
            preparedStatement.setInt(4, point.getY());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 정보 업데이트 중 오류가 발생했습니다.", e);
        }
    }

    public void deletePieceByPoint(Point point) {
        String query = "DELETE FROM piece WHERE pos_x = ? AND pos_y = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, point.getX());
            preparedStatement.setInt(2, point.getY());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 삭제 중 오류가 발생했습니다.", e);
        }
    }

    public Camp findWinningCamp() {
        String query = "SELECT * FROM piece WHERE type = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, PieceType.GENERAL.getName(null));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Camp.from(resultSet.getString("camp"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("승리한 캠프 조회 중 오류가 발생했습니다.", e);
        }
        return null;
    }

    public int getGeneralCount() {
        String query = "SELECT * FROM piece WHERE type = ?";
        int generalCount = 0;
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, PieceType.GENERAL.getName(null));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                generalCount++;
            }
        } catch (SQLException e) {
            throw new RuntimeException("장군 기물 수 조회 중 오류가 발생했습니다.", e);
        }
        return generalCount;
    }

    public List<Piece> findAllCampPieces(Camp camp) {
        String query = "SELECT * FROM piece WHERE camp = ?";
        List<Piece> campPieces = new ArrayList<>();
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, camp.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                campPieces.add(PieceType.toPiece(type, camp));
            }
        } catch (SQLException e) {
            throw new RuntimeException("캠프별 기물 전체 조회 중 오류가 발생했습니다.", e);
        }
        return campPieces;
    }

    public void clearTable() {
        String query = "TRUNCATE TABLE piece";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("기물 테이블 초기화 중 오류가 발생했습니다.", e);
        }
    }
}
