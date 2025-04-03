package dao;

import game.Team;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import location.Position;
import piece.Piece;
import piece.PieceType;
import piece.Pieces;

public class PieceDao extends BaseDao {

    public void add(Piece piece) {
        var query = "INSERT INTO piece(piece_id, x, y, team, is_catch, piece_type, board_id) VALUES(?, ?, ?, ?, ?, ?, 1)";
        executeUpdate(query, preparedStatement -> {
            preparedStatement.setInt(1, piece.getId());
            preparedStatement.setInt(2, piece.getCurrentPosition().x());
            preparedStatement.setInt(3, piece.getCurrentPosition().y());
            preparedStatement.setString(4, piece.getTeam().name().toLowerCase());
            preparedStatement.setBoolean(5, piece.isCatch());
            preparedStatement.setString(6, piece.getPieceType().name().toLowerCase());
        });
    }

    public void addAll(List<Piece> pieces) {
        pieces.forEach(
                this::add
        );
    }

    public Pieces findByTeam(Team team) {
        var query = "SELECT * FROM piece WHERE team = ?";
        List<Piece> pieces = executeQuery(query,
                preparedStatement -> preparedStatement.setString(1, team.name().toLowerCase()),
                this::mapResultSetToPiece
        );
        return new Pieces(pieces);
    }

    public Pieces findCatchAllBy(Team team) {
        var query = "SELECT * FROM piece WHERE team = ? AND is_catch = true";
        List<Piece> pieces = executeQuery(query,
                preparedStatement -> preparedStatement.setString(1, team.name().toLowerCase()),
                this::mapResultSetToPiece
        );
        return new Pieces(pieces);
    }

    public Pieces findAll() {
        var query = "SELECT * FROM piece";
        List<Piece> pieces = executeQuery(query,
                preparedStatement -> {
                },
                this::mapResultSetToPiece
        );
        return new Pieces(pieces);
    }

    public void updatePosition(Piece piece) {
        var query = "UPDATE piece SET x = ?, y = ? WHERE piece_id = ?";
        executeUpdate(query, preparedStatement -> {
            preparedStatement.setInt(1, piece.getCurrentPosition().x());
            preparedStatement.setInt(2, piece.getCurrentPosition().y());
            preparedStatement.setInt(3, piece.getId());
        });
    }

    public void updateCatch(Piece piece) {
        var query = "UPDATE piece SET is_catch = ? WHERE piece_id = ?";
        executeUpdate(query, preparedStatement -> {
            preparedStatement.setBoolean(1, piece.isCatch());
            preparedStatement.setInt(2, piece.getId());
        });
    }

    public void deleteAllPieces() {
        var query = "TRUNCATE TABLE piece";
        executeUpdate(query, preparedStatement -> {
        });
    }

    private Piece mapResultSetToPiece(ResultSet resultSet) throws SQLException {
        int pieceId = resultSet.getInt("piece_id");
        Team team = Team.fromName(resultSet.getString("team"));
        PieceType pieceType = PieceType.fromName(resultSet.getString("piece_type"));
        Position position = new Position(resultSet.getInt("x"), resultSet.getInt("y"));

        return pieceType.createPiece(pieceId, team, position);
    }
}
