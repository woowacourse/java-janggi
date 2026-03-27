package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.BoardMediatorImpl;
import janggi.domain.piece.Horse;
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

public class HorseTest {

    @Nested
    @DisplayName("이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        static Piece horse;
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
            horse = new Horse(TeamType.RED);
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
        @DisplayName("마는 기물을 뛰어넘을 수 없다.")
        void test1() {
            positionPieceMap.put(Position.valueOf(6, 4), horse);
            positionPieceMap.put(Position.valueOf(6, 3), ally1);
            positionPieceMap.put(Position.valueOf(5, 4), enemy1);
            positionPieceMap.put(Position.valueOf(7, 4), ally2);
            positionPieceMap.put(Position.valueOf(6, 5), ally3);
            List<Position> expected = List.of();

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> actual = horse.calculateMovablePositions(Position.valueOf(6, 4),
                    boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("마는 직선 한 칸, 대각선 한 칸을 가서 기물을 잡을 수 있다.")
        void test2() {
            positionPieceMap.put(Position.valueOf(6, 4), horse);
            positionPieceMap.put(Position.valueOf(5, 6), enemy1);
            positionPieceMap.put(Position.valueOf(7, 2), enemy2);
            List<Position> expected = List.of(Position.valueOf(4, 3), Position.valueOf(4, 5),
                    Position.valueOf(5, 6), Position.valueOf(7, 6),
                    Position.valueOf(8, 3), Position.valueOf(8, 5), Position.valueOf(7, 2),
                    Position.valueOf(5, 2));

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> actual = horse.calculateMovablePositions(Position.valueOf(6, 4),
                    boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("마는 장기판 밖으로 이동할 수 없다.")
        void test3() {
            positionPieceMap.put(Position.valueOf(1, 1), horse);
            positionPieceMap.put(Position.valueOf(1, 2), ally1);
            positionPieceMap.put(Position.valueOf(2, 1), ally3);
            List<Position> expected = List.of();

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> actual = horse.calculateMovablePositions(Position.valueOf(1, 1),
                    boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }
    }
}
