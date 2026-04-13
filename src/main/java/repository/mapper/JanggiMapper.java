package repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.GameStatus;
import model.JanggiState;
import model.Team;
import model.coordinate.Position;
import model.piece.Cannon;
import model.piece.Chariot;
import model.piece.Elephant;
import model.piece.General;
import model.piece.Guard;
import model.piece.Horse;
import model.piece.Piece;
import model.piece.PieceType;
import model.piece.Soldier;
import model.state.Finished;
import model.state.Running;

public class JanggiMapper {

    private JanggiMapper() {
    }

    public static Piece mapToPiece(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        Team team = Team.valueOf(rs.getString("team"));

        return switch (PieceType.valueOf(type)) {
            case CHARIOT -> new Chariot(team);
            case CANNON -> new Cannon(team);
            case HORSE -> new Horse(team);
            case ELEPHANT -> new Elephant(team);
            case SOLDIER -> new Soldier(team);
            case GUARD -> new Guard(team);
            case GENERAL -> new General(team);
        };
    }

    public static Position mapToPosition(ResultSet rs) throws SQLException {
        return new Position(rs.getInt("row"), rs.getInt("col"));
    }

    public static JanggiState mapToState(ResultSet rs) throws SQLException {
        Team turn = Team.valueOf(rs.getString("turn"));
        GameStatus status = GameStatus.valueOf(rs.getString("status"));

        return switch (status) {
            case PLAYING -> new Running(turn, GameStatus.PLAYING);
            case BIG_JANG -> new Running(turn, GameStatus.BIG_JANG);
            case DONE -> new Finished(turn, GameStatus.DONE);
            case BIG_JANG_DONE -> new Finished(turn, GameStatus.BIG_JANG_DONE);
        };
    }

    public static GameStatus mapToGameStatus(ResultSet rs) throws SQLException {
        return GameStatus.valueOf(rs.getString("status"));
    }
}