package janggi.repository;

import janggi.GameStatus;
import janggi.domain.JanggiGame;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Team;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import janggi.exception.database.GameCreationException;
import janggi.exception.database.GameLoadException;
import janggi.exception.database.GameUpdateException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JdbcJanggiRepository implements JanggiRepository {

    @Override
    public JanggiGame save(Connection conn, JanggiGame game) {
        String gameSql = "INSERT INTO Game (state, turn) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(gameSql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, game.getGameStatus().name());
            pstmt.setString(2, game.getCurrentTeam().name());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int gameId = rs.getInt(1);
                    game.assignId(gameId);

                    savePieces(conn, gameId, game.getBoard());

                    return game;
                }
            }
        } catch (SQLException e) {
            throw new GameCreationException(e);
        }
        throw new RuntimeException("게임 ID 생성에 실패했습니다.");
    }

    @Override
    public void update(Connection conn, JanggiGame game) {
        try {
            String updateGameSql = "UPDATE Game SET turn = ?, state = ? WHERE game_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateGameSql)) {
                pstmt.setString(1, game.getCurrentTeam().name());
                pstmt.setString(2, game.getGameStatus().name());
                pstmt.setInt(3, game.getGameId());
                pstmt.executeUpdate();
            }

            String deletePiecesSql = "DELETE FROM Piece WHERE game_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deletePiecesSql)) {
                pstmt.setInt(1, game.getGameId());
                pstmt.executeUpdate();
            }

            savePieces(conn, game.getGameId(), game.getBoard());

        } catch (SQLException e) {
            throw new GameUpdateException(e);
        }
    }

    @Override
    public Optional<JanggiGame> findInProgressGame(Connection conn) {
        String gameSql = "SELECT * FROM Game WHERE state = 'PROGRESS' ORDER BY game_id ASC LIMIT 1";

        try (PreparedStatement pstmt = conn.prepareStatement(gameSql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int gameId = rs.getInt("game_id");
                String turn = rs.getString("turn");
                String state = rs.getString("state");

                Board board = fetchBoard(conn, gameId);

                return Optional.of(new JanggiGame(gameId, board, Team.from(turn), GameStatus.from(state)));
            }
        } catch (SQLException e) {
            throw new GameLoadException(e);
        }
        return Optional.empty();
    }

    private void savePieces(Connection conn, int gameId, Board board) throws SQLException {
        String pieceSql = "INSERT INTO Piece (game_id, type, team, `row`, col) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(pieceSql)) {
            for (Map.Entry<Position, Piece> entry : board.getBoard().entrySet()) {
                pstmt.setInt(1, gameId);
                pstmt.setString(2, entry.getValue().getPieceType().name());
                pstmt.setString(3, entry.getValue().getTeam().name());
                pstmt.setInt(4, entry.getKey().getRow());
                pstmt.setInt(5, entry.getKey().getColumn());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    private Board fetchBoard(Connection conn, int gameId) throws SQLException {
        String pieceSql = "SELECT * FROM Piece WHERE game_id = ?";
        Map<Position, Piece> pieces = new HashMap<>();

        try (PreparedStatement pstmt = conn.prepareStatement(pieceSql)) {
            pstmt.setInt(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Position pos = Position.of(Row.of(rs.getInt("row")), Column.of(rs.getInt("col")));
                    Piece piece = new Piece(Team.from(rs.getString("team")), PieceType.from(rs.getString("type")));
                    pieces.put(pos, piece);
                }
            }
        }
        return new Board(pieces);
    }
}
