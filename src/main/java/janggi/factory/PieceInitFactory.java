package janggi.factory;

import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;

public enum PieceInitFactory {
    GENERAL1(PieceType.GENERAL, Team.CHO, 9, 5),
    GUARD1(PieceType.GUARD, Team.CHO, 10, 4),
    GUARD2(PieceType.GUARD, Team.CHO, 10, 6),
    CHARIOT1(PieceType.CHARIOT, Team.CHO, 10, 1),
    CHARIOT2(PieceType.CHARIOT, Team.CHO, 10, 9),
    CANNON1(PieceType.CANNON, Team.CHO, 8, 2),
    CANNON2(PieceType.CANNON, Team.CHO, 8, 8),
    SOLDIER1(PieceType.SOLDIER, Team.CHO, 7, 1),
    SOLDIER2(PieceType.SOLDIER, Team.CHO, 7, 3),
    SOLDIER3(PieceType.SOLDIER, Team.CHO, 7, 5),
    SOLDIER4(PieceType.SOLDIER, Team.CHO, 7, 7),
    SOLDIER5(PieceType.SOLDIER, Team.CHO, 7, 9),
    GENERAL2(PieceType.GENERAL, Team.HAN, 2, 5),
    GUARD3(PieceType.GUARD, Team.HAN, 1, 4),
    GUARD4(PieceType.GUARD, Team.HAN, 1, 6),
    CHARIOT3(PieceType.CHARIOT, Team.HAN, 1, 1),
    CHARIOT4(PieceType.CHARIOT, Team.HAN, 1, 9),
    CANNON3(PieceType.CANNON, Team.HAN, 3, 2),
    CANNON4(PieceType.CANNON, Team.HAN, 3, 8),
    SOLDIER6(PieceType.SOLDIER, Team.HAN, 4, 1),
    SOLDIER7(PieceType.SOLDIER, Team.HAN, 4, 3),
    SOLDIER8(PieceType.SOLDIER, Team.HAN, 4, 5),
    SOLDIER9(PieceType.SOLDIER, Team.HAN, 4, 7),
    SOLDIER10(PieceType.SOLDIER, Team.HAN, 4, 9),
    ;

    private final PieceType pieceType;
    private final Team team;
    private final int row;
    private final int column;

    PieceInitFactory(PieceType pieceType, Team team, int row, int column) {
        this.pieceType = pieceType;
        this.team = team;
        this.row = row;
        this.column = column;
    }

    public static Map<Position, Piece> initialize() {
        Map<Position, Piece> map = new HashMap<>();

        for (PieceInitFactory value : PieceInitFactory.values()) {
            Position position = Position.of(value.row, value.column);
            Piece piece = PieceFactory.create(value.pieceType, value.team);
            map.put(position, piece);
        }

        return map;
    }
}
