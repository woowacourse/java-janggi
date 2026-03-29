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

public class GeneralTest {

    @Nested
    @DisplayName("이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        Piece general;
        Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            general = new General(TeamType.RED);
            positionPieceMap = new LinkedHashMap<>();
        }

        @Test
        @DisplayName("기물을 뛰어넘을 수 없다.")
        void success_1() {
            positionPieceMap.put(Position.valueOf(6, 4), general);
            positionPieceMap.put(Position.valueOf(5, 3), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(5, 5), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(6, 3), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(6, 5), new Soldier(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(7, 3), new Soldier(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(7, 4), new Soldier(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(7, 5), new Soldier(TeamType.BLUE));

            List<Position> expected = List.of(Position.valueOf(6, 5), Position.valueOf(7, 3),
                Position.valueOf(7, 4), Position.valueOf(7, 5));

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> actual = general.calculateMovablePositions(Position.valueOf(6, 4),
                boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("모든 방향 중 한 칸을 가서 기물을 잡을 수 있다.")
        void success_2() {
            positionPieceMap.put(Position.valueOf(6, 4), general);
            positionPieceMap.put(Position.valueOf(6, 5), new Soldier(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(7, 3), new Soldier(TeamType.BLUE));
            List<Position> expected = List.of(Position.valueOf(5, 3), Position.valueOf(5, 4),
                Position.valueOf(5, 5), Position.valueOf(6, 3), Position.valueOf(6, 5),
                Position.valueOf(7, 3),
                Position.valueOf(7, 4), Position.valueOf(7, 5));

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> actual = general.calculateMovablePositions(Position.valueOf(6, 4),
                boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("장기판 밖으로 이동할 수 없다.")
        void success_3() {
            positionPieceMap.put(Position.valueOf(1, 1), general);
            positionPieceMap.put(Position.valueOf(1, 2), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(2, 1), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(2, 2), new Soldier(TeamType.RED));
            List<Position> expected = List.of();

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);
            List<Position> actual = general.calculateMovablePositions(Position.valueOf(1, 1),
                boardMediator);

            assertThat(actual).hasSameElementsAs(expected);
        }
    }
}
