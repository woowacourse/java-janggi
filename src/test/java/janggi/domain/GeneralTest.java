package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.piece.General;
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

public class GeneralTest {

    @Nested
    @DisplayName("이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        static Piece general;
        static Piece enemy1;
        static Piece enemy2;
        static Piece enemy3;
        static Piece enemy4;
        static Piece ally1;
        static Piece ally2;
        static Piece ally3;
        static Piece ally4;
        static Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            general = new General(TeamType.RED);
            enemy1 = new Soldier(TeamType.BLUE);
            enemy3 = new Soldier(TeamType.BLUE);
            enemy4 = new Soldier(TeamType.BLUE);
            enemy2 = new Soldier(TeamType.BLUE);
            ally1 = new Soldier(TeamType.RED);
            ally2 = new Soldier(TeamType.RED);
            ally3 = new Soldier(TeamType.RED);
            ally4 = new Soldier(TeamType.RED);
            positionPieceMap = new LinkedHashMap<Position, Piece>();
        }

        @Test
        @DisplayName("장은 기물을 뛰어넘을 수 없다.")
        void test1() {
            positionPieceMap.put(Position.valueOf(6, 4), general);
            positionPieceMap.put(Position.valueOf(5, 3), ally1);
            positionPieceMap.put(Position.valueOf(5, 4), ally2);
            positionPieceMap.put(Position.valueOf(5, 5), ally3);
            positionPieceMap.put(Position.valueOf(6, 3), ally4);
            positionPieceMap.put(Position.valueOf(6, 5), enemy1);
            positionPieceMap.put(Position.valueOf(7, 3), enemy2);
            positionPieceMap.put(Position.valueOf(7, 4), enemy3);
            positionPieceMap.put(Position.valueOf(7, 5), enemy4);

            List<Position> expected = List.of(Position.valueOf(6, 5), Position.valueOf(7, 3),
                    Position.valueOf(7, 4), Position.valueOf(7, 5));

            Board board = new Board(positionPieceMap);
            List<Position> actual = general.calculateMovablePositions(Position.valueOf(6, 4), board);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("장은 모든 방향 중 한 칸을 가서 기물을 잡을 수 있다.")
        void test2() {
            positionPieceMap.put(Position.valueOf(6, 4), general);
            positionPieceMap.put(Position.valueOf(6, 5), enemy1);
            positionPieceMap.put(Position.valueOf(7, 3), enemy2);
            List<Position> expected = List.of(Position.valueOf(5, 3), Position.valueOf(5, 4),
                    Position.valueOf(5, 5), Position.valueOf(6, 3), Position.valueOf(6, 5), Position.valueOf(7, 3),
                    Position.valueOf(7, 4), Position.valueOf(7, 5));

            Board board = new Board(positionPieceMap);
            List<Position> actual = general.calculateMovablePositions(Position.valueOf(6, 4), board);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("장은 장기판 밖으로 이동할 수 없다.")
        void test3() {
            positionPieceMap.put(Position.valueOf(1, 1), general);
            positionPieceMap.put(Position.valueOf(1, 2), ally1);
            positionPieceMap.put(Position.valueOf(2, 1), ally2);
            positionPieceMap.put(Position.valueOf(2, 2), ally3);
            List<Position> expected = List.of();

            Board board = new Board(positionPieceMap);
            List<Position> actual = general.calculateMovablePositions(Position.valueOf(1, 1), board);

            assertThat(actual).hasSameElementsAs(expected);
        }
    }
}
