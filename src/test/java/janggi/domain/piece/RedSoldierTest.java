package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
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

        static Piece redSoldier;
        static Piece enemy1;
        static Piece enemy2;
        static Piece ally1;
        static Piece ally2;
        static Map<Position, Piece> positionPieceMap;

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

            Board board = new Board(positionPieceMap);

            List<Position> actual = redSoldier.calculateMovablePositions(
                    Position.valueOf(6, 4), board
            );

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("홍졸은 아군이 있는 위치로 이동할 수 없다.")
        void test2() {
            positionPieceMap.put(Position.valueOf(6, 4), redSoldier);
            positionPieceMap.put(Position.valueOf(7, 4), ally1);
            positionPieceMap.put(Position.valueOf(6, 5), ally2);

            List<Position> expected = List.of(
                    Position.valueOf(6, 3)
            );

            Board board = new Board(positionPieceMap);

            List<Position> actual = redSoldier.calculateMovablePositions(
                    Position.valueOf(6, 4), board
            );

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("홍졸은 장기판 밖으로 이동할 수 없다.")
        void test3() {
            positionPieceMap.put(Position.valueOf(10, 1), redSoldier);
            positionPieceMap.put(Position.valueOf(10, 2), ally1);

            List<Position> expected = List.of();

            Board board = new Board(positionPieceMap);

            List<Position> actual = redSoldier.calculateMovablePositions(
                    Position.valueOf(10, 1), board
            );

            assertThat(actual).hasSameElementsAs(expected);
        }
    }
}