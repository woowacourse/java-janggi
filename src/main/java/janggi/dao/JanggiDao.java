package janggi.dao;

import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.board.Board;
import janggi.domain.turn.ChoTurn;
import janggi.domain.turn.HanTurn;
import janggi.domain.turn.Turn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JanggiDao {

    public void savePieces(int gameID, Board board) {
        String query = "INSERT INTO piece (game_id, team, piece_type, position_column, position_row) VALUES (?,?,?,?,?)";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Map<Position, Piece> currentBoard = board.getBoard();

            for (Map.Entry<Position, Piece> entry : currentBoard.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                preparedStatement.setInt(1, gameID);
                preparedStatement.setString(2, piece.getTeam().name());
                preparedStatement.setString(3, piece.getPieceType().name());
                preparedStatement.setInt(4, position.getColumn());
                preparedStatement.setInt(5, position.getRow());

                preparedStatement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException("기물 저장 실패: " + e.getMessage());
        }
    }

    public void saveGame(Turn currentTurn, Board board) {
        String deleteQuery = "DELETE FROM game";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("기존 저장기록 초기화 실패");
        }

        String insertGameQuery = "INSERT INTO game(game_id, current_turn) VALUES(1,?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertGameQuery)) {
            preparedStatement.setString(1, currentTurn.getTeam().name());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("새 턴 저장 실패");
        }

        savePieces(1, board);
    }

    public Map<Position, Piece> loadBoard() {
        Map<Position, Piece> loadedBoard = new HashMap<>();
        String query = "SELECT team, piece_type, position_column, position_row FROM piece WHERE game_id = 1";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Team team = Team.valueOf(resultSet.getString("team"));
                PieceType pieceType = PieceType.valueOf(resultSet.getString("piece_type"));
                int column = resultSet.getInt("position_column");
                int row = resultSet.getInt("position_row");

                Position position = new Position(column, row);
                Piece piece = new Piece(team, pieceType);
                loadedBoard.put(position, piece);
            }
        } catch (SQLException e) {
            throw new RuntimeException("저장된 장기판 불러오기 실패");
        }
        return loadedBoard;
    }

    public Turn loadCurrentTurn() {
        String query = "SELECT current_turn FROM game WHERE game_id = 1";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String teamName = resultSet.getString("current_turn");
                if (teamName.equals("HAN")) {
                    return new HanTurn();
                }
                return new ChoTurn();
            }
        } catch (SQLException e) {
            throw new RuntimeException("저장된 턴 불러오기 실패: " + e.getMessage());
        }
        throw new IllegalStateException("[ERROR] 저장된 게임이 존재하지 않습니다.");
    }

    public boolean hasSavedGame() {
        String query = "SELECT COUNT(*) FROM game WHERE game_id = 1";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("저장된 게임 확인 실패: " + e.getMessage());
        }
        return false;
    }
}
