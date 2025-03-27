package janggi.domain.piece.generator;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.fixed.ElephantMovementStrategy;
import janggi.domain.piece.movement.fixed.KnightMovementStrategy;
import java.util.List;
import java.util.function.BiFunction;

public enum KnightElephantSetting {

    KNIGHT_ELEPHANT_KNIGHT_ELEPHANT(KnightElephantSetting::makeKnightElephantKnightElephant),
    KNIGHT_ELEPHANT_ELEPHANT_KNIGHT(KnightElephantSetting::makeKnightElephantElephantKnight),
    ELEPHANT_KNIGHT_KNIGHT_ELEPHANT(KnightElephantSetting::makeElephantKnightKnightElephant),
    ELEPHANT_KNIGHT_ELEPHANT_KNIGHT(KnightElephantSetting::makeElephantKnightElephantKnight);

    private final BiFunction<Side, List<Position>, List<Piece>> pieceMaker;

    KnightElephantSetting(BiFunction<Side, List<Position>, List<Piece>> pieceMaker) {
        this.pieceMaker = pieceMaker;
    }

    private static List<Piece> makeKnightElephantKnightElephant(Side side, List<Position> positions) {
        return List.of(
            makeKnight(side, positions.get(0)),
            makeElephant(side, positions.get(1)),
            makeKnight(side, positions.get(2)),
            makeElephant(side, positions.get(3))
        );
    }

    private static List<Piece> makeKnightElephantElephantKnight(Side side, List<Position> positions) {
        return List.of(
            makeKnight(side, positions.get(0)),
            makeElephant(side, positions.get(1)),
            makeElephant(side, positions.get(2)),
            makeKnight(side, positions.get(3))
        );
    }

    private static List<Piece> makeElephantKnightKnightElephant(Side side, List<Position> positions) {
        return List.of(
            makeElephant(side, positions.get(0)),
            makeKnight(side, positions.get(1)),
            makeKnight(side, positions.get(2)),
            makeElephant(side, positions.get(3))
        );
    }

    private static List<Piece> makeElephantKnightElephantKnight(Side side, List<Position> positions) {
        return List.of(
            makeElephant(side, positions.get(0)),
            makeKnight(side, positions.get(1)),
            makeElephant(side, positions.get(2)),
            makeKnight(side, positions.get(3))
        );
    }

    private static Piece makeKnight(Side side, Position position) {
        return new Piece(PieceType.KNIGHT, new KnightMovementStrategy(), side, position.x(), position.y());
    }

    private static Piece makeElephant(Side side, Position position) {
        return new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), side, position.x(), position.y());
    }

    public List<Piece> make(Side side, List<Position> positions) {
        return pieceMaker.apply(side, positions);
    }
}
