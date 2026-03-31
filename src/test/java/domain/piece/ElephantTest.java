package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Position;
import domain.Team;
import domain.strategy.NoInitializeStrategy;
import domain.stub.StubBoard;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import strategy.CustomInitializeStrategy;
import strategy.InitializeStrategy;

class ElephantTest {
    private final InitializeStrategy strategy = new NoInitializeStrategy();

    /**
     * 직진 1칸 + 대각선 2칸만 이동 가능
     */
    @ParameterizedTest
    @MethodSource("invalidDirectionsProvider")
    void 코끼리의_이동범위가_직진1칸_대각선_2칸이_아닌_경우_움직일수_없다(Position to) {
        // given
        Board board = new Board(strategy, strategy);
        Piece elephant = new Elephant(Team.CHO);

        // when
        Position from = Position.from(7, 1);

        // then
        assertThat(elephant.canMove(from, to, board)).isEqualTo(false);
    }

    static Stream<Position> invalidDirectionsProvider() {
        return Stream.of(
                Position.from(6, 1),
                Position.from(5, 1),
                Position.from(6, 2),
                Position.from(7, 3)
        );
    }

    /**
     * 도착지에 같은 팀이 존재하는 경우 이동 불가
     */
    @Test
    void 도착지에_같은_팀이_존재하는_경우_이동_불가능하다() {
        Piece elephant = new Elephant(Team.CHO);

        Position from = Position.from(1, 1);
        Position to = Position.from(3, 4);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Elephant(Team.CHO));
        testPiece.put(to, new Pawn(Team.CHO));

        InitializeStrategy customStrategy = new CustomInitializeStrategy(testPiece);
        Board board = new Board(customStrategy, strategy);

        // when & then
        assertThat(elephant.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 도착지랑 출발지 사이에 말이 존재하지 않는 경우 이동 가능
     */
    @Test
    void 도착지랑_출발지_사이에_말이_존재하는_경우_이동_불가능하다() {
        Piece elephant = new Elephant(Team.CHO);

        Position from = Position.from(1, 1);
        Position pathPosition = Position.from(2, 3);
        Position to = Position.from(3, 4);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Elephant(Team.CHO));
        testPiece.put(pathPosition, new Pawn(Team.CHO));

        InitializeStrategy customStrategy = new CustomInitializeStrategy(testPiece);
        Board board = new Board(customStrategy, strategy);

        // when & then
        assertThat(elephant.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 정상테스트 1. 도착지에 같은 팀이 존재하지 않고, 2. 도착지와 출발지 사이에 다른 기물이 존재하지 않는다면 이동 가능
     */
    @ParameterizedTest
    @MethodSource("validDirectionsProvider")
    void 도착지에_같은_팀이_존재하지_않고_경로에_다른_기물이_존재하지_않을_경우(Position to, Position from) {
        // given
        Piece elephant = new Elephant(Team.CHO);

        // when
        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Elephant(Team.CHO));

        InitializeStrategy customStrategy = new CustomInitializeStrategy(testPiece);
        Board board = new Board(customStrategy, strategy);

        // then
        assertThat(elephant.canMove(from, to, board)).isEqualTo(true);
    }

    static Stream<Arguments> validDirectionsProvider() {
        return Stream.of(
                Arguments.of(Position.from(10, 3), Position.from(7, 5)),
                Arguments.of(Position.from(10, 5), Position.from(7, 3)),
                Arguments.of(Position.from(7, 3), Position.from(10, 5)),
                Arguments.of(Position.from(7, 5), Position.from(10, 3))
        );
    }
}
