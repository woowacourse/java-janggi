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

public class GuardTest {

    @Nested
    @DisplayName("이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        private Piece guard;
        private Piece enemy1;
        private Piece enemy2;
        private Piece enemy3;
        private Piece enemy4;
        private Piece ally1;
        private Piece ally2;
        private Piece ally3;
        private Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            guard = new Guard(TeamType.RED);
            enemy1 = new Soldier(TeamType.BLUE);
            enemy3 = new Soldier(TeamType.BLUE);
            enemy4 = new Soldier(TeamType.BLUE);
            enemy2 = new Soldier(TeamType.BLUE);
            ally1 = new Soldier(TeamType.RED);
            ally2 = new Soldier(TeamType.RED);
            ally3 = new Soldier(TeamType.RED);
            positionPieceMap = new LinkedHashMap<>();
        }

        @Test
        @DisplayName("사는 기물을 뛰어넘을 수 없다.")
        void test1() {
            positionPieceMap.put(Position.valueOf(1, 4), guard);
            positionPieceMap.put(Position.valueOf(1, 5), ally1);
            positionPieceMap.put(Position.valueOf(2, 4), ally2);
            List<Position> expected = List.of(Position.valueOf(2, 5));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = guard.calculateMovablePositions(Position.valueOf(1, 4), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("사는 모든 방향 중 한 칸을 가서 기물을 잡을 수 있다.")
        void test2() {
            positionPieceMap.put(Position.valueOf(2, 5), guard);
            positionPieceMap.put(Position.valueOf(1, 4), enemy1);
            positionPieceMap.put(Position.valueOf(2, 4), enemy2);
            positionPieceMap.put(Position.valueOf(3, 5), enemy3);
            positionPieceMap.put(Position.valueOf(3, 6), enemy4);
            List<Position> expected = List.of(
                    Position.valueOf(1, 4),
                    Position.valueOf(1, 5),
                    Position.valueOf(1, 6),
                    Position.valueOf(2, 4),
                    Position.valueOf(2, 6),
                    Position.valueOf(3, 4),
                    Position.valueOf(3, 5),
                    Position.valueOf(3, 6));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = guard.calculateMovablePositions(Position.valueOf(2, 5), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("사는 모든 방향 중 한 칸을 가서 기물을 잡을 수 있다.")
        void test3() {
            positionPieceMap.put(Position.valueOf(3, 5), guard);
            positionPieceMap.put(Position.valueOf(3, 4), enemy1);
            positionPieceMap.put(Position.valueOf(2, 4), enemy2);
            positionPieceMap.put(Position.valueOf(2, 5), enemy3);
            positionPieceMap.put(Position.valueOf(2, 6), enemy4);
            List<Position> expected = List.of(
                    Position.valueOf(2, 5),
                    Position.valueOf(3, 4),
                    Position.valueOf(3, 6));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = guard.calculateMovablePositions(Position.valueOf(3, 5), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("사는 장기판 밖으로 이동할 수 없다.")
        void test4() {
            positionPieceMap.put(Position.valueOf(1, 5), guard);
            positionPieceMap.put(Position.valueOf(1, 4), ally1);
            positionPieceMap.put(Position.valueOf(2, 5), ally2);
            positionPieceMap.put(Position.valueOf(1, 6), ally3);
            List<Position> expected = List.of();

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = guard.calculateMovablePositions(Position.valueOf(1, 5), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("사는 궁성영역 안에서만 이동할 수 있다.")
        void test5() {
            positionPieceMap.put(Position.valueOf(2, 5), guard);
            List<Position> expected = List.of(
                    Position.valueOf(1, 4),
                    Position.valueOf(1, 5),
                    Position.valueOf(1, 6),
                    Position.valueOf(2, 4),
                    Position.valueOf(2, 6),
                    Position.valueOf(3, 4),
                    Position.valueOf(3, 5),
                    Position.valueOf(3, 6));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = guard.calculateMovablePositions(Position.valueOf(2, 5), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }
    }
}
