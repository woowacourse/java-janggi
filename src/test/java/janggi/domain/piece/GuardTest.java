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
        @DisplayName("기물을 뛰어넘을 수 없다.")
        void success_1() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(6, 4), guard,
                Position.valueOf(5, 3), new Soldier(TeamType.RED),
                Position.valueOf(5, 4), new Soldier(TeamType.RED),
                Position.valueOf(5, 5), new Soldier(TeamType.RED),
                Position.valueOf(6, 3), new Soldier(TeamType.RED),
                Position.valueOf(6, 5), new Soldier(TeamType.BLUE),
                Position.valueOf(7, 3), new Soldier(TeamType.BLUE),
                Position.valueOf(7, 4), new Soldier(TeamType.BLUE),
                Position.valueOf(7, 5), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(Position.valueOf(6, 5), Position.valueOf(7, 3),
                Position.valueOf(7, 4), Position.valueOf(7, 5));

            List<Position> actual = guard.calculateMovablePositions(Position.valueOf(6, 4),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("모든 방향 중 한 칸을 가서 기물을 잡을 수 있다.")
        void success_2() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(6, 4), guard,
                Position.valueOf(6, 5), new Soldier(TeamType.BLUE),
                Position.valueOf(7, 3), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(Position.valueOf(5, 3), Position.valueOf(5, 4),
                Position.valueOf(5, 5), Position.valueOf(6, 3), Position.valueOf(6, 5),
                Position.valueOf(7, 3),
                Position.valueOf(7, 4), Position.valueOf(7, 5));

            List<Position> actual = guard.calculateMovablePositions(Position.valueOf(6, 4),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("장기판 밖으로 이동할 수 없다.")
        void success_3() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(1, 1), guard,
                Position.valueOf(1, 2), new Soldier(TeamType.RED),
                Position.valueOf(2, 1), new Soldier(TeamType.RED),
                Position.valueOf(2, 2), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of();

            List<Position> actual = guard.calculateMovablePositions(Position.valueOf(1, 1),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }
    }
}
