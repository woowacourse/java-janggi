package domain;

import static java.util.Map.entry;

import domain.piece.PieceType;
import java.util.Map;

public class Score {

    private final Map<PieceType, Integer> scores;

    public Score() {
        this.scores = Map.ofEntries(
                entry(PieceType.GENERAL, 0),
                entry(PieceType.SOLDIER, 2),
                entry(PieceType.GUARD, 3),
                entry(PieceType.ELEPHANT, 3),
                entry(PieceType.HORSE, 5),
                entry(PieceType.CANNON, 7),
                entry(PieceType.CHARIOT, 13)
        );
    }
}
