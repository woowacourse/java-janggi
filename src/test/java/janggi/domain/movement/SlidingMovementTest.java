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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SlidingMovementTest {

    @Nested
    @DisplayName("잡기 여부 판정 테스트")
    class CanCatch {

        @Test
        @DisplayName("대상이 적군인 경우")
        void success_1() {
            Position from = Position.valueOf(5, 3);
            Piece me = new Soldier(TeamType.RED);
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(from, me);
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            Direction direction = Direction.EAST;
            SlidingMovement slidingMovement = new SlidingMovement(1, direction);
            boolean expected = true;

            boolean actual = slidingMovement.canCatchAnyOnPath(me, from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("대상이 아군인 경우")
        void success_2() {
            Position from = Position.valueOf(5, 3);
            Piece me = new Soldier(TeamType.RED);
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(from, me);
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            Direction direction = Direction.EAST;
            SlidingMovement slidingMovement = new SlidingMovement(1, direction);
            boolean expected = false;

            boolean actual = slidingMovement.canCatchAnyOnPath(me, from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("대상이 없는 경우")
        void success_3() {
            Position from = Position.valueOf(5, 3);
            Piece me = new Soldier(TeamType.RED);
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(from, me);
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            Direction direction = Direction.EAST;
            SlidingMovement slidingMovement = new SlidingMovement(1, direction);
            boolean expected = true;

            boolean actual = slidingMovement.canCatchAnyOnPath(me, from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("유효하지 않은 위치에 아군이 있는 경우")
        void success_4() {
            Position from = Position.valueOf(9, 5);
            Piece me = new Soldier(TeamType.RED);
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(from, me);
            positionPieceMap.put(Position.valueOf(7, 7), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            SlidingMovement slidingMovement = new SlidingMovement(2, Direction.NORTH_EAST);
            boolean expected = true;

            boolean actual = slidingMovement.canCatchAnyOnPath(me, from, boardMediator);

            assertThat(actual).isEqualTo(expected);

        }
    }

    @Nested
    @DisplayName("장애물 여부 판정 테스트")
    class IsBlocked {

        @Test
        @DisplayName("기물이 없는 경우")
        void success_1() {
            Position from = Position.valueOf(5, 3);
            Piece me = new Soldier(TeamType.RED);
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(from, me);
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            Direction direction = Direction.EAST;
            SlidingMovement slidingMovement = new SlidingMovement(1, direction);
            boolean expected = false;

            boolean actual = slidingMovement.isBlocked(from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("기물이 있는 경우")
        void success_2() {
            Position from = Position.valueOf(5, 3);
            Piece me = new Soldier(TeamType.RED);
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(from, me);
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            Direction direction = Direction.EAST;
            SlidingMovement slidingMovement = new SlidingMovement(1, direction);
            boolean expected = true;

            boolean actual = slidingMovement.isBlocked(from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("유효하지 않은 위치에 기물이 있는 경우")
        void success_3() {
            Position from = Position.valueOf(9, 5);
            Piece me = new Soldier(TeamType.RED);
            Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            positionPieceMap.put(from, me);
            positionPieceMap.put(Position.valueOf(7, 7), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            SlidingMovement slidingMovement = new SlidingMovement(2, Direction.NORTH_EAST);
            boolean expected = false;

            boolean actual = slidingMovement.isBlocked(from, boardMediator);

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
            Direction direction = Direction.EAST;
            SlidingMovement slidingMovement = new SlidingMovement(maxDistance, direction);
            Optional<Position> expected = Optional.of(Position.valueOf(5, 5));

            Optional<Position> actual = slidingMovement.calculateDestination(from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("경로에 적군이 있는 경우")
        void success_2() {
            positionPieceMap.put(Position.valueOf(5, 6), new Soldier(TeamType.BLUE));
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(5, 3);
            int maxDistance = 4;
            Direction direction = Direction.EAST;
            SlidingMovement slidingMovement = new SlidingMovement(maxDistance, direction);
            Optional<Position> expected = Optional.of(Position.valueOf(5, 6));

            Optional<Position> actual = slidingMovement.calculateDestination(from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("경로에 기물이 없는 경우")
        void success_3() {
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(5, 3);
            int maxDistance = 4;
            Direction direction = Direction.EAST;
            SlidingMovement slidingMovement = new SlidingMovement(maxDistance, direction);
            Optional<Position> expected = Optional.of(Position.valueOf(5, 7));

            Optional<Position> actual = slidingMovement.calculateDestination(from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("경로에 유효하지 않은 위치가 존재하는 경우")
        void success_4() {
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(9, 5);
            int maxDistance = 4;
            Direction direction = Direction.NORTH_EAST;
            SlidingMovement slidingMovement = new SlidingMovement(maxDistance, direction);
            Optional<Position> expected = Optional.of(Position.valueOf(8, 6));

            Optional<Position> actual = slidingMovement.calculateDestination(from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("유효한 목적지가 존재하지 않는 경우")
        void success_5() {
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(8, 6);
            int maxDistance = 4;
            Direction direction = Direction.NORTH_EAST;
            SlidingMovement slidingMovement = new SlidingMovement(maxDistance, direction);
            Optional<Position> expected = Optional.empty();

            Optional<Position> actual = slidingMovement.calculateDestination(from, boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("경로 자취 계산 테스트")
    class CalculatePath {

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
            Direction direction = Direction.EAST;
            SlidingMovement slidingMovement = new SlidingMovement(maxDistance, direction);
            List<Position> expected = List.of(Position.valueOf(5, 4), Position.valueOf(5, 5));

            List<Position> actual = slidingMovement.calculatePath(from,
                boardMediator.getPieceByPosition(from), boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("경로에 적군이 있는 경우")
        void success_2() {
            positionPieceMap.put(Position.valueOf(5, 6), new Soldier(TeamType.BLUE));
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(5, 3);
            int maxDistance = 4;
            Direction direction = Direction.EAST;
            SlidingMovement slidingMovement = new SlidingMovement(maxDistance, direction);
            List<Position> expected = List.of(Position.valueOf(5, 4), Position.valueOf(5, 5),
                Position.valueOf(5, 6));

            List<Position> actual = slidingMovement.calculatePath(from,
                boardMediator.getPieceByPosition(from), boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("경로에 기물이 없는 경우")
        void success_3() {
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(5, 3);
            int maxDistance = 4;
            Direction direction = Direction.EAST;
            SlidingMovement SlidingMovement = new SlidingMovement(maxDistance, direction);
            List<Position> expected = List.of(Position.valueOf(5, 4), Position.valueOf(5, 5),
                Position.valueOf(5, 6), Position.valueOf(5, 7));

            List<Position> actual = SlidingMovement.calculatePath(from,
                boardMediator.getPieceByPosition(from), boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("경로에 유효하지 않은 위치가 있는 경우")
        void success_4() {
            positionPieceMap.put(Position.valueOf(9, 5), new Chariot(TeamType.RED));
            boardMediator = new BoardMediatorImpl(new Board(positionPieceMap));
            Position from = Position.valueOf(9, 5);
            int maxDistance = 4;
            Direction direction = Direction.NORTH_EAST;
            SlidingMovement slidingMovement = new SlidingMovement(maxDistance, direction);
            List<Position> expected = List.of(Position.valueOf(8, 6));

            List<Position> actual = slidingMovement.calculatePath(from,
                boardMediator.getPieceByPosition(from), boardMediator);

            assertThat(actual).isEqualTo(expected);
        }

    }

}