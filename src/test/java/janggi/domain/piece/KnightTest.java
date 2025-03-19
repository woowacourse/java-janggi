package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.domain.Position;
import janggi.domain.ReplaceUnderBar;
import janggi.domain.Side;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class KnightTest {

    private static final Side ALLY_SIDE = Side.CHO;
    private static final Side ENEMY_SIDE = Side.HAN;
    private static final Position DEFAULT_POSITION = new Position(5, 5);

    private static Knight DEFAULT_ALLY_KNIGHT = new Knight(ALLY_SIDE, DEFAULT_POSITION);

    private static Knight createAllyKnight(int x, int y) {
        return new Knight(ALLY_SIDE, new Position(x, y));
    }

    private static Knight createEnemyKnight(int x, int y) {
        return new Knight(ENEMY_SIDE, new Position(x, y));
    }

    private static Stream<Arguments> 이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(5, 6)),
                        4,
                        7
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(5, 6)),
                        6,
                        7
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(6, 5)),
                        7,
                        6
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(6, 5)),
                        7,
                        4
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(5, 4)),
                        6,
                        3
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(5, 4)),
                        4,
                        3
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(4, 5)),
                        3,
                        6
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(4, 5)),
                        3,
                        4
                )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_경로에_다른_기물이_존재하지_않으면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(),
                        4,
                        7
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(),
                        6,
                        7
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(),
                        7,
                        6
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(),
                        7,
                        4
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(),
                        6,
                        3
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(),
                        4,
                        3
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(),
                        3,
                        6
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(),
                        3,
                        4
                )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createEnemyKnight(4, 7)),
                        4,
                        7
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createEnemyKnight(6, 7)),
                        6,
                        7
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createEnemyKnight(7, 6)),
                        7,
                        6
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createEnemyKnight(7, 4)),
                        7,
                        4
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createEnemyKnight(6, 3)),
                        6,
                        3
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createEnemyKnight(4, 3)),
                        4,
                        3
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createEnemyKnight(3, 6)),
                        3,
                        6
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createEnemyKnight(3, 4)),
                        3,
                        4
                )
        );
    }

    private static Stream<Arguments> 이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(4, 7)),
                        4,
                        7
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(6, 7)),
                        6,
                        7
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(7, 6)),
                        7,
                        6
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(7, 4)),
                        7,
                        4
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(6, 3)),
                        6,
                        3
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(4, 3)),
                        4,
                        3
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(3, 6)),
                        3,
                        6
                ),
                Arguments.of(
                        DEFAULT_ALLY_KNIGHT,
                        List.of(createAllyKnight(3, 4)),
                        3,
                        4
                )
        );
    }

    public static Stream<Arguments> 이동_가능한_경로가_좌표_밖에_있다면_계산되지_않는다_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        createAllyKnight(0, 0),
                        1,
                        2
                ),
                Arguments.of(
                        createAllyKnight(8, 0),
                        7,
                        2
                ),
                Arguments.of(
                        createAllyKnight(8, 9),
                        7,
                        7
                ),
                Arguments.of(
                        createAllyKnight(0, 9),
                        2,
                        8
                )
        );
    }

    @BeforeEach
    void setUpDefaultAllyKnight() {
        DEFAULT_ALLY_KNIGHT = new Knight(ALLY_SIDE, DEFAULT_POSITION);
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_경로에_다른_기물이_존재하면_이동할_수_없다(Knight knight, List<Piece> existingPieces, int x, int y) {
        assertThat(knight.isMoveablePath(existingPieces, new Position(x, y))).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_경로에_다른_기물이_존재하지_않으면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_경로에_다른_기물이_존재하지_않으면_이동할_수_있다(Knight knight, List<Piece> existingPieces, int x, int y) {
        assertThat(knight.isMoveablePath(existingPieces, new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다_테스트_케이스")
    void 이동하고자_하는_위치에_적_기물이_있으면_이동할_수_있다(Knight knight, List<Piece> existingPieces, int x, int y) {
        assertThat(knight.isMoveablePath(existingPieces, new Position(x, y))).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다_테스트_케이스")
    void 이동하고자_하는_위치에_아군_기물이_있으면_이동할_수_없다(Knight knight, List<Piece> existingPieces, int x, int y) {
        assertThat(knight.isMoveablePath(existingPieces, new Position(x, y))).isFalse();
    }

    @ParameterizedTest
    @MethodSource("이동_가능한_경로가_좌표_밖에_있다면_계산되지_않는다_테스트_케이스")
    void 이동_가능한_경로가_좌표_밖에_있다면_계산되지_않는다(Knight knight, int x, int y) {
        assertThatCode(() -> knight.move(List.of(), x, y)).doesNotThrowAnyException();
    }
}
