package janggi.dao;

import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.direction.Position;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDao {

    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
    private final PositionDao positionDao = new PositionDao();
    private final TeamDao teamDao = new TeamDao();

    public void addPiece(final Piece piece) {
        final var query = "INSERT INTO piece (piece_type, team_id, position_id) VALUES (?, ?, ?)";
        try (final var connection = databaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final Position position = piece.getPosition();
            positionDao.addPosition(position);
            final int positionId = positionDao.findIdByXY(position.x(), position.y());
            final int teamId = teamDao.findTeamIdByName(piece.getTeam());
            PieceType pieceType = piece.getPieceType();
            preparedStatement.setString(1, pieceType.name());
            preparedStatement.setInt(2, teamId);
            preparedStatement.setInt(3, positionId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException();
        }
    }

    public Piece findPieceById(final int pieceId) {
        final var query = "SELECT * FROM piece WHERE piece_id = ?";
        try (final var connection = databaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pieceId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int teamId = resultSet.getInt("team_id");
                    int positionId = resultSet.getInt("position_id");
                    PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                    Position position = positionDao.findByPositionId(positionId);
                    Team team = teamDao.findTeamById(teamId);
                    return PieceFactory.createPiece(position, team, pieceType);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return null;
    }

    public void addPieces(List<Piece> pieces) {
        for (Piece piece : pieces) {
            addPiece(piece);
        }
    }

    public boolean deletePieceByPosition(final Position position) {

        final int positionId = positionDao.findIdByXY(position.x(), position.y());
        if (positionId <= 0) {
            return false;
        }

        final var query = "DELETE FROM piece WHERE position_id = ?";
        try (final var connection = databaseConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, positionId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                positionDao.deletePosition(position);
                return true;
            }
            return false;
        } catch (final SQLException e) {
            System.err.println("Piece 삭제 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePieceByPosition(final int x, final int y) {
        Position position = new Position(x, y);
        return deletePieceByPosition(position);
    }

    public boolean deletePieceById(final int pieceId) {
        Piece piece = findPieceById(pieceId);
        if (piece == null) {
            return false;
        }

        final Position position = piece.getPosition();
        return deletePieceByPosition(position);
    }

    public List<Piece> findAllPieces() {
        final var query = "SELECT * FROM piece";
        List<Piece> pieces = new ArrayList<>();

        try (final var connection = databaseConnection.getConnection();
             final var statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int teamId = resultSet.getInt("team_id");
                int positionId = resultSet.getInt("position_id");
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                Position position = positionDao.findByPositionId(positionId);
                Team team = teamDao.findTeamById(teamId);
                Piece piece = PieceFactory.createPiece(position, team, pieceType);
                pieces.add(piece);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve all pieces", e);
        }
        return pieces;
    }

    public void deleteAllPieces() {
        final var query = "DELETE FROM piece";
        try (final var connection = databaseConnection.getConnection();
             final var statement = connection.createStatement()) {
            statement.executeUpdate(query);
            positionDao.deleteAllPositions();
        } catch (final SQLException e) {
            System.err.println("모든 Piece 삭제 오류: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to delete all pieces", e);
        }
    }
}
