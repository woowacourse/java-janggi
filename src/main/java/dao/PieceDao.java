package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Team;
import model.piece.Piece;
import model.piece.PieceConstructor;
import model.piece.PieceType;
import util.DBConnectionManager;

public class PieceDao {

    private static final String QUERY_EXECUTION_ERROR_MESSAGE = "DB 쿼리 실행 중 오류가 발생했습니다.";

    public void insertAll(int gameId, List<Piece> pieces) {
        for (var piece : pieces) {
            insert(gameId, piece);
        }
    }

    public void insert(int gameId, Piece piece) {
        String query = "INSERT INTO pieces (game_id, x, y, type, team) VALUES (?, ?, ?, ?, ?)";
        DBConnectionManager.useDBConnectionWithStatement(preparedStatement -> {
            try {
                preparedStatement.setInt(1, gameId);
                preparedStatement.setInt(2, piece.getPosition().x());
                preparedStatement.setInt(3, piece.getPosition().y());
                preparedStatement.setString(4, piece.type().name());
                preparedStatement.setString(5, piece.getTeam().name());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                return resultSet.getInt(1);
            } catch (SQLException e) {
                throw new IllegalStateException(QUERY_EXECUTION_ERROR_MESSAGE + e.getMessage());
            }
        }, query, Statement.RETURN_GENERATED_KEYS);
    }

    public void update(Piece piece) {
        String query = "UPDATE pieces SET x=?, y=?, type=?, team=? WHERE id=?";
        DBConnectionManager.useDBConnection(preparedStatement -> {
            try {
                preparedStatement.setInt(1, piece.getPosition().x());
                preparedStatement.setInt(2, piece.getPosition().y());
                preparedStatement.setString(3, piece.type().name());
                preparedStatement.setString(4, piece.getTeam().name());
                preparedStatement.setInt(5, piece.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new IllegalStateException(QUERY_EXECUTION_ERROR_MESSAGE + e.getMessage());
            }
        }, query);
    }

    public List<Piece> selectAllByGameId(int gameId) {
        String query = "SELECT * FROM pieces WHERE game_id=?;";
        return DBConnectionManager.useDBConnection(preparedStatement -> {
            try {
                List<Piece> result = new ArrayList<>();
                preparedStatement.setInt(1, gameId);
                var resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    PieceType pieceType = PieceType.from(resultSet.getString("type"));
                    PieceConstructor constructor = pieceType.getConstructor();
                    int x = resultSet.getInt("x");
                    int y = resultSet.getInt("y");
                    Team team = Team.from(resultSet.getString("team"));
                    Piece piece = constructor.construct(x, y, team);
                    piece.setId(id);
                    result.add(piece);
                }
                return result;
            } catch (SQLException e) {
                throw new IllegalStateException(QUERY_EXECUTION_ERROR_MESSAGE + e.getMessage());
            }
        }, query);
    }

    public void delete(Piece piece) {
        String query = "DELETE FROM pieces WHERE id=?";
        DBConnectionManager.useDBConnection(preparedStatement -> {
            try {
                preparedStatement.setInt(1, piece.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new IllegalStateException(QUERY_EXECUTION_ERROR_MESSAGE + e.getMessage());
            }
        }, query);
    }

    public void deleteAllInGame(int gameId) {
        String query = "DELETE FROM pieces WHERE game_id=?";
        DBConnectionManager.useDBConnection(preparedStatement -> {
            try {
                preparedStatement.setInt(1, gameId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new IllegalStateException(QUERY_EXECUTION_ERROR_MESSAGE + e.getMessage());
            }
        }, query);
    }
}
