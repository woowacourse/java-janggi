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

public class ChariotTest {

    @Nested
    @DisplayName("이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        Piece chariot;

        @BeforeEach
        void setUp() {
            chariot = new Chariot(TeamType.RED);
        }

        @Test
        @DisplayName("적군을 뛰어넘어 갈 수 없다.")
        void success_1() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(5, 3), chariot,
                Position.valueOf(5, 1), new Soldier(TeamType.BLUE),
                Position.valueOf(3, 3), new Soldier(TeamType.BLUE),
                Position.valueOf(5, 6), new Soldier(TeamType.BLUE),
                Position.valueOf(7, 3), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(Position.valueOf(3, 3), Position.valueOf(4, 3),
                Position.valueOf(5, 1),
                Position.valueOf(5, 2), Position.valueOf(5, 4),
                Position.valueOf(5, 5), Position.valueOf(5, 6),
                Position.valueOf(6, 3), Position.valueOf(7, 3));

            List<Position> actual = chariot.calculateMovablePositions(Position.valueOf(5, 3),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("아군을 뛰어넘어 갈 수 없다.")
        void success_2() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(5, 3), chariot,
                Position.valueOf(5, 1), new Soldier(TeamType.RED),
                Position.valueOf(3, 3), new Soldier(TeamType.RED),
                Position.valueOf(5, 6), new Soldier(TeamType.RED),
                Position.valueOf(7, 3), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(Position.valueOf(4, 3),
                Position.valueOf(5, 2), Position.valueOf(5, 4),
                Position.valueOf(5, 5), Position.valueOf(6, 3));

            List<Position> actual = chariot.calculateMovablePositions(Position.valueOf(5, 3),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("궁성의 간선을 타고 갈 수 있다.")
        void success_3() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(10, 4), chariot,
                Position.valueOf(10, 3), new Cannon(TeamType.RED),
                Position.valueOf(8, 4), new Guard(TeamType.RED),
                Position.valueOf(8, 6), new Guard(TeamType.RED),
                Position.valueOf(10, 6), new General(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(Position.valueOf(9, 4), Position.valueOf(9, 5),
                Position.valueOf(10, 5));

            List<Position> actual = chariot.calculateMovablePositions(Position.valueOf(10, 4),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("상하좌우 방향으로 이동할 수 있다.")
        void success_4() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(5, 3), chariot);
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(
                Position.valueOf(1, 3),
                Position.valueOf(2, 3),
                Position.valueOf(3, 3),
                Position.valueOf(4, 3),
                Position.valueOf(5, 1),
                Position.valueOf(5, 2),
                Position.valueOf(5, 4),
                Position.valueOf(5, 5),
                Position.valueOf(5, 6),
                Position.valueOf(5, 7),
                Position.valueOf(5, 8),
                Position.valueOf(5, 9),
                Position.valueOf(6, 3),
                Position.valueOf(7, 3),
                Position.valueOf(8, 3),
                Position.valueOf(9, 3),
                Position.valueOf(10, 3));

            List<Position> actual = chariot.calculateMovablePositions(Position.valueOf(5, 3),
                boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }
    }
}
