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
        var query = "INSERT INTO piece(piece_id, x, y, team_id, is_catch, piece_type_id) VALUES(?, ?, ?, ?, ?, ?)";
        executeUpdate(query, preparedStatement -> {
            preparedStatement.setInt(1, piece.getId());
            preparedStatement.setInt(2, piece.getCurrentPosition().x());
            preparedStatement.setInt(3, piece.getCurrentPosition().y());
            preparedStatement.setInt(4, piece.getTeam().getId());
            preparedStatement.setBoolean(5, piece.isCatch());
            preparedStatement.setInt(6, piece.getPieceType().getId());
        });
    }

    public Pieces findByTeam(Team team) {
        String query = "SELECT * FROM piece WHERE team_id = ?";
        List<Piece> pieces = executeQuery(query,
                preparedStatement -> preparedStatement.setInt(1, team.getId()),
                this::mapResultSetToPiece
        );
        return new Pieces(pieces);
    }

    public Pieces findCatchAllBy(Team team) {
        String query = "SELECT * FROM piece WHERE team_id = ? AND is_catch = true";
        List<Piece> pieces = executeQuery(query,
                preparedStatement -> preparedStatement.setInt(1, team.getId()),
                this::mapResultSetToPiece
        );
        return new Pieces(pieces);
    }

    public Pieces findAll() {
        String query = "SELECT * FROM piece";
        List<Piece> pieces = executeQuery(query,
                preparedStatement -> {
                },
                this::mapResultSetToPiece
        );
        return new Pieces(pieces);
    }

    public void update(Piece piece, Position destination) {
        var query = "UPDATE piece SET x = ?, y = ? WHERE piece_id = ?";
        executeUpdate(query, preparedStatement -> {
            preparedStatement.setInt(1, destination.x());
            preparedStatement.setInt(2, destination.y());
            preparedStatement.setInt(3, piece.getId());
        });
    }

    public void deleteAllPieces() {
        var query = "TRUNCATE TABLE piece";
        executeUpdate(query, preparedStatement -> {
        });
    }

    private Piece mapResultSetToPiece(ResultSet resultSet) throws SQLException {
        int pieceId = resultSet.getInt("piece_id");
        Team team = Team.findById(resultSet.getInt("team_id"));
        PieceType pieceType = PieceType.findById(resultSet.getInt("piece_type_id"));
        Position position = new Position(resultSet.getInt("x"), resultSet.getInt("y"));

        return pieceType.createPiece(pieceId, team, position);
    }
}
