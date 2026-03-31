package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Board;
import domain.Position;
import domain.Team;
import domain.strategy.NoInitializeStrategy;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import strategy.CustomInitializeStrategy;
import strategy.InitializeStrategy;

class RookTest {
    private final InitializeStrategy strategy = new NoInitializeStrategy();

    /**
     * 1. 도착 지점이 같은 열과 행이 아닌 경우 이동 불가
     * 2. 도착지에 같은 팀이 존재하는 경우 이동 불가
     * 3. 도착지랑 출발지 사이에 말이 하나라도 존재하면 이동 불가
     */

    /**
     * 1. 도착 지점이 같은 열이 아닌 경우 이동 불가
     */
    @Test
    void 도착_지점이_같은_열_혹은_행이_아닌_경우_이동_불가능하다() {
        // given
        Board board = new Board(strategy, strategy);
        Piece rook = new Rook(Team.CHO);

        // when
        Position from = Position.from(10, 1);
        Position to = Position.from(9, 2);

        // then
        assertThat(rook.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 2. 도착지에 같은 팀이 존재하는 경우 이동 불가
     */
    @Test
    void 도착지에_같은_팀이_존재하는_경우_이동_불가능하다() {
        // given
        Piece rook = new Rook(Team.CHO);

        // when
        Position from = Position.from(10, 1);
        Position to = Position.from(10, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Rook(Team.CHO));
        testPiece.put(to, new Pawn(Team.CHO));

        InitializeStrategy customStrategy = new CustomInitializeStrategy(testPiece);
        Board board = new Board(customStrategy, strategy);

        // then
        assertThat(rook.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 3. 도착지랑 출발지 사이에 말이 하나라도 존재하면 이동 불가
     */
    @Test
    void 도착지랑_출발지_사이에_말이_하나라도_존재하면_이동_불가능하다() {
        // given
        Piece rook = new Rook(Team.CHO);

        // when
        Position from = Position.from(10, 1);
        Position to = Position.from(10, 3);
        Position between = Position.from(10, 2);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Rook(Team.CHO));
        testPiece.put(between, new Pawn(Team.CHO));

        InitializeStrategy customStrategy = new CustomInitializeStrategy(testPiece);
        Board board = new Board(customStrategy, strategy);

        // then
        assertThat(rook.canMove(from, to, board)).isEqualTo(false);
    }

    /**
     * 정상 테스트
     */
    @ParameterizedTest
    @MethodSource("validDirectionsPositions")
    void 출발지와_도착지_사이에_기물이_없고_직선_움직임인_경우_차는_정상_이동한다(Position to) {
        // given
        Piece rook = new Rook(Team.CHO);

        // when
        Position from = Position.from(10, 1);

        Map<Position, Piece> testPiece = new HashMap<>();
        testPiece.put(from, new Rook(Team.CHO));

        InitializeStrategy customStrategy = new CustomInitializeStrategy(testPiece);
        Board board = new Board(customStrategy, strategy);

        // then
        assertThat(rook.canMove(from, to, board)).isEqualTo(true);
    }

    static Stream<Position> validDirectionsPositions() {
        return Stream.of(
                Position.from(10, 3),
                Position.from(8, 1)
        );
    }
}
