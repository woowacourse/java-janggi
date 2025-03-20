package janggi.domain.piece.gererator;

import static janggi.domain.piece.gererator.KnightElephantSetting.KNIGHT_ELEPHANT_KNIGHT_ELEPHANT;

import janggi.domain.Side;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.King;
import janggi.domain.piece.Knight;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Rook;
import java.util.ArrayList;
import java.util.List;

public class DefaultHanPieceGenerator implements HanPieceGenerator {

    private static final Side SIDE = Side.HAN;

    @Override
    public List<Piece> generate(KnightElephantSetting knightElephantSetting) {
        List<Piece> pieces = new ArrayList<>(generateDefaultPieces());
        pieces.addAll(generateKnightElephants(knightElephantSetting));
        return pieces;
    }

    private List<Piece> generateDefaultPieces() {
        return List.of(
                new Pawn(SIDE, 0, 3),
                new Pawn(SIDE, 2, 3),
                new Pawn(SIDE, 4, 3),
                new Pawn(SIDE, 6, 3),
                new Pawn(SIDE, 8, 3),

                new Cannon(SIDE, 1, 2),
                new Cannon(SIDE, 7, 2),

                new Rook(SIDE, 0, 0),
                new Rook(SIDE, 8, 0),

                new Guard(SIDE, 3, 0),
                new Guard(SIDE, 5, 0),

                new King(SIDE, 4, 1)
        );
    }

    private List<Piece> generateKnightElephants(KnightElephantSetting knightElephantSetting) {

        if (knightElephantSetting == KNIGHT_ELEPHANT_KNIGHT_ELEPHANT) {
            return generateKnightElephantKnightElephant();
        }
        if (knightElephantSetting == KnightElephantSetting.KNIGHT_ELEPHANT_ELEPHANT_KNIGHT) {
            return generateKnightElephantElephantKnight();
        }
        if (knightElephantSetting == KnightElephantSetting.ELEPHANT_KNIGHT_KNIGHT_ELEPHANT) {
            return generateElephantKnightKnightElephant();
        }
        return generateElephantKnightElephantKnight();
    }

    private List<Piece> generateKnightElephantKnightElephant() {
        return List.of(
                new Knight(SIDE, 1, 0),
                new Elephant(SIDE, 2, 0),
                new Knight(SIDE, 6, 0),
                new Elephant(SIDE, 7, 0)
        );
    }

    private List<Piece> generateKnightElephantElephantKnight() {
        return List.of(
                new Knight(SIDE, 1, 0),
                new Elephant(SIDE, 6, 0),
                new Elephant(SIDE, 2, 0),
                new Knight(SIDE, 7, 0)
        );
    }

    private List<Piece> generateElephantKnightKnightElephant() {
        return List.of(
                new Elephant(SIDE, 1, 0),
                new Knight(SIDE, 6, 0),
                new Knight(SIDE, 2, 0),
                new Elephant(SIDE, 7, 0)
        );
    }

    private List<Piece> generateElephantKnightElephantKnight() {
        return List.of(
                new Elephant(SIDE, 1, 0),
                new Knight(SIDE, 6, 0),
                new Elephant(SIDE, 2, 0),
                new Knight(SIDE, 7, 0)
        );
    }
}
