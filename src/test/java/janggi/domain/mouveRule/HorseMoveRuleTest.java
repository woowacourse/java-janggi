package janggi.domain.mouveRule;

import janggi.domain.FakeBoard;
import janggi.domain.board.BoardView;
import janggi.domain.piece.Soldier;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class HorseMoveRuleTest {
    private final MoveRule moveRule = new HorseMoveRule();

    @ParameterizedTest(name = "마 정상 이동: {0}")
    @MethodSource("provideNormalMove")
    void 마_정상_이동_테스트(String description, Position from, Position to, BoardView board) {
        assertThat(moveRule.canMove(from, to, board)).isTrue();
    }

    @ParameterizedTest(name = "마 이동 실패: {0}")
    @MethodSource("provideInvalidMove")
    void 마_이동_실패_테스트(String description, Position from, Position to, BoardView board) {
        assertThat(moveRule.canMove(from, to, board)).isFalse();
    }

    private static Stream<Arguments> provideNormalMove() {
        return Stream.of(
                Arguments.of("남쪽 방향 정상 이동 (남->남동)",
                        new Position(0, 0), new Position(2, 1), new FakeBoard()),
                Arguments.of("남쪽 방향 정상 이동 (남->남서)",
                        new Position(0, 4), new Position(2, 3), new FakeBoard()),

                Arguments.of("북쪽 방향 정상 이동 (북->북동)",
                        new Position(9, 4), new Position(7, 5), new FakeBoard()),
                Arguments.of("북쪽 방향 정상 이동 (북->북서)",
                        new Position(9, 8), new Position(7, 7), new FakeBoard()),

                Arguments.of("동쪽 방향 정상 이동 (동->동북)",
                        new Position(4, 4), new Position(3, 6), new FakeBoard()),
                Arguments.of("동쪽 방향 정상 이동 (동->동남)",
                        new Position(4, 4), new Position(5, 6), new FakeBoard()),

                Arguments.of("서쪽 방향 정상 이동 (서->서북)",
                        new Position(4, 4), new Position(3, 2), new FakeBoard()),
                Arguments.of("서쪽 방향 정상 이동 (서->서남)",
                        new Position(4, 4), new Position(5, 2), new FakeBoard()),

                Arguments.of("적군을 잡으며 이동 (서->서북)",
                        new Position(4, 4), new Position(3, 2),
                        FakeBoard.createBoardWith(new Position(3, 2), new Soldier(Team.HAN)))
                );
    }

    private static Stream<Arguments> provideInvalidMove() {
        return Stream.of(
                Arguments.of("길이 아군에 의해 막힌 경우",
                        new Position(0, 0), new Position(2, 1),
                        FakeBoard.createBoardWith(new Position(1, 0), new Soldier(Team.CHO))),

                Arguments.of("길이 적군에 의해 막힌 경우",
                        new Position(0, 0), new Position(2, 1),
                        FakeBoard.createBoardWith(new Position(1, 0), new Soldier(Team.HAN))),

                Arguments.of("마의 이동 패턴이 아닌 경우",
                        new Position(0, 0), new Position(2, 2), new FakeBoard())
        );
    }
}
