package janggi.db;

import janggi.domain.GameContext;
import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRecord;
import janggi.domain.piece.PieceType;
import janggi.domain.team.TeamType;
import janggi.domain.team.TurnManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class GameDao {

    private final DatabaseConnector databaseConnector;

    public GameDao() {
        databaseConnector = new DatabaseConnector();
    }

    public GameDao(String url) {
        databaseConnector = new DatabaseConnector(url);
    }

    public void saveGame(GameContext gameContext) {
        try (Connection connection = databaseConnector.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM PIECE;");
            statement.execute("DELETE FROM GAME;");

            PreparedStatement gameStatement = connection.prepareStatement(
                    "INSERT INTO game (current_turn) VALUES (?);");
            gameStatement.setString(1, gameContext.currentTeamType().toString());
            gameStatement.executeUpdate();

            // 맵을 다 돌면서 해야함
            Map<Position, Piece> positionPieceMap = gameContext.getPositionPieceMap();
            Map<Position, PieceRecord> mapForDB = new HashMap<Position, PieceRecord>();
            for (Map.Entry<Position, Piece> entry : positionPieceMap.entrySet()) {
                mapForDB.put(entry.getKey(), new PieceRecord(1, entry.getKey().getRow(), entry.getKey().getColumn(),
                        entry.getValue().pieceType().toString(), entry.getValue().teamType().toString()));
            }
            for (Map.Entry<Position, PieceRecord> entry : mapForDB.entrySet()) {
                PreparedStatement pieceStatement = connection.prepareStatement(
                        "INSERT INTO piece (game_id, position_row, position_column, piece_type, team_type) VALUES (?, ?, ?, ?, ?)"
                );
                pieceStatement.setInt(1, 1);
                pieceStatement.setInt(2, entry.getKey().getRow());
                pieceStatement.setInt(3, entry.getKey().getColumn());
                pieceStatement.setString(4, entry.getValue().pieceType());
                pieceStatement.setString(5, entry.getValue().teamType());
                pieceStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류");
        }
    }

    public GameContext loadPreviousGame() {
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement gameStatement = connection.prepareStatement(
                    "SELECT current_turn FROM game WHERE id = 1;");
            ResultSet resultSet = gameStatement.executeQuery();
            resultSet.next();
            TurnManager turnManager = new TurnManager(resultSet.getString("current_turn"));

            PreparedStatement pieceStatement = connection.prepareStatement(
                    "SELECT position_row, position_column, piece_type, team_type FROM piece WHERE game_id = 1;");
            resultSet = pieceStatement.executeQuery();
            Map<Position, Piece> positionPieceMap = new HashMap<>();
            while (resultSet.next()) {
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                TeamType teamType = TeamType.valueOf(resultSet.getString("team_type"));

                positionPieceMap.put(
                        Position.valueOf(resultSet.getInt("position_row"), resultSet.getInt("position_column")),
                        pieceType.toPiece(teamType));
            }
            Board board = new Board(positionPieceMap);
            return new GameContext(turnManager, board);
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 오류");
        }
    }
}
