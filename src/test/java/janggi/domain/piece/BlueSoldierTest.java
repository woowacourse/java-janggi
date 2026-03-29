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

public class BlueSoldierTest {

    @Nested
    @DisplayName("이동 가능한 위치 계산 테스트")
    class CalculateMovablePositions {

        Piece blueSoldier;
        Map<Position, Piece> positionPieceMap;

        @BeforeEach
        void setUp() {
            blueSoldier = new Soldier(TeamType.BLUE);
            positionPieceMap = new LinkedHashMap<>();
        }

        @Test
        @DisplayName("앞, 좌, 우로 이동할 수 있고 적이 있으면 잡을 수 있다.")
        void success_1() {
            positionPieceMap.put(Position.valueOf(6, 4), blueSoldier);
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.RED));
            positionPieceMap.put(Position.valueOf(6, 3), new Soldier(TeamType.RED));

            List<Position> expected = List.of(
                Position.valueOf(5, 4),
                Position.valueOf(6, 3),
                Position.valueOf(6, 5)
            );

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);

            List<Position> actual = blueSoldier.calculateMovablePositions(
                Position.valueOf(6, 4), boardMediator
            );

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("아군이 있는 위치로 이동할 수 없다.")
        void success_2() {
            positionPieceMap.put(Position.valueOf(6, 4), blueSoldier);
            positionPieceMap.put(Position.valueOf(5, 4), new Soldier(TeamType.BLUE));
            positionPieceMap.put(Position.valueOf(6, 5), new Soldier(TeamType.BLUE));

            List<Position> expected = List.of(
                Position.valueOf(6, 3)
            );

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);

            List<Position> actual = blueSoldier.calculateMovablePositions(
                Position.valueOf(6, 4), boardMediator
            );

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        @DisplayName("장기판 밖으로 이동할 수 없다.")
        void success_3() {
            positionPieceMap.put(Position.valueOf(1, 1), blueSoldier);
            positionPieceMap.put(Position.valueOf(1, 2), new Soldier(TeamType.BLUE));

            List<Position> expected = List.of();

            Board board = new Board(positionPieceMap);
            BoardMediator boardMediator = new BoardMediatorImpl(board);

            List<Position> actual = blueSoldier.calculateMovablePositions(
                Position.valueOf(1, 1), boardMediator
            );

            assertThat(actual).hasSameElementsAs(expected);
        }
    }
}