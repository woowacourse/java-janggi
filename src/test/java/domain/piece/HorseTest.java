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
import org.junit.jupiter.params.provider.MethodSource;
import strategy.InitializeStrategy;

public class HorseTest {
    private final InitializeStrategy strategy = new NoInitializeStrategy();

    /**
     * 직진 1칸 + 대각선 1칸만 이동 가능
     */
    @ParameterizedTest
    @MethodSource("invalidDirectionsPositions")
    void 말의_이동범위가_직진1칸_대각선_1칸이_아닌_경우_움직일수_없다(Position to) {
        // given
        Board board = new StubBoard(strategy);
        Piece horse = new Horse(Team.CHO);

        // when
        Position from = Position.from(10, 2);

        // then
        assertThat(horse.canMove(from, to, board)).isEqualTo(false);
    }

    static Stream<Position> invalidDirectionsPositions() {
        return Stream.of(
                Position.from(10, 3),
                Position.from(8, 2),
                Position.from(9, 3)
        );
    }

    /**
     * 도착지에 같은 팀이 존재하는 경우 이동 불가
     */
    @ParameterizedTest
    @MethodSource("blockedBySameTeamProvider")
    void 도착지에_같은_팀이_존재하는_경우_이동_불가능하다(Position to) {
        StubBoard board = new StubBoard(strategy);
        Piece horse = new Horse(Team.CHO);

        Position from = Position.from(10, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Horse(Team.CHO));
        testPiece.put(to, new Pawn(Team.CHO));
        board.putPieces(testPiece);

        // when & then
        assertThat(horse.canMove(from, to, board)).isEqualTo(false);
    }

    static Stream<Position> blockedBySameTeamProvider() {
        return Stream.of(
                Position.from(8, 3),
                Position.from(8, 1),
                Position.from(9, 4)
        );
    }

    /**
     * 도착지랑 출발지 사이에 말이 존재하지 않는 경우 이동 가능
     */
    @Test
    void 도착지랑_출발지_사이에_말이_존재하는_경우_이동_불가능하다() {
        StubBoard board = new StubBoard(strategy);
        Piece horse = new Horse(Team.CHO);

        Position from = Position.from(1, 1);
        Position pathPosition = Position.from(2, 1);
        Position to = Position.from(3, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Horse(Team.CHO));
        testPiece.put(pathPosition, new Pawn(Team.CHO));
        board.putPieces(testPiece);

        // when & then
        assertThat(horse.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 정상테스트 1. 도착지에 같은 팀이 존재하지 않고, 2. 도착지와 출발지 사이에 다른 기물이 존재하지 않는다면 이동 가능
     */
    @ParameterizedTest
    @MethodSource("validDirectionsProvider")
    void 도착지에_같은_팀이_존재하지_않고_경로에_다른_기물이_존재하지_않을_경우(Position to) {
        // given
        StubBoard board = new StubBoard(strategy);
        Piece horse = new Horse(Team.CHO);

        // when
        Position from = Position.from(10, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Horse(Team.CHO));
        board.putPieces(testPiece);

        // then
        assertThat(horse.canMove(from, to, board)).isEqualTo(true);
    }

    static Stream<Position> validDirectionsProvider() {
        return Stream.of(
                Position.from(8, 3),
                Position.from(8, 1),
                Position.from(9, 4)
        );
    }
}
