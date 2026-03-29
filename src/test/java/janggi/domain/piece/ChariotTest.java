package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.BoardMediatorImpl;
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

        Piece chariot;
        Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            chariot = new Chariot(TeamType.RED);
            positionPieceMap = new LinkedHashMap<>();
        }

        @Test
        @DisplayName("적군을 뛰어넘어 갈 수 없다.")
        void success_1() {
            positionPieceMap.put(Position.valueOf(5, 3), chariot);
            positionPieceMap.put(Position.valueOf(5, 1), new Soldier(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(3, 3), new Soldier(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(5, 6), new Soldier(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(7, 3), new Soldier(TeamType.BLUE));
            List<Position> expected = List.of(Position.valueOf(3, 3), Position.valueOf(4, 3),
                Position.valueOf(5, 1),
                Position.valueOf(5, 2), Position.valueOf(5, 4),
                Position.valueOf(5, 5), Position.valueOf(5, 6),
                Position.valueOf(6, 3), Position.valueOf(7, 3));

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> actual = chariot.calculateMovablePositions(Position.valueOf(5, 3),
                boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("아군을 뛰어넘어 갈 수 없다.")
        void success_2() {
            positionPieceMap.put(Position.valueOf(5, 3), chariot);
            positionPieceMap.put(Position.valueOf(5, 1), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(3, 3), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(5, 6), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(7, 3), new Soldier(TeamType.RED));
            List<Position> expected = List.of(Position.valueOf(4, 3),
                Position.valueOf(5, 2), Position.valueOf(5, 4),
                Position.valueOf(5, 5), Position.valueOf(6, 3));

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> actual = chariot.calculateMovablePositions(Position.valueOf(5, 3),
                boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }
    }
}
