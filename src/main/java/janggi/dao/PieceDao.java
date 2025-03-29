package janggi.dao;

import janggi.dao.dto.PieceFindResponse;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Side;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    public List<PieceFindResponse> findAllPieces() {
        final String query = "SELECT * FROM Piece";
        try (final Connection connection = DatabaseConnectionManager.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<PieceFindResponse> findPieceResponses = new ArrayList<>();
            while (resultSet.next()) {
                String type = resultSet.getString("type");
                String side = resultSet.getString("side");

                findPieceResponses.add(new PieceFindResponse(type, side));
            }

            return findPieceResponses;
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 기물 조회가 성공적으로 진행되지 않았습니다.");
        }
    }

    public String findById(int pieceId) {
        final String query = "SELECT * FROM Piece WHERE piece_id = ?";
        try (final Connection connection = DatabaseConnectionManager.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, pieceId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getString("type");
            }
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 기물 조회가 성공적으로 진행되지 않았습니다.");
        }
        return null;
    }

    public void addPiece(PieceType pieceType, Side side) {
        final String query = "INSERT INTO Piece (type, side) VALUES (?, ?)";
        try (final Connection connection = DatabaseConnectionManager.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, pieceType.getSymbol());
            preparedStatement.setString(2, side.getName());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 기물 초기화가 성공적으로 진행되지 않았습니다.");
        }
    }

    public void resetTable() {
        final String query = "Delete FROM Piece WHERE piece_id > 0"; // 모든 데이터 삭제 + AUTO_INCREMENT 초기화
        try (final Connection connection = DatabaseConnectionManager.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException("[ERROR] 데이터베이스 초기화를 실패했습니다.");
        }
    }
}
