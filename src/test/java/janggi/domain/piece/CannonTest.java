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

class CannonTest {

    @Nested
    @DisplayName("이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        Piece cannon;

        @BeforeEach
        void setUp() {
            cannon = new Cannon(TeamType.RED);
        }

        @Test
        @DisplayName("기물을 뛰어 넘어서 이동할 수 있다.")
        void success_1() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(6, 7), cannon,
                Position.valueOf(6, 5), new Soldier(TeamType.BLUE),
                Position.valueOf(7, 7), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(
                Position.valueOf(6, 1),
                Position.valueOf(6, 2),
                Position.valueOf(6, 3),
                Position.valueOf(6, 4),
                Position.valueOf(8, 7),
                Position.valueOf(9, 7),
                Position.valueOf(10, 7));

            List<Position> actual = cannon.calculateMovablePositions(Position.valueOf(6, 7),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("포를 넘을 수 없다.")
        void success_2() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(6, 7), cannon,
                Position.valueOf(4, 7), new Soldier(TeamType.BLUE),
                Position.valueOf(6, 5), new Cannon(TeamType.BLUE),
                Position.valueOf(9, 7), new Cannon(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(
                Position.valueOf(1, 7),
                Position.valueOf(2, 7),
                Position.valueOf(3, 7));

            List<Position> actual = cannon.calculateMovablePositions(Position.valueOf(6, 7),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("포를 잡을 수 없다.")
        void success_3() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(6, 7), cannon,
                Position.valueOf(6, 3), new Cannon(TeamType.BLUE),
                Position.valueOf(6, 4), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of();

            List<Position> actual = cannon.calculateMovablePositions(Position.valueOf(6, 7),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }
    }
}
