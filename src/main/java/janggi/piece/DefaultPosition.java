package janggi.piece;

import janggi.piece.pieces.Cannon;
import janggi.piece.pieces.Chariot;
import janggi.piece.pieces.Elephant;
import janggi.piece.pieces.General;
import janggi.piece.pieces.Horse;
import janggi.piece.pieces.Piece;
import janggi.piece.pieces.Scholar;
import janggi.piece.pieces.Soldier;
import janggi.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum DefaultPosition {
    KING(1, 8, List.of(4), General::new),
    SCHOLAR(0, 9, List.of(3, 5), Scholar::new),
    CAR(0, 9, List.of(0, 8), Chariot::new),
    CANNON(2, 7, List.of(1, 7), Cannon::new),
    JOL(3, 6, List.of(0, 2, 4, 6, 8), Soldier::new),
    HORSE(0, 9, List.of(2, 7), Horse::new),
    ELEPHANT(0, 9, List.of(1, 6), Elephant::new),
    ;

    private final int hanY;
    private final int choY;
    private final List<Integer> xPositions;
    private final Function<Team, Piece> rule;

    DefaultPosition(int hanY, int choY, List<Integer> xPositions, Function<Team, Piece> rule) {
        this.hanY = hanY;
        this.choY = choY;
        this.xPositions = xPositions;
        this.rule = rule;
    }

    public static Map<Position, Piece> createDefaultPieces(DefaultPosition position, Team team) {
        Map<Position, Piece> pieces = new HashMap<>();
        if (team == Team.CHO) {
            position.xPositions.forEach(column -> pieces.put(new Position(column, position.choY),
                    position.rule.apply(team)));
        }
        if (team == Team.HAN) {
            position.xPositions.forEach(xPosition -> pieces.put(new Position(xPosition, position.hanY),
                    position.rule.apply(team)));
        }
        return pieces;
    }
}
