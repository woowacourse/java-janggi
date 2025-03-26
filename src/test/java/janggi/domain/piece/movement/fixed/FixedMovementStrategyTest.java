package janggi.domain.piece.movement.fixed;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.pieces.Pieces;
import janggi.domain.piece.pieces.PiecesView;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class FixedMovementStrategyTest {

    private static final Side ALLEY_SIDE = Side.HAN;
    private static final Side ENEMY_SIDE = Side.CHO;
    private final RawFixedMovementStrategy rawFixedMovementStrategy = new RawFixedMovementStrategy();

    private static Piece createElephant(Side side, int x, int y) {
        return new Piece(PieceType.ELEPHANT, new ElephantMovementStrategy(), side, x, y);
    }

    private static Stream<Arguments> 목적지에_아군이_있다면_움직일_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                createPieces(createElephant(ALLEY_SIDE, 5, 6)),
                ALLEY_SIDE,
                new Position(5, 5),
                new Position(7, 8),
                createPieces(createElephant(ALLEY_SIDE, 7, 8))
            ),
            Arguments.of(
                createPieces(createElephant(ALLEY_SIDE, 5, 6)),
                ALLEY_SIDE,
                new Position(5, 5),
                new Position(8, 7),
                createPieces(createElephant(ALLEY_SIDE, 8, 7))
            )
        );
    }

    private static Stream<Arguments> 목적지에_적군이_있다면_움직일_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                createPieces(createElephant(ALLEY_SIDE, 5, 6)),
                ALLEY_SIDE,
                new Position(5, 5),
                new Position(7, 8),
                createPieces(createElephant(ENEMY_SIDE, 7, 8))
            ),
            Arguments.of(
                createPieces(createElephant(ALLEY_SIDE, 5, 6)),
                ALLEY_SIDE,
                new Position(5, 5),
                new Position(8, 7),
                createPieces(createElephant(ENEMY_SIDE, 8, 7))
            )
        );
    }

    private static Pieces createPieces(Piece... pieces) {
        return new Pieces(Arrays.stream(pieces).collect(Collectors.toMap(Piece::getPosition, Function.identity())));
    }

    @ParameterizedTest
    @MethodSource("목적지에_아군이_있다면_움직일_수_없다_테스트_케이스")
    void 목적지에_아군이_있다면_움직일_수_없다(
        Pieces map,
        Side side,
        Position origin,
        Position destination,
        Pieces onPathPieces
    ) {

        rawFixedMovementStrategy.setIsLegalDestination(true);
        rawFixedMovementStrategy.setAllPiecesOnPath(onPathPieces);

        assertThat(rawFixedMovementStrategy.isMoveable(map, origin, side, destination)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("목적지에_적군이_있다면_움직일_수_없다_테스트_케이스")
    void 목적지에_적군이_있다면_움직일_수_없다(
        Pieces map,
        Side side,
        Position origin,
        Position destination,
        Pieces onPathPieces
    ) {

        rawFixedMovementStrategy.setIsLegalDestination(true);
        rawFixedMovementStrategy.setAllPiecesOnPath(onPathPieces);

        assertThat(rawFixedMovementStrategy.isMoveable(map, origin, side, destination)).isTrue();
    }

    @Test
    void 움직일_수_있는_위치이고_경로에_아무도_없다면_움직일_수_있다() {
        rawFixedMovementStrategy.setIsLegalDestination(true);
        rawFixedMovementStrategy.setAllPiecesOnPath(new Pieces(Map.of()));

        assertThat(rawFixedMovementStrategy.isMoveable(createPieces(), new Position(0, 0), ALLEY_SIDE,
            new Position(0, 0))).isTrue();
    }

    private static final class RawFixedMovementStrategy extends FixedMovementStrategy {

        private boolean isLegalDestination = true;
        private PiecesView allPiecesOnPath = new Pieces(Map.of());

        public void setIsLegalDestination(boolean legalDestination) {
            isLegalDestination = legalDestination;
        }

        public void setAllPiecesOnPath(Pieces allPiecesOnPath) {
            this.allPiecesOnPath = allPiecesOnPath;
        }

        @Override
        public boolean isLegalDestination(Position origin, Position destination) {
            return isLegalDestination;
        }

        @Override
        public PiecesView getAllPiecesOnPath(PiecesView map, Position origin, Position destination) {
            return allPiecesOnPath;
        }
    }
}
