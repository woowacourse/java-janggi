package repository.dao;

import janggi.board.Position;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Team;
import repository.connection.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class BoardDAO {

    private final Connection connection;

    public BoardDAO(ConnectionManager manager) {
        this.connection = manager.getConnection();
    }

    public void saveInitialBoard(Map<Position, Piece> initialBoard) {
        String query = """
                INSERT INTO piece (piece_type_name, team_name, position_column, position_row) 
                VALUES (?, ?, ?, ?)
                """;

        int PIECE_TYPE_INDEX = 1;
        int TEAM_INDEX = 2;
        int COLUMN_INDEX = 3;
        int ROW_INDEX = 4;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Map.Entry<Position, Piece> entry : initialBoard.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();

                statement.setString(PIECE_TYPE_INDEX, piece.getType().name());
                statement.setString(TEAM_INDEX, piece.getTeam().name());
                statement.setInt(COLUMN_INDEX, position.getColumn());
                statement.setInt(ROW_INDEX, position.getRow());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("초기 보드 상태 저장 실패", e);
        }
    }

    public Map<Position, Piece> findAllPiecesOnBoard() {
        Map<Position, Piece> board = new HashMap<>();
        String query = """
                SELECT piece_type_name, team_name, position_column, position_row 
                FROM piece
                """;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String typeName = resultSet.getString("piece_type_name");
                String teamName = resultSet.getString("team_name");
                int column = resultSet.getInt("position_column");
                int row = resultSet.getInt("position_row");

                Position position = new Position(column, row);
                Piece piece = mapToPiece(Team.convert(teamName), typeName);
                board.put(position, piece);
            }
        } catch (SQLException e) {
            throw new RuntimeException("보드 상태 불러오기 실패", e);
        }
        return board;
    }

    public void updatePiecePosition(Position start, Position goal) {
        String deleteExistingPieceQuery = """
                DELETE FROM piece 
                WHERE position_column = ? AND position_row = ?
                """;

        String updateQuery = """
                UPDATE piece
                SET position_column = ?, position_row = ?
                WHERE position_column = ? AND position_row = ?
                """;

        int GOAL_POSITION_COLUMN = 1;
        int GOAL_POSITION_ROW = 2;
        int START_POSITION_COLUMN = 3;
        int START_POSITION_ROW = 4;

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteExistingPieceQuery)) {
                deleteStatement.setInt(GOAL_POSITION_COLUMN, goal.getColumn());
                deleteStatement.setInt(GOAL_POSITION_ROW, goal.getRow());
                deleteStatement.executeUpdate();
            }

            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setInt(GOAL_POSITION_COLUMN, goal.getColumn());
                updateStatement.setInt(GOAL_POSITION_ROW, goal.getRow());
                updateStatement.setInt(START_POSITION_COLUMN, start.getColumn());
                updateStatement.setInt(START_POSITION_ROW, start.getRow());

                int rowsAffected = updateStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new RuntimeException("기물 이동 실패: 기존 위치에 기물이 없음");
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException("기물 이동 업데이트 실패", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                autoCommitEx.printStackTrace();
            }
        }
    }

    public void resetPieces() {
        String query = "DELETE FROM PIECE";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("보드 리셋 실패", e);
        }
    }

    private Piece mapToPiece(Team team, String pieceTypeName) {
        return switch (pieceTypeName) {
            case "CHARIOT" -> new Chariot(team);
            case "CANNON" -> new Cannon(team);
            case "HORSE" -> new Horse(team);
            case "ELEPHANT" -> new Elephant(team);
            case "GUARD" -> new Guard(team);
            case "SOLDIER" -> new Soldier(team);
            case "GENERAL" -> new General(team);
            default -> throw new IllegalStateException("Unexpected value: " + pieceTypeName);
        };
    }
}
