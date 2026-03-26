package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.BoardMediatorImpl;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import java.util.ArrayList;
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

        static Piece chariot;
        static Piece enemy1;
        static Piece enemy2;
        static Piece enemy3;
        static Piece enemy4;
        static Piece ally1;
        static Piece ally2;
        static Piece ally3;
        static Piece ally4;
        static Map<Position, Piece> positionPieceMap;
        static List<Position> candidatePositions;

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
            positionPieceMap = new LinkedHashMap<Position, Piece>();
            candidatePositions = initCanididatePositions();
        }

        @Test
        @DisplayName("차는 적군을 뛰어넘어 갈 수 없다.")
        void test1() {
            positionPieceMap.put(Position.valueOf(5, 3), chariot);
            positionPieceMap.put(Position.valueOf(5, 1), enemy1);
            positionPieceMap.put(Position.valueOf(3, 3), enemy2);
            positionPieceMap.put(Position.valueOf(5, 6), enemy3);
            positionPieceMap.put(Position.valueOf(7, 3), enemy4);
            List<Position> expected = List.of(Position.valueOf(3, 3), Position.valueOf(4, 3), Position.valueOf(5, 1),
                    Position.valueOf(5, 2), Position.valueOf(5, 4),
                    Position.valueOf(5, 5), Position.valueOf(5, 6),
                    Position.valueOf(6, 3), Position.valueOf(7, 3));

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
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
            List<Position> expected = List.of(Position.valueOf(4, 3),
                    Position.valueOf(5, 2), Position.valueOf(5, 4),
                    Position.valueOf(5, 5), Position.valueOf(6, 3));

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> actual = chariot.calculateMovablePositions(Position.valueOf(5, 3), boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        List<Position> initCanididatePositions() {
            List<Position> candidatePositions = new ArrayList<>();
            for (int row = 1; row <= 10; row++) {
                candidatePositions.add(Position.valueOf(row, 3));
            }
            for (int column = 1; column <= 9; column++) {
                candidatePositions.add(Position.valueOf(5, column));
            }
            candidatePositions.remove(Position.valueOf(5, 3));
            candidatePositions.remove(Position.valueOf(5, 3));
            return candidatePositions;
        }
    }
}
