package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.ReplaceUnderBar;
import janggi.domain.Side;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
public class PawnTest {

    private static final Side ALLY_SIDE = Side.HAN;
    private static final Side ENEMY_SIDE = Side.CHO;
    private static final Position DEFAULT_POSITION = new Position(5, 5);

    private static final Pawn DEFAULT_ALLY_PAWN = new Pawn(ALLY_SIDE, DEFAULT_POSITION.getX(), DEFAULT_POSITION.getY());

    private static Pawn createAllyPawn(int x, int y) {
        return new Pawn(ALLY_SIDE, x, y);
    }

    private static Pawn createEnemyPawn(int x, int y) {
        return new Pawn(ENEMY_SIDE, x, y);
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        DEFAULT_ALLY_PAWN,
                        List.of(createEnemyPawn(4, 5)),
                        4,
                        5
                ),
                Arguments.of(
                        DEFAULT_ALLY_PAWN,
                        List.of(createEnemyPawn(6, 5)),
                        6,
                        5
                ),
                Arguments.of(
                        DEFAULT_ALLY_PAWN,
                        List.of(createEnemyPawn(5, 6)),
                        5,
                        6
                ),

                Arguments.of(
                        createEnemyPawn(5, 5),
                        List.of(createAllyPawn(4, 5)),
                        4,
                        5
                ),
                Arguments.of(
                        createEnemyPawn(5, 5),
                        List.of(createAllyPawn(6, 5)),
                        6,
                        5
                ),
                Arguments.of(
                        createEnemyPawn(5, 5),
                        List.of(createAllyPawn(5, 4)),
                        5,
                        4
                )
        );
    }

    public static Stream<Arguments> 이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        DEFAULT_ALLY_PAWN,
                        List.of(createAllyPawn(4, 5)),
                        4,
                        5
                ),
                Arguments.of(
                        DEFAULT_ALLY_PAWN,
                        List.of(createAllyPawn(6, 5)),
                        6,
                        5
                ),
                Arguments.of(
                        DEFAULT_ALLY_PAWN,
                        List.of(createAllyPawn(5, 6)),
                        5,
                        6
                ),

                Arguments.of(
                        createEnemyPawn(5, 5),
                        List.of(createEnemyPawn(4, 5)),
                        4,
                        5
                ),
                Arguments.of(
                        createEnemyPawn(5, 5),
                        List.of(createEnemyPawn(6, 5)),
                        6,
                        5
                ),
                Arguments.of(
                        createEnemyPawn(5, 5),
                        List.of(createEnemyPawn(5, 6)),
                        5,
                        6
                )
        );
    }

    public static Stream<Arguments> 앞과_양옆으로_이동할_수_있다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        createAllyPawn(5, 5),
                        6,
                        5
                ),
                Arguments.of(
                        createAllyPawn(5, 5),
                        5,
                        6
                ),
                Arguments.of(
                        createAllyPawn(5, 5),
                        4,
                        5
                ),

                Arguments.of(
                        createEnemyPawn(5, 5),
                        6,
                        5
                ),
                Arguments.of(
                        createEnemyPawn(5, 5),
                        5,
                        4
                ),
                Arguments.of(
                        createEnemyPawn(5, 5),
                        4,
                        5
                )
        );
    }

    public static Stream<Arguments> 앞과_양옆외에는_이동할_수_없다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        createAllyPawn(5, 5),
                        7,
                        5
                ),
                Arguments.of(
                        createAllyPawn(5, 5),
                        5,
                        7
                ),
                Arguments.of(
                        createAllyPawn(5, 5),
                        3,
                        5
                ),

                Arguments.of(
                        createEnemyPawn(5, 5),
                        7,
                        5
                ),
                Arguments.of(
                        createEnemyPawn(5, 5),
                        5,
                        3
                ),
                Arguments.of(
                        createEnemyPawn(5, 5),
                        3,
                        5
                ),

                Arguments.of(
                        createAllyPawn(5, 5),
                        5,
                        4
                ),
                Arguments.of(
                        createEnemyPawn(5, 5),
                        5,
                        6
                )
        );
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다(Pawn pawn, List<Piece> existingPieces, int x, int y) {
        assertThat(pawn.isMoveablePath(existingPieces, new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다(Pawn pawn, List<Piece> existingPieces, int x, int y) {
        assertThat(pawn.isMoveablePath(existingPieces, new Position(x, y))).isFalse();
    }

    @ParameterizedTest
    @MethodSource("앞과_양옆으로_이동할_수_있다_테스트_케이스")
    void 앞과_양옆으로_이동할_수_있다(Pawn pawn, int x, int y) {
        assertThat(pawn.isMoveablePosition(new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("앞과_양옆외에는_이동할_수_없다_테스트_케이스")
    void 앞과_양옆외에는_이동할_수_없다(Pawn pawn, int x, int y) {
        assertThat(pawn.isMoveablePosition(new Position(x, y))).isFalse();
    }
}
