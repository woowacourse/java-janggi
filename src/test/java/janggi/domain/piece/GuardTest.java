package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.BoardMediatorImpl;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GuardTest {

    @Nested
    @DisplayName("이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        Piece guard;

        @BeforeEach
        void setUp() {
            guard = new Guard(TeamType.RED);
        }

        @Test
        @DisplayName("궁성 내에서 자유롭게 이동이 가능하다.")
        void success_1() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(2, 5), guard,
                Position.valueOf(1, 4), new General(TeamType.RED),
                Position.valueOf(1, 6), new Guard(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(Position.valueOf(1, 5), Position.valueOf(2, 4),
                Position.valueOf(2, 6), Position.valueOf(3, 4), Position.valueOf(3, 5),
                Position.valueOf(3, 6));

            List<Position> actual = guard.calculateMovablePositions(Position.valueOf(2, 5),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("궁성 밖으로 나갈 수 없다.")
        void success_2() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(2, 4), guard,
                Position.valueOf(1, 4), new General(TeamType.RED),
                Position.valueOf(1, 6), new Guard(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(Position.valueOf(2, 5), Position.valueOf(3, 4));

            List<Position> actual = guard.calculateMovablePositions(Position.valueOf(2, 4),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("적 기물을 잡을 수 있다.")
        void success_4() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(2, 4), guard,
                Position.valueOf(1, 4), new General(TeamType.RED),
                Position.valueOf(1, 6), new Guard(TeamType.RED),
                Position.valueOf(3, 4), new Cannon(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(Position.valueOf(2, 5), Position.valueOf(3, 4));

            List<Position> actual = guard.calculateMovablePositions(Position.valueOf(2, 4),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("장기판 밖으로 이동할 수 없다.")
        void success_5() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(1, 5), guard,
                Position.valueOf(1, 4), new General(TeamType.RED),
                Position.valueOf(1, 6), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(Position.valueOf(2, 5));

            List<Position> actual = guard.calculateMovablePositions(Position.valueOf(1, 5),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }
    }
}
