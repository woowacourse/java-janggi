package domain.board.wing;

import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;

public record WingPieces(Piece first, Piece second) {

    private static final int ELEPHANT_COUNT = 1;
    private static final int HORSE_COUNT = 1;

    public WingPieces {
        mustSameSide(first, second);
        mustHaveOneHorseAndOneElephant(List.of(first, second));
    }

    public static WingPieces of(List<Piece> pieces) {
        if (pieces.size() != 2) {
            throw new IllegalArgumentException("진에 놓일 기물은 2개여야 합니다(현재 " + pieces.size() + "개).");
        }
        return new WingPieces(pieces.get(0), pieces.get(1));
    }

    private static void mustSameSide(Piece first, Piece second) {
        if (first.isDifferentSide(second)) {
            throw new IllegalArgumentException("진에 놓일 기물은 서로 같은 진영이어야 합니다.");
        }
    }

    private static void mustHaveOneHorseAndOneElephant(List<Piece> pieces) {
        mustHaveOneElephant(pieces);
        mustHaveOneHorse(pieces);
    }

    private static void mustHaveOneElephant(List<Piece> pieces) {
        long count = pieces.stream()
                .filter(piece -> piece.isSameType(PieceType.ELEPHANT))
                .count();

        if (count != ELEPHANT_COUNT) {
            throw new IllegalArgumentException("상의 기물 수는 " + ELEPHANT_COUNT + "개여야 합니다(현재 기물 수: " + count + "개).");
        }
    }

    private static void mustHaveOneHorse(List<Piece> pieces) {
        long count = pieces.stream()
                .filter(piece -> piece.isSameType(PieceType.HORSE))
                .count();

        if (count != HORSE_COUNT) {
            throw new IllegalArgumentException("마의 기물 수는 " + HORSE_COUNT + "개여야 합니다(현재 기물 수: " + count + "개).");
        }
    }

    public boolean isDifferentSide(Side side) {
        return first.isDifferentSide(side);
    }
}
