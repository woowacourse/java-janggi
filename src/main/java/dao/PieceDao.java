package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.Team;
import model.piece.Piece;
import model.piece.PieceConstructor;
import model.piece.PieceType;
import util.DBConnectionManager;

public class PieceDao {

    public void updateAllByGameId(int gameId, List<Piece> pieces) {
        List<Piece> dbPieces = selectAllByGameId(gameId);
        List<Piece> updateTarget = new ArrayList<>();
        List<Piece> deleteTarget = new ArrayList<>();
        for (var piece : dbPieces) {
            if (pieces.contains(piece)) {
                updateTarget.add(piece);
            }
            else {
                deleteTarget.add(piece);
            }
        }
        updatePieces(updateTarget);
        deletePieces(deleteTarget);
    }

    private void updatePieces(List<Piece> pieces) {
        String query = "UPDATE pieces SET x=?, y=?, piece_type=?, team=? WHERE id=?";
        DBConnectionManager.useDBConnection(preparedStatement -> {
            for (var piece : pieces) {
                try {
                    preparedStatement.setInt(1, piece.getPosition().x());
                    preparedStatement.setInt(2, piece.getPosition().y());
                    preparedStatement.setString(3, piece.type().name());
                    preparedStatement.setInt(4, piece.getId());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    throw new IllegalStateException("DB 쿼리 실행 중 오류가 발생했습니다." + e.getMessage());
                }
            }
        }, query);
    }

    public List<Piece> selectAllByGameId(int gameId) {
        String query = "SELECT id FROM pieces WHERE game_id=?;";
        return DBConnectionManager.useDBConnection(preparedStatement -> {
            try {
                List<Piece> result = new ArrayList<>();
                preparedStatement.setInt(1, gameId);
                var resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    PieceType pieceType = PieceType.from(resultSet.getString("piece_type"));
                    PieceConstructor constructor = pieceType.getConstructor();
                    int x = resultSet.getInt("x");
                    int y = resultSet.getInt("y");
                    Team team = Team.from(resultSet.getString("team"));
                    result.add(constructor.construct(x, y, team));
                }
                return result;
            } catch (SQLException e) {
                throw new IllegalStateException("DB 쿼리 실행 중 오류가 발생했습니다." + e.getMessage());
            }
        }, query);
    }

    private void deletePieces(List<Piece> pieces) {
        String query = "DELETE FROM pieces WHERE game_id=" + piecesIdToQueryCondition(pieces);
        DBConnectionManager.useDBConnection(preparedStatement -> {
            try {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new IllegalStateException("DB 쿼리 실행 중 오류가 발생했습니다." + e.getMessage());
            }
        }, query);
    }

    private String piecesIdToQueryCondition(List<Piece> pieces) {
        String query = pieces.stream()
            .map(piece -> piece.getId())
            .map(id -> String.format("\"%d\"", id))
            .collect(Collectors.joining(","));
        return "(" + query + ")";
    }
}
