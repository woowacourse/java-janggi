package janggi.domain.movement;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
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

        private static Position from;
        private static Piece me;
        private static Map<Position, Piece> positionPieceMap;

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
            // given
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            Direction direction = Direction.RIGHT;
            Movement Movement = new Movement(direction);
            boolean expected = true;

            // when
            boolean actual = Movement.hasReachablePosition(from, TeamType.RED, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("대상이 아군인 경우")
        void success_2() {
            // given
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            Direction direction = Direction.RIGHT;
            Movement Movement = new Movement(direction);
            boolean expected = false;

            // when
            boolean actual = Movement.hasReachablePosition(from, TeamType.RED, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("대상이 없는 경우")
        void success_3() {
            // given
            Board board = new Board(positionPieceMap);
            Direction direction = Direction.RIGHT;
            Movement Movement = new Movement(direction);
            boolean expected = true;

            // when
            boolean actual = Movement.hasReachablePosition(from, TeamType.RED, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("장애물 여부 판정 테스트")
    class IsBlocked {

        private static Position from;
        private static Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            from = Position.valueOf(5, 3);
            Piece me = new Soldier(TeamType.RED);
            positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(from, me);
        }

        @Test
        @DisplayName("기물이 없는 경우")
        void success_1() {
            // given
            Board board = new Board(positionPieceMap);
            Direction direction = Direction.RIGHT;
            Movement Movement = new Movement(direction);
            boolean expected = false;

            // when
            boolean actual = Movement.isBlocked(from, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("기물이 있는 경우")
        void success_2() {
            // given
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            Direction direction = Direction.RIGHT;
            Movement Movement = new Movement(direction);
            boolean expected = true;

            // when
            boolean actual = Movement.isBlocked(from, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("목적지 계산 테스트")
    class CalculateDestination {

        private static Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(Position.valueOf(5, 3), new Chariot(TeamType.RED));
        }

        @Test
        @DisplayName("경로에 아군이 있는 경우")
        void success_1() {
            // given
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(5, 3);
            int maxDistance = 1;
            Direction direction = Direction.RIGHT;
            Movement Movement = new Movement(direction);
            Position expected = Position.valueOf(5, 3);

            // when
            Position actual = Movement.calculateDestination(from, TeamType.RED, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("경로에 적군이 있는 경우")
        void success_2() {
            // given
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(5, 3);
            int maxDistance = 1;
            Direction direction = Direction.RIGHT;
            Movement Movement = new Movement(direction);
            Position expected = Position.valueOf(5, 4);

            // when
            Position actual = Movement.calculateDestination(from, TeamType.RED, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("경로에 기물이 없는 경우")
        void success_3() {
            // given
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(5, 3);
            int maxDistance = 1;
            Direction direction = Direction.RIGHT;
            Movement Movement = new Movement(direction);
            Position expected = Position.valueOf(5, 4);

            // when
            Position actual = Movement.calculateDestination(from, TeamType.RED, board);

            // then
            assertThat(actual).isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("한 칸 이동 경로 자취 계산 테스트")
    class CalculateTraces {

        private static Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(Position.valueOf(5, 3), new Chariot(TeamType.RED));
        }

        @Test
        @DisplayName("경로에 아군이 있는 경우")
        void success_1() {
            // given
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(5, 3);
            Direction direction = Direction.RIGHT;
            Movement Movement = new Movement(direction);
            List<Position> expected = List.of();

            // when
            List<Position> actual = Movement.calculateTraces(from, TeamType.RED, board, 1);

            // then
            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("경로에 적군이 있는 경우")
        void success_2() {
            // given
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(5, 3);
            Direction direction = Direction.RIGHT;
            Movement Movement = new Movement(direction);
            List<Position> expected = List.of(Position.valueOf(5, 4));

            // when
            List<Position> actual = Movement.calculateTraces(from, TeamType.RED, board, 1);

            // then
            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("경로에 기물이 없는 경우")
        void success_3() {
            // given
            Board board = new Board(positionPieceMap);
            Position from = Position.valueOf(5, 3);
            Direction direction = Direction.RIGHT;
            Movement Movement = new Movement(direction);
            List<Position> expected = List.of(Position.valueOf(5, 4));

            // when
            List<Position> actual = Movement.calculateTraces(from, TeamType.RED, board, 1);

            // then
            assertThat(actual).hasSameElementsAs(expected);
        }
    }
}