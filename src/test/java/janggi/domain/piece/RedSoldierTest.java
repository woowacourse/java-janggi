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

public class RedSoldierTest {

    @Nested
    @DisplayName("이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        Piece redSoldier;

        @BeforeEach
        void setUp() {
            redSoldier = new Soldier(TeamType.RED);
        }

        @Test
        @DisplayName("적군 기물을 잡을 수 있다.")
        void success_1() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(6, 4), redSoldier,
                Position.valueOf(7, 4), new Soldier(TeamType.BLUE),
                Position.valueOf(6, 3), new Soldier(TeamType.BLUE));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(
                Position.valueOf(7, 4),
                Position.valueOf(6, 3),
                Position.valueOf(6, 5)
            );

            List<Position> actual = redSoldier.calculateMovablePositions(
                Position.valueOf(6, 4), boardMediator
            );

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("아군이 있는 위치로 이동할 수 없다.")
        void success_2() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(6, 4), redSoldier,
                Position.valueOf(7, 4), new Soldier(TeamType.RED),
                Position.valueOf(6, 5), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(
                Position.valueOf(6, 3)
            );

            List<Position> actual = redSoldier.calculateMovablePositions(
                Position.valueOf(6, 4), boardMediator
            );

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("장기판 밖으로 이동할 수 없다.")
        void success_3() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(10, 1), redSoldier,
                Position.valueOf(10, 2), new Soldier(TeamType.RED));
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of();

            List<Position> actual = redSoldier.calculateMovablePositions(
                Position.valueOf(10, 1), boardMediator
            );

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("궁성의 간선을 타고 이동할 수 있다.")
        void success_4() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(8, 6), redSoldier);
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(Position.valueOf(8, 5), Position.valueOf(8, 7),
                Position.valueOf(9, 5), Position.valueOf(9, 6));

            List<Position> actual = redSoldier.calculateMovablePositions(
                Position.valueOf(8, 6), boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        @DisplayName("남, 서, 동쪽으로 1칸 이동할 수 있다.")
        void success_5() {
            Map<Position, Piece> positionPieceMap = Map.of(
                Position.valueOf(6, 5), redSoldier);
            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> expected = List.of(
                Position.valueOf(6, 4),
                Position.valueOf(6, 6),
                Position.valueOf(7, 5)
            );

            List<Position> actual = redSoldier.calculateMovablePositions(
                Position.valueOf(6, 5), boardMediator);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }
    }
}