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

public class ElephantTest {

    @Nested
    @DisplayName("이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        Piece elephant;

        @BeforeEach
        void setUp() {
            elephant = new Elephant(TeamType.RED);
        }

        @Test
        @DisplayName("적군 기물을 잡을 수 있다.")
        void success_1() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(6, 4), elephant,
                Position.valueOf(3, 2), new Soldier(TeamType.BLUE),
                Position.valueOf(4, 7), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(Position.valueOf(3, 2), Position.valueOf(3, 6),
                Position.valueOf(4, 1),
                Position.valueOf(4, 7), Position.valueOf(8, 1), Position.valueOf(8, 7),
                Position.valueOf(9, 2),
                Position.valueOf(9, 6));

            List<Position> actual = elephant.calculateMovablePositions(Position.valueOf(6, 4),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("기물을 뛰어넘을 수 없다.")
        void success_2() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(6, 4), elephant,
                Position.valueOf(4, 1), new Soldier(TeamType.RED),
                Position.valueOf(5, 4), new Soldier(TeamType.BLUE),
                Position.valueOf(7, 4), new Soldier(TeamType.RED),
                Position.valueOf(5, 6), new Soldier(TeamType.RED),
                Position.valueOf(8, 1), new Soldier(TeamType.RED),
                Position.valueOf(7, 6), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of();

            List<Position> actual = elephant.calculateMovablePositions(Position.valueOf(6, 4),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("장기판 밖으로 이동할 수 없다.")
        void success_3() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(1, 1), elephant,
                Position.valueOf(1, 2), new Soldier(TeamType.RED),
                Position.valueOf(2, 1), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of();

            List<Position> actual = elephant.calculateMovablePositions(Position.valueOf(1, 1),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("직선 한 칸, 대각선 두 칸을 이동할 수 있다.")
        void success_4() {
            Map<Position, Piece> positionPieceMap =
                Map.of(Position.valueOf(6, 5), elephant);
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(
                Position.valueOf(3, 3),
                Position.valueOf(3, 7),
                Position.valueOf(4, 2),
                Position.valueOf(4, 8),
                Position.valueOf(8, 2),
                Position.valueOf(8, 8),
                Position.valueOf(9, 3),
                Position.valueOf(9, 7));

            List<Position> actual = elephant.calculateMovablePositions(Position.valueOf(6, 5),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }
    }
}
