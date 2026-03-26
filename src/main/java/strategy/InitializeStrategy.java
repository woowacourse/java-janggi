package strategy;

import domain.piece.Cannon;
import domain.piece.Guard;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.Position;
import domain.Team;
import domain.piece.Rook;
import java.util.HashMap;
import java.util.Map;

public abstract class InitializeStrategy {
    public Map<Position, Piece> initialize(Team team) {
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.putAll(initializeDefaultFormation(team));
        pieces.putAll(initializeElephantHorseFormation(team));

        return pieces;
    }

    private Map<Position, Piece> initializeDefaultFormation(Team team) {
        if (team == Team.CHO) {
            return initializeChoDefaultFormation();
        }

        return initializeHanDefaultFormation();
    }

    protected abstract Map<Position, Piece> initializeElephantHorseFormation(Team team);

    private Map<Position, Piece> initializeHanDefaultFormation() {
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(Position.from(1, 1), new Rook(Team.HAN));
        pieces.put(Position.from(1, 4), new Guard(Team.HAN));
        pieces.put(Position.from(1, 5), new King(Team.HAN));
        pieces.put(Position.from(1, 6), new Guard(Team.HAN));
        pieces.put(Position.from(1, 9), new Rook(Team.HAN));

        pieces.put(Position.from(3, 2), new Cannon(Team.HAN));
        pieces.put(Position.from(3, 8), new Cannon(Team.HAN));

        pieces.put(Position.from(4, 1), new Pawn(Team.HAN));
        pieces.put(Position.from(4, 3), new Pawn(Team.HAN));
        pieces.put(Position.from(4, 5), new Pawn(Team.HAN));
        pieces.put(Position.from(4, 7), new Pawn(Team.HAN));
        pieces.put(Position.from(4, 9), new Pawn(Team.HAN));

        return pieces;
    }

    private Map<Position, Piece> initializeChoDefaultFormation() {
        Map<Position, Piece> pieces = new HashMap<>();

        pieces.put(Position.from(10, 1), new Rook(Team.CHO));
        pieces.put(Position.from(10, 4), new Guard(Team.CHO));
        pieces.put(Position.from(10, 5), new King(Team.CHO));
        pieces.put(Position.from(10, 6), new Guard(Team.CHO));
        pieces.put(Position.from(10, 9), new Rook(Team.CHO));

        pieces.put(Position.from(8, 2), new Cannon(Team.CHO));
        pieces.put(Position.from(8, 8), new Cannon(Team.CHO));

        pieces.put(Position.from(7, 1), new Pawn(Team.CHO));
        pieces.put(Position.from(7, 3), new Pawn(Team.CHO));
        pieces.put(Position.from(7, 5), new Pawn(Team.CHO));
        pieces.put(Position.from(7, 9), new Pawn(Team.CHO));
        pieces.put(Position.from(7, 7), new Pawn(Team.CHO));

        return pieces;
    }
}
