package janggi.domain.piece.movement.normal.fixed;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceMaker;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.pieces.Pieces;
import janggi.domain.piece.pieces.PiecesView;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class KnightMovementStrategyTest {

    private static final Side ALLY_SIDE = Side.CHO;
    private static final KnightMovementStrategy KNIGHT_MOVEMENT_STRATEGY = new KnightMovementStrategy();

    private static Piece createAllyKnight(int x, int y) {
        return PieceMaker.createPiece(PieceType.KNIGHT, ALLY_SIDE, new Position(x, y));
    }

    private static Stream<Arguments> 목적지까지의_경로_상_위치한_모든_기물을_반환한다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                createPieces(
                    createAllyKnight(1, 2),
                    createAllyKnight(2, 3),
                    createAllyKnight(4, 4)
                ),
                new Position(1, 1),
                new Position(2, 3),
                createPieces(
                    createAllyKnight(1, 2),
                    createAllyKnight(2, 3)
                )
            ),

            Arguments.of(
                createPieces(
                    createAllyKnight(5, 4),
                    createAllyKnight(4, 3),
                    createAllyKnight(2, 1)
                ),
                new Position(5, 5),
                new Position(4, 3),
                createPieces(
                    createAllyKnight(5, 4),
                    createAllyKnight(4, 3)
                )
            ),

            Arguments.of(
                createPieces(
                    createAllyKnight(6, 5),
                    createAllyKnight(7, 6),
                    createAllyKnight(2, 1)
                ),
                new Position(5, 5),
                new Position(7, 6),
                createPieces(
                    createAllyKnight(6, 5),
                    createAllyKnight(7, 6)
                )
            ),

            Arguments.of(
                createPieces(
                    createAllyKnight(6, 5),
                    createAllyKnight(7, 4),
                    createAllyKnight(2, 4)
                ),
                new Position(5, 5),
                new Position(7, 4),
                createPieces(
                    createAllyKnight(6, 5),
                    createAllyKnight(7, 4)
                )
            )
        );
    }

    private static Pieces createPieces(Piece... pieces) {
        return new Pieces(Arrays.stream(pieces).collect(Collectors.toMap(Piece::getPosition, Function.identity())));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 5", "4, 2, 3, 5", "1, 2, 2, 5", "4, 2, 3, 5"})
    void 이동하고자_하는_x와의_차이가_1인_경우_이동하고자_하는_y와의_차이가_2이_아니라면_움직일_수_없다(
        int x,
        int y,
        int moveX,
        int moveY
    ) {

        assertThat(KNIGHT_MOVEMENT_STRATEGY.isLegalDestination(new Position(x, y),
            new Position(moveX, moveY))).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 2, 4", "4, 2, 3, 4", "1, 5, 2, 3", "4, 5, 3, 3"})
    void 이동하고자_하는_x와의_차이가_1인_경우_이동하고자_하는_y와의_차이가_2이라면_움직일_수_있다(
        int x,
        int y,
        int moveX,
        int moveY
    ) {

        assertThat(
            KNIGHT_MOVEMENT_STRATEGY.isLegalDestination(new Position(x, y), new Position(moveX, moveY))).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 4, 3", "4, 2, 1, 3", "1, 2, 4, 1", "4, 2, 1, 1"})
    void 이동하고자_하는_x와의_차이가_2인_경우_이동하고자_하는_y와의_차이가_1가_아니라면_움직일_수_없다(
        int x,
        int y,
        int moveX,
        int moveY
    ) {

        assertThat(KNIGHT_MOVEMENT_STRATEGY.isLegalDestination(new Position(x, y),
            new Position(moveX, moveY))).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 3", "4, 2, 2, 3", "1, 2, 3, 1", "4, 2, 2, 1"})
    void 이동하고자_하는_x와의_차이가_2인_경우_이동하고자_하는_y와의_차이가_1라면_움직일_수_있다(
        int x,
        int y,
        int moveX,
        int moveY
    ) {

        assertThat(
            KNIGHT_MOVEMENT_STRATEGY.isLegalDestination(new Position(x, y), new Position(moveX, moveY))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("목적지까지의_경로_상_위치한_모든_기물을_반환한다_테스트_케이스")
    void 목적지까지의_경로_상_위치한_모든_기물을_반환한다(PiecesView map, Position origin, Position destination, PiecesView expected) {
        assertThat(KNIGHT_MOVEMENT_STRATEGY.getAllPiecesOnPath(map, origin, destination)).isEqualTo(expected);
    }
}
