package janggi.domain.piece.gererator;

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

import static janggi.domain.piece.gererator.KnightElephantSetting.KNIGHT_ELEPHANT_KNIGHT_ELEPHANT;

public class DefaultChoPieceGenerator implements ChoPieceGenerator {

    private final Side SIDE = Side.CHO;

    @Override
    public List<Piece> generate(KnightElephantSetting knightElephantSetting) {
        List<Piece> pieces = new ArrayList<>(generateDefaultPieces());
        pieces.addAll(generateKnightElephants(knightElephantSetting));
        return pieces;
    }

    private List<Piece> generateDefaultPieces() {
        return List.of(
                new Pawn(SIDE, 0, 6),
                new Pawn(SIDE, 2, 6),
                new Pawn(SIDE, 4, 6),
                new Pawn(SIDE, 6, 6),
                new Pawn(SIDE, 8, 6),

                new Cannon(SIDE, 1, 7),
                new Cannon(SIDE, 7, 7),

                new Rook(SIDE, 0, 9),
                new Rook(SIDE, 8, 9),

                new Guard(SIDE, 3, 9),
                new Guard(SIDE, 5, 9),

                new King(SIDE, 4, 8)
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
                new Knight(SIDE, 1, 9),
                new Elephant(SIDE, 2, 9),
                new Knight(SIDE, 6, 9),
                new Elephant(SIDE, 7, 9)
        );
    }

    private List<Piece> generateKnightElephantElephantKnight() {
        return List.of(
                new Knight(SIDE, 1, 9),
                new Elephant(SIDE, 6, 9),
                new Elephant(SIDE, 2, 9),
                new Knight(SIDE, 7, 9)
        );
    }

    private List<Piece> generateElephantKnightKnightElephant() {
        return List.of(
                new Elephant(SIDE, 1, 9),
                new Knight(SIDE, 6, 9),
                new Knight(SIDE, 2, 9),
                new Elephant(SIDE, 7, 9)
        );
    }

    private List<Piece> generateElephantKnightElephantKnight() {
        return List.of(
                new Elephant(SIDE, 1, 9),
                new Knight(SIDE, 2, 9),
                new Elephant(SIDE, 6, 9),
                new Knight(SIDE, 7, 9)
        );
    }
}
