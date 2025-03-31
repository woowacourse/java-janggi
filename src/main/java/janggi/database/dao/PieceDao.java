package janggi.database.dao;

import janggi.database.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PieceDao {

    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    public void addPiece(final PieceEntity pieceEntity) {
        final String query = "INSERT INTO piece (piece_type, team_id, position_id) VALUES (?, ?, ?)";
        try (final Connection connection = databaseConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final Long id = DBUtils.executeInsert(query, pieceEntity.getDtype(), pieceEntity.getTeam(),
                    pieceEntity.getColumnIndex(), pieceEntity.getRowIndex());
            preparedStatement.setString(1, pieceEntity.getType());
            preparedStatement.setLong(2, pieceEntity.getTeamId());
            preparedStatement.setLong(3, pieceEntity.getPositionId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("오류가 발생했습니다.");
        }
    }
//
//    public Piece findPieceById(final int pieceId) {
//        final String query = "SELECT * FROM piece WHERE piece_id = ?";
//        try (final Connection connection = databaseConnection.getConnection();
//             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setInt(1, pieceId);
//            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    final int teamId = resultSet.getInt("team_id");
//                    final int positionId = resultSet.getInt("position_id");
//                    final PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
//                    final Position position = positionDao.findPositionById(positionId);
//                    final Team team = teamDao.findTeamById(teamId);
//                    return PieceFactory.createPiece(position, team, pieceType);
//                }
//            }
//            throw new RuntimeException("해당 기물을 찾을 수 없습니다.");
//        } catch (final SQLException e) {
//            throw new RuntimeException("오류가 발생했습니다.");
//        }
//    }
//
//    public int findIdByPiece(final Piece piece) {
//        final String query = "SELECT * FROM piece WHERE team_id = ? AND position_id = ? AND piece_type = ?";
//        try (final Connection connection = databaseConnection.getConnection();
//             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            final int teamId = teamDao.findIdByTeam(piece.getTeam());
//            preparedStatement.setInt(1, teamId);
//            final int positionId = positionDao.findIdByPosition(piece.getPosition()).get();
//            preparedStatement.setInt(2, positionId);
//            preparedStatement.setString(3, piece.getPieceType().name());
//            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    return resultSet.getInt("piece_id");
//                }
//            }
//            throw new RuntimeException("해당 기물을 찾을 수 없습니다.");
//        } catch (final SQLException e) {
//            throw new RuntimeException("오류가 발생했습니다.");
//        }
//    }
//
//    public void addPieces(final List<Piece> pieces) {
//        for (final Piece piece : pieces) {
//            addPiece(piece);
//        }
//    }
//
//    public void deletePieceByPosition(final Position position) {
//
//        final Optional<Integer> positionId = positionDao.findIdByPosition(position);
//        if (positionId.isEmpty()) {
//            return;
//        }
//
//        final String query = "DELETE FROM piece WHERE position_id = ?";
//        try (final Connection connection = databaseConnection.getConnection();
//             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setInt(1, positionId.get());
//            final int rowsAffected = preparedStatement.executeUpdate();
//
//            if (rowsAffected > 0) {
//                positionDao.deletePosition(position);
//            }
//        } catch (final SQLException e) {
//            throw new RuntimeException("오류가 발생했습니다.");
//        }
//    }
//
//    public List<Piece> findAllPieces() {
//        final String query = "SELECT * FROM piece";
//        final List<Piece> pieces = new ArrayList<>();
//
//        try (final Connection connection = databaseConnection.getConnection();
//             final PreparedStatement preparedStatement = connection.prepareStatement(query);
//             final ResultSet resultSet = preparedStatement.executeQuery(query)) {
//
//            while (resultSet.next()) {
//                final int teamId = resultSet.getInt("team_id");
//                final int positionId = resultSet.getInt("position_id");
//                final PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
//                final Position position = positionDao.findPositionById(positionId);
//                final Team team = teamDao.findTeamById(teamId);
//                final Piece piece = PieceFactory.createPiece(position, team, pieceType);
//                pieces.add(piece);
//            }
//        } catch (final SQLException e) {
//            throw new RuntimeException("오류가 발생했습니다.");
//        }
//        return pieces;
//    }
//
//    public void deleteAllPieces() {
//        final String query = "DELETE FROM piece";
//        try (final Connection connection = databaseConnection.getConnection();
//             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.executeUpdate(query);
//            positionDao.deleteAllPositions();
//        } catch (final SQLException e) {
//            throw new RuntimeException("오류가 발생했습니다.");
//        }
//    }
}
