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

public class RedSoldierTest {

    @Nested
    @DisplayName("Red Soldier 이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        private Piece enemy1;
        private Piece enemy2;
        private Piece ally1;
        private Piece ally2;
        private Map<Position, Piece> positionPieceMap;
        private Piece redSoldier;

        @BeforeEach
        void setUp() {
            redSoldier = new Soldier(TeamType.RED);
            enemy1 = new Soldier(TeamType.BLUE);
            enemy2 = new Soldier(TeamType.BLUE);
            ally1 = new Soldier(TeamType.RED);
            ally2 = new Soldier(TeamType.RED);
            positionPieceMap = new LinkedHashMap<>();
        }

        @Test
        @DisplayName("홍졸은 앞, 좌, 우로 이동할 수 있고 적이 있으면 잡을 수 있다.")
        void test1() {
            positionPieceMap.put(Position.valueOf(6, 4), redSoldier);
            positionPieceMap.put(Position.valueOf(7, 4), enemy1);
            positionPieceMap.put(Position.valueOf(6, 3), enemy2);
            List<Position> expected = List.of(
                    Position.valueOf(7, 4),
                    Position.valueOf(6, 3),
                    Position.valueOf(6, 5)
            );

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = redSoldier.calculateMovablePositions(Position.valueOf(6, 4), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("홍졸은 아군이 있는 위치로 이동할 수 없다.")
        void test2() {
            positionPieceMap.put(Position.valueOf(6, 4), redSoldier);
            positionPieceMap.put(Position.valueOf(7, 4), ally1);
            positionPieceMap.put(Position.valueOf(6, 5), ally2);
            List<Position> expected = List.of(Position.valueOf(6, 3));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = redSoldier.calculateMovablePositions(Position.valueOf(6, 4), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("홍졸은 장기판 밖으로 이동할 수 없다.")
        void test3() {
            positionPieceMap.put(Position.valueOf(10, 1), redSoldier);
            positionPieceMap.put(Position.valueOf(10, 2), ally1);
            List<Position> expected = List.of();

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = redSoldier.calculateMovablePositions(Position.valueOf(10, 1), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("홍졸은 궁성 내부에서 대각선으로 이동할 수 있다.")
        void test4() {
            positionPieceMap.put(Position.valueOf(8, 4), redSoldier);
            List<Position> expected = List.of(
                    Position.valueOf(9, 4),
                    Position.valueOf(8, 3),
                    Position.valueOf(8, 5),
                    Position.valueOf(9, 5));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = redSoldier.calculateMovablePositions(Position.valueOf(8, 4), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("홍졸은 궁성 내부에서 대각선으로 이동할 수 있다.")
        void test5() {
            positionPieceMap.put(Position.valueOf(9, 5), redSoldier);
            List<Position> expected = List.of(
                    Position.valueOf(9, 4),
                    Position.valueOf(9, 6),
                    Position.valueOf(10, 5),
                    Position.valueOf(10, 4),
                    Position.valueOf(10, 6));

            BoardMediator boardMediator = new Board(positionPieceMap);
            List<Position> actual = redSoldier.calculateMovablePositions(Position.valueOf(9, 5), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }
    }
}