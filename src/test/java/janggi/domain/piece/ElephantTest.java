package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.board.BoardView;
import janggi.domain.vo.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {
    private final Piece elephant = new Elephant(Team.HAN);

    private static Stream<Arguments> provideNormalMove() {
        return Stream.of(
                Arguments.of("북쪽 방향 정상 이동 (북->북동->북동)",
                        new Position(0, 0), new Position(3, 2), new Board()),

                Arguments.of("적군을 잡으며 이동 (북->북서->북서)",
                        new Position(0, 0), new Position(3, 2),
                        Board.createBoardWith(new Position(3, 2), new Soldier(Team.HAN))),

                Arguments.of("남쪽 방향 정상 이동 (남 -> 남서 -> 남서)",
                        new Position(9, 4), new Position(6, 2), new Board()),

                Arguments.of("남쪽 방향 정상 이동 (남 -> 남동 -> 남동)",
                        new Position(7, 3), new Position(4, 5), new Board()),

                Arguments.of("동쪽 방향 정상 이동 (동 -> 북동 -> 북동)",
                        new Position(0, 0), new Position(2, 3), new Board()),

                Arguments.of("동쪽 방향 정상 이동 (동 -> 남동 -> 남동)",
                        new Position(4, 4), new Position(2, 7), new Board()),

                Arguments.of("서쪽 방향 정상 이동 (서 -> 북서 -> 북서)",
                        new Position(0, 8), new Position(2, 5), new Board()),

                Arguments.of("서쪽 방향 정상 이동 (서 -> 남서 -> 남서)",
                        new Position(5, 5), new Position(3, 2), new Board())
        );
    }

    private static Stream<Arguments> provideInvalidMove() {
        return Stream.of(
                Arguments.of("첫 번째 멱(직선 칸)이 막힌 경우",
                        new Position(0, 0), new Position(3, 2),
                        Board.createBoardWith(new Position(1, 0), new Soldier(Team.CHO))),

                Arguments.of("두 번째 멱(대각선 첫 칸)이 막힌 경우",
                        new Position(0, 0), new Position(3, 2),
                        Board.createBoardWith(new Position(2, 1), new Soldier(Team.HAN))),

                Arguments.of("상의 이동 패턴(직선1+대각2)이 아닌 경우",
                        new Position(0, 0), new Position(3, 3), new Board())
        );
    }

    @ParameterizedTest(name = "상 정상 이동: {0}")
    @MethodSource("provideNormalMove")
    void 상_정상_이동_테스트(String description, Position from, Position to, BoardView board) {
        assertThat(elephant.canMove(from, to, board)).isTrue();
    }

    @ParameterizedTest(name = "상 이동 실패: {0}")
    @MethodSource("provideInvalidMove")
    void 상_이동_실패_테스트(String description, Position from, Position to, BoardView board) {
        assertThat(elephant.canMove(from, to, board)).isFalse();
    }
}