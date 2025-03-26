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
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class ElephantMovementStrategyTest {

    private static final Side ALLY_SIDE = Side.CHO;
    private static final ElephantMovementStrategy ELEPHANT_MOVEMENT_STRATEGY = new ElephantMovementStrategy();

    private static Piece createAllyElephant(int x, int y) {
        return new Piece(PieceType.ELEPHANT, ELEPHANT_MOVEMENT_STRATEGY, ALLY_SIDE, x, y);
    }


    private static Stream<Arguments> 목적지까지의_경로_상_위치한_모든_기물을_반환한다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                createPieces(
                    createAllyElephant(1, 2),
                    createAllyElephant(2, 3),
                    createAllyElephant(3, 4),
                    createAllyElephant(4, 4)
                ),
                new Position(1, 1),
                new Position(3, 4),
                createPieces(
                    createAllyElephant(1, 2),
                    createAllyElephant(2, 3),
                    createAllyElephant(3, 4)
                )
            ),

            Arguments.of(
                createPieces(
                    createAllyElephant(5, 4),
                    createAllyElephant(4, 3),
                    createAllyElephant(3, 2),
                    createAllyElephant(2, 1)
                ),
                new Position(5, 5),
                new Position(3, 2),
                createPieces(
                    createAllyElephant(5, 4),
                    createAllyElephant(4, 3),
                    createAllyElephant(3, 2)
                )
            ),

            Arguments.of(
                createPieces(
                    createAllyElephant(6, 5),
                    createAllyElephant(7, 6),
                    createAllyElephant(8, 7),
                    createAllyElephant(2, 1)
                ),
                new Position(5, 5),
                new Position(8, 7),
                createPieces(
                    createAllyElephant(6, 5),
                    createAllyElephant(7, 6),
                    createAllyElephant(8, 7)
                )
            ),

            Arguments.of(
                createPieces(
                    createAllyElephant(6, 5),
                    createAllyElephant(7, 4),
                    createAllyElephant(8, 3),
                    createAllyElephant(2, 4)
                ),
                new Position(5, 5),
                new Position(8, 3),
                createPieces(
                    createAllyElephant(6, 5),
                    createAllyElephant(7, 4),
                    createAllyElephant(8, 3)
                )
            )
        );
    }

    private static Pieces createPieces(Piece... pieces) {
        return new Pieces(Arrays.stream(pieces).collect(Collectors.toMap(Piece::getPosition, Function.identity())));
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 4", "4, 2, 2, 4", "1, 2, 3, 0", "4, 2, 2, 0"})
    void 이동하고자_하는_x와의_차이가_2인_경우_이동하고자_하는_y와의_차이가_3이_아니라면_움직일_수_없다(
        int x,
        int y,
        int moveX,
        int moveY
    ) {

        assertThat(ELEPHANT_MOVEMENT_STRATEGY.isLegalDestination(new Position(x, y),
            new Position(moveX, moveY))).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 3, 5", "4, 2, 2, 5", "1, 5, 3, 2", "4, 5, 2, 2"})
    void 이동하고자_하는_x와의_차이가_2인_경우_이동하고자_하는_y와의_차이가_3이라면_움직일_수_있다(
        int x,
        int y,
        int moveX,
        int moveY
    ) {

        assertThat(
            ELEPHANT_MOVEMENT_STRATEGY.isLegalDestination(new Position(x, y), new Position(moveX, moveY))).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 4, 3", "4, 2, 1, 3", "1, 2, 4, 1", "4, 2, 1, 1"})
    void 이동하고자_하는_x와의_차이가_3인_경우_이동하고자_하는_y와의_차이가_2가_아니라면_움직일_수_없다(
        int x,
        int y,
        int moveX,
        int moveY
    ) {

        assertThat(ELEPHANT_MOVEMENT_STRATEGY.isLegalDestination(new Position(x, y),
            new Position(moveX, moveY))).isFalse();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, 2, 4, 4", "4, 2, 1, 4", "1, 2, 4, 0", "4, 2, 1, 0"})
    void 이동하고자_하는_x와의_차이가_3인_경우_이동하고자_하는_y와의_차이가_2라면_움직일_수_있다(
        int x,
        int y,
        int moveX,
        int moveY
    ) {

        assertThat(
            ELEPHANT_MOVEMENT_STRATEGY.isLegalDestination(new Position(x, y), new Position(moveX, moveY))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("목적지까지의_경로_상_위치한_모든_기물을_반환한다_테스트_케이스")
    void 목적지까지의_경로_상_위치한_모든_기물을_반환한다(PiecesView map, Position origin, Position destination, PiecesView expected) {
        assertThat(ELEPHANT_MOVEMENT_STRATEGY.getAllPiecesOnPath(map, origin, destination)).isEqualTo(expected);
    }
}
