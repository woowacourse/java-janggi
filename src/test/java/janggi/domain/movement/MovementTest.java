package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.BoardMediatorImpl;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MovementTest {

    @Nested
    @DisplayName("잡기 여부 판정 테스트")
    class CanKill {

        Position from;
        Piece me;
        Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            from = Position.valueOf(5, 3);
            me = new Soldier(TeamType.RED);
            positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(from, me);
        }

        @Test
        @DisplayName("대상이 적군인 경우")
        void success_1() {
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            Direction direction = Direction.valueOf(0, 1);
            Movement Movement = new Movement(1, direction);
            boolean expected = true;

            boolean actual = Movement.canKill(me, from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("대상이 아군인 경우")
        void success_2() {
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            Direction direction = Direction.valueOf(0, 1);
            Movement Movement = new Movement(1, direction);
            boolean expected = false;

            boolean actual = Movement.canKill(me, from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("대상이 없는 경우")
        void success_3() {
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            Direction direction = Direction.valueOf(0, 1);
            Movement Movement = new Movement(1, direction);
            boolean expected = true;

            boolean actual = Movement.canKill(me, from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("장애물 여부 판정 테스트")
    class IsBlocked {

        Position from;
        Piece me;
        Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            from = Position.valueOf(5, 3);
            me = new Soldier(TeamType.RED);
            positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(from, me);
        }

        @Test
        @DisplayName("기물이 없는 경우")
        void success_1() {
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            Direction direction = Direction.valueOf(0, 1);
            Movement Movement = new Movement(1, direction);
            boolean expected = false;

            boolean actual = Movement.isBlocked(from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("기물이 있는 경우")
        void success_2() {
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            Direction direction = Direction.valueOf(0, 1);
            Movement Movement = new Movement(1, direction);
            boolean expected = true;

            boolean actual = Movement.isBlocked(from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("목적지 계산 테스트")
    class CalculateDestination {

        Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
        BoardMediator boardMediator;

        @BeforeEach
        void setUp() {
            positionPieceMap.put(Position.valueOf(5, 3), new Chariot(TeamType.RED));
        }

        @Test
        @DisplayName("경로에 아군이 있는 경우")
        void success_1() {
            positionPieceMap.put(Position.valueOf(5, 6), new Soldier(TeamType.RED));
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(5, 3);
            int maxDistance = 4;
            Direction direction = Direction.valueOf(0, 1);
            Movement Movement = new Movement(maxDistance, direction);
            Position expected = Position.valueOf(5, 5);

            Position actual = Movement.calculateDestination(from,
                boardMediator.getPieceInPosition(from),
                boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("경로에 적군이 있는 경우")
        void success_2() {
            positionPieceMap.put(Position.valueOf(5, 6), new Soldier(TeamType.BLUE));
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(5, 3);
            int maxDistance = 4;
            Direction direction = Direction.valueOf(0, 1);
            Movement Movement = new Movement(maxDistance, direction);
            Position expected = Position.valueOf(5, 6);

            Position actual = Movement.calculateDestination(from,
                boardMediator.getPieceInPosition(from),
                boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("경로에 기물이 없는 경우")
        void success_3() {
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(5, 3);
            int maxDistance = 4;
            Direction direction = Direction.valueOf(0, 1);
            Movement Movement = new Movement(maxDistance, direction);
            Position expected = Position.valueOf(5, 7);

            Position actual = Movement.calculateDestination(from,
                boardMediator.getPieceInPosition(from),
                boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("경로 자취 계산 테스트")
    class CalculateTraces {

        Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
        BoardMediator boardMediator;

        @BeforeEach
        void setUp() {
            positionPieceMap.put(Position.valueOf(5, 3), new Chariot(TeamType.RED));
        }

        @Test
        @DisplayName("경로에 아군이 있는 경우")
        void success_1() {
            positionPieceMap.put(Position.valueOf(5, 6), new Soldier(TeamType.RED));
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(5, 3);
            int maxDistance = 4;
            Direction direction = Direction.valueOf(0, 1);
            Movement Movement = new Movement(maxDistance, direction);
            List<Position> expected = List.of(Position.valueOf(5, 4), Position.valueOf(5, 5));

            List<Position> actual = Movement.calculateTraces(from,
                boardMediator.getPieceInPosition(from),
                boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("경로에 적군이 있는 경우")
        void success_2() {
            positionPieceMap.put(Position.valueOf(5, 6), new Soldier(TeamType.BLUE));
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(5, 3);
            int maxDistance = 4;
            Direction direction = Direction.valueOf(0, 1);
            Movement Movement = new Movement(maxDistance, direction);
            List<Position> expected = List.of(Position.valueOf(5, 4), Position.valueOf(5, 5),
                Position.valueOf(5, 6));

            List<Position> actual = Movement.calculateTraces(from,
                boardMediator.getPieceInPosition(from),
                boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("경로에 기물이 없는 경우")
        void success_3() {
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(5, 3);
            int maxDistance = 4;
            Direction direction = Direction.valueOf(0, 1);
            Movement Movement = new Movement(maxDistance, direction);
            List<Position> expected = List.of(Position.valueOf(5, 4), Position.valueOf(5, 5),
                Position.valueOf(5, 6), Position.valueOf(5, 7));

            List<Position> actual = Movement.calculateTraces(from,
                boardMediator.getPieceInPosition(from),
                boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

    }

}