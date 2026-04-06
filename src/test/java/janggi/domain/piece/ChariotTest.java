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

public class ChariotTest {

    @Nested
    @DisplayName("이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        private Piece chariot;
        private Piece enemy1;
        private Piece enemy2;
        private Piece enemy3;
        private Piece enemy4;
        private Piece ally1;
        private Piece ally2;
        private Piece ally3;
        private Piece ally4;
        private Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            chariot = new Chariot(TeamType.RED);
            enemy1 = new Soldier(TeamType.BLUE);
            enemy3 = new Soldier(TeamType.BLUE);
            enemy4 = new Soldier(TeamType.BLUE);
            enemy2 = new Soldier(TeamType.BLUE);
            ally1 = new Soldier(TeamType.RED);
            ally2 = new Soldier(TeamType.RED);
            ally3 = new Soldier(TeamType.RED);
            ally4 = new Soldier(TeamType.RED);
            positionPieceMap = new LinkedHashMap<>();
        }

        @Test
        @DisplayName("차는 적군을 뛰어넘어 갈 수 없다.")
        void test1() {
            positionPieceMap.put(Position.valueOf(5, 3), chariot);
            positionPieceMap.put(Position.valueOf(5, 1), enemy1);
            positionPieceMap.put(Position.valueOf(3, 3), enemy2);
            positionPieceMap.put(Position.valueOf(5, 6), enemy3);
            positionPieceMap.put(Position.valueOf(7, 3), enemy4);
            List<Position> expected = List.of(
                    Position.valueOf(3, 3),
                    Position.valueOf(4, 3),
                    Position.valueOf(5, 1),
                    Position.valueOf(5, 2),
                    Position.valueOf(5, 4),
                    Position.valueOf(5, 5),
                    Position.valueOf(5, 6),
                    Position.valueOf(6, 3),
                    Position.valueOf(7, 3));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = chariot.calculateMovablePositions(Position.valueOf(5, 3), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("차는 아군을 뛰어넘어 갈 수 없다.")
        void test2() {
            positionPieceMap.put(Position.valueOf(5, 3), chariot);
            positionPieceMap.put(Position.valueOf(5, 1), ally1);
            positionPieceMap.put(Position.valueOf(3, 3), ally2);
            positionPieceMap.put(Position.valueOf(5, 6), ally3);
            positionPieceMap.put(Position.valueOf(7, 3), ally4);
            List<Position> expected = List.of(
                    Position.valueOf(4, 3),
                    Position.valueOf(5, 2),
                    Position.valueOf(5, 4),
                    Position.valueOf(5, 5),
                    Position.valueOf(6, 3));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = chariot.calculateMovablePositions(Position.valueOf(5, 3), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("차는 궁성영역에서 대각선으로 이동할 수 있다.")
        void test3() {
            positionPieceMap.put(Position.valueOf(9, 5), chariot);
            positionPieceMap.put(Position.valueOf(8, 5), ally1);
            positionPieceMap.put(Position.valueOf(8, 6), ally2);
            List<Position> expected = List.of(
                    Position.valueOf(8, 4),
                    Position.valueOf(9, 1),
                    Position.valueOf(9, 2),
                    Position.valueOf(9, 3),
                    Position.valueOf(9, 4),
                    Position.valueOf(9, 6),
                    Position.valueOf(9, 7),
                    Position.valueOf(9, 8),
                    Position.valueOf(9, 9),
                    Position.valueOf(10, 4),
                    Position.valueOf(10, 5),
                    Position.valueOf(10, 6));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = chariot.calculateMovablePositions(Position.valueOf(9, 5), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("차는 궁성영역에서 대각선으로 이동할 수 있다.")
        void test5() {
            positionPieceMap.put(Position.valueOf(8, 6), chariot);
            positionPieceMap.put(Position.valueOf(8, 4), enemy1);
            positionPieceMap.put(Position.valueOf(6, 6), ally1);
            List<Position> expected = List.of(
                    Position.valueOf(8, 4),
                    Position.valueOf(8, 5),
                    Position.valueOf(8, 7),
                    Position.valueOf(8, 8),
                    Position.valueOf(8, 9),
                    Position.valueOf(7, 6),
                    Position.valueOf(9, 6),
                    Position.valueOf(10, 6),
                    Position.valueOf(9, 5),
                    Position.valueOf(10, 4));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = chariot.calculateMovablePositions(Position.valueOf(8, 6), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }
    }
}
