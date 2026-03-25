package domain.board;

import domain.game.Side;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public abstract class Wing {

    private static final int WING_SIZE = 2;
    private static final int ELEPHANT_COUNT = 1;
    private static final int HORSE_COUNT = 1;

    protected final Piece first;
    protected final Piece second;

    public Wing(List<Piece> pieces) {
        validatePieces(pieces);

        this.first = pieces.get(0);
        this.second = pieces.get(1);
    }

    private static void validatePieces(List<Piece> pieces) {
        validateSize(pieces);
        validateElephantCount(pieces);
        validateHorseCount(pieces);
    }

    private static void validateSize(List<Piece> pieces) {
        if (pieces.size() != WING_SIZE) {
            throw new IllegalArgumentException("진의 기물 수는 " + WING_SIZE + "개여야 합니다(현재 기물 수: " + pieces.size() + "개).");
        }
    }

    private static void validateElephantCount(List<Piece> pieces) {
        long count = pieces.stream()
                .filter(piece -> piece instanceof Elephant)
                .count();

        if (count != ELEPHANT_COUNT) {
            throw new IllegalArgumentException("상의 기물 수는 " + ELEPHANT_COUNT + "개여야 합니다(현재 기물 수: " + count + "개).");
        }
    }

    private static void validateHorseCount(List<Piece> pieces) {
        long count = pieces.stream()
                .filter(piece -> piece instanceof Horse)
                .count();

        if (count != HORSE_COUNT) {
            throw new IllegalArgumentException("마의 기물 수는 " + HORSE_COUNT + "개여야 합니다(현재 기물 수: " + count + "개).");
        }
    }

    public abstract Map<Intersection, Piece> setUpPieces(Side side);
}
