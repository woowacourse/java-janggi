package janggi.factory;

import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceBehavior;
import janggi.domain.piece.behavior.palace.General;
import janggi.domain.piece.behavior.palace.Guard;
import janggi.domain.piece.behavior.Soldier;
import janggi.domain.piece.behavior.straightmove.Cannon;
import janggi.domain.piece.behavior.straightmove.Chariot;
import java.util.HashMap;
import java.util.Map;

public enum PieceInitFactory {
    GENERAL1(Team.CHO, 9, 5, new General()),
    GUARD1(Team.CHO, 10, 4, new Guard()),
    GUARD2(Team.CHO, 10, 6, new Guard()),
    CHARIOT1(Team.CHO, 10, 1, new Chariot()),
    CHARIOT2(Team.CHO, 10, 9, new Chariot()),
    CANNON1(Team.CHO, 8, 2, new Cannon()),
    CANNON2(Team.CHO, 8, 8, new Cannon()),
    SOLDIER1(Team.CHO, 7, 1, new Soldier()),
    SOLDIER2(Team.CHO, 7, 3, new Soldier()),
    SOLDIER3(Team.CHO, 7, 5, new Soldier()),
    SOLDIER4(Team.CHO, 7, 7, new Soldier()),
    SOLDIER5(Team.CHO, 7, 9, new Soldier()),
    GENERAL2(Team.HAN, 2, 5, new General()),
    GUARD3(Team.HAN, 1, 4, new Guard()),
    GUARD4(Team.HAN, 1, 6, new Guard()),
    CHARIOT3(Team.HAN, 1, 1, new Chariot()),
    CHARIOT4(Team.HAN, 1, 9, new Chariot()),
    CANNON3(Team.HAN, 3, 2, new Cannon()),
    CANNON4(Team.HAN, 3, 8, new Cannon()),
    SOLDIER6(Team.HAN, 4, 1, new Soldier()),
    SOLDIER7(Team.HAN, 4, 3, new Soldier()),
    SOLDIER8(Team.HAN, 4, 5, new Soldier()),
    SOLDIER9(Team.HAN, 4, 7, new Soldier()),
    SOLDIER10(Team.HAN, 4, 9, new Soldier()),
    ;

    private final Team team;
    private final int row;
    private final int column;
    private final PieceBehavior pieceBehavior;

    PieceInitFactory(Team team, int row, int column, PieceBehavior pieceBehavior) {
        this.team = team;
        this.row = row;
        this.column = column;
        this.pieceBehavior = pieceBehavior;
    }

    public static Map<Position, Piece> initialize() {
        Map<Position, Piece> map = new HashMap<>();

        for (PieceInitFactory value : PieceInitFactory.values()) {
            Position position = Position.of(value.row, value.column);
            Piece piece = new Piece(value.team, value.pieceBehavior);
            map.put(position, piece);
        }

        return map;
    }
}
