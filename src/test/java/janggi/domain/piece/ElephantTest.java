package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardMediator;
import janggi.domain.team.TeamType;
import java.util.LinkedHashMap;
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

        private Piece elephant;
        private Piece enemy1;
        private Piece enemy2;
        private Piece ally1;
        private Piece ally2;
        private Piece ally3;
        private Piece ally4;
        private Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            elephant = new Elephant(TeamType.RED);
            enemy1 = new Soldier(TeamType.BLUE);
            enemy2 = new Soldier(TeamType.BLUE);
            ally1 = new Soldier(TeamType.RED);
            ally2 = new Soldier(TeamType.RED);
            ally3 = new Soldier(TeamType.RED);
            ally4 = new Soldier(TeamType.RED);
            positionPieceMap = new LinkedHashMap<>();
        }

        @Test
        @DisplayName("상은 기물을 뛰어넘을 수 없다.")
        void test1() {
            positionPieceMap.put(Position.valueOf(6, 4), elephant);
            positionPieceMap.put(Position.valueOf(4, 1), ally1);
            positionPieceMap.put(Position.valueOf(5, 4), enemy1);
            positionPieceMap.put(Position.valueOf(7, 4), ally2);
            positionPieceMap.put(Position.valueOf(5, 6), ally3);
            positionPieceMap.put(Position.valueOf(8, 1), ally4);
            positionPieceMap.put(Position.valueOf(7, 6), enemy2);
            List<Position> expected = List.of();

            Board board = new Board(positionPieceMap);
            List<Position> actual = elephant.calculateMovablePositions(Position.valueOf(6, 4), board);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("상은 직선 한 칸, 대각선 두 칸을 가서 기물을 잡을 수 있다.")
        void test2() {
            positionPieceMap.put(Position.valueOf(6, 4), elephant);
            positionPieceMap.put(Position.valueOf(3, 2), enemy1);
            positionPieceMap.put(Position.valueOf(4, 7), enemy2);
            List<Position> expected = List.of(
                    Position.valueOf(3, 2),
                    Position.valueOf(3, 6),
                    Position.valueOf(4, 1),
                    Position.valueOf(4, 7),
                    Position.valueOf(8, 1),
                    Position.valueOf(8, 7),
                    Position.valueOf(9, 2),
                    Position.valueOf(9, 6));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = elephant.calculateMovablePositions(Position.valueOf(6, 4), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("상은 장기판 밖으로 이동할 수 없다.")
        void test3() {
            positionPieceMap.put(Position.valueOf(5, 2), elephant);
            List<Position> expected = List.of(
                    Position.valueOf(3, 5),
                    Position.valueOf(2, 4),
                    Position.valueOf(7, 5),
                    Position.valueOf(8, 4));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = elephant.calculateMovablePositions(Position.valueOf(5, 2), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("상은 장기판 밖으로 이동할 수 없다.")
        void test4() {
            positionPieceMap.put(Position.valueOf(1, 1), elephant);
            positionPieceMap.put(Position.valueOf(1, 2), ally1);
            positionPieceMap.put(Position.valueOf(2, 1), ally3);
            List<Position> expected = List.of();

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = elephant.calculateMovablePositions(Position.valueOf(1, 1), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }
    }
}
