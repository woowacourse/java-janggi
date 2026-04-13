package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.Position;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.team.TeamType;
import janggi.domain.team.TurnManager;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("현재 차례 플레이어의 장이 살아있다")
    void generalIsTwo() {
        Map<Position, Piece> positionPieceMap;
        Position position1 = Position.valueOf(9, 5);
        TurnManager turnManager = new TurnManager();
        positionPieceMap = Map.of(position1, new General(TeamType.RED));
        Board board = new Board(positionPieceMap);
        boolean expected = true;

        boolean actual = board.hasGeneral(turnManager.currentTeamType());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("현재 차례 플레이어의 장이 없다")
    void generalIsOne() {
        Map<Position, Piece> positionPieceMap;
        Position position1 = Position.valueOf(9, 5);
        TurnManager turnManager = new TurnManager();
        positionPieceMap = Map.of(position1, new General(TeamType.BLUE));
        Board board = new Board(positionPieceMap);
        boolean expected = false;

        boolean actual = board.hasGeneral(turnManager.currentTeamType());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("빈 칸의 기물을 이동하려고 한 경우")
    void failure() {
        Map<Position, Piece> positionPieceMap = new LinkedHashMap<>();
        Board board = new Board(positionPieceMap);
        Position position = Position.valueOf(9, 5);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> board.calculateMovablePositions(position));
    }

    @Nested
    @DisplayName("빈칸 여부 테스트")
    class isBlank {
        @Test
        @DisplayName("빈칸인 경우")
        void success_1() {
            Board board = new Board(new LinkedHashMap<>());
            Position position = Position.valueOf(1, 1);
            boolean expected = false;

            boolean actual = board.hasPieceAt(position);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("빈칸이 아닌 경우")
        void success_2() {
            Position position = Position.valueOf(1, 1);
            Map<Position, Piece> positionPieceMap = Map.of(position, new Cannon(TeamType.RED));
            Board board = new Board(positionPieceMap);
            boolean expected = true;

            boolean actual = board.hasPieceAt(position);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("궁성 영역 테스트")
    class isPalace {
        @Test
        @DisplayName("궁성 영역인 경우")
        void success_1() {
            LinkedHashMap<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            Position position = Position.valueOf(1, 4);
            BoardMediator boardMediator = new Board(positionPieceMap);
            boolean expected = true;

            boolean actual = boardMediator.isPalace(position);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("궁성 영역이 아닌 경우")
        void success_2() {
            LinkedHashMap<Position, Piece> positionPieceMap = new LinkedHashMap<>();
            Position position = Position.valueOf(1, 7);
            BoardMediator boardMediator = new Board(positionPieceMap);
            boolean expected = false;

            boolean actual = boardMediator.isPalace(position);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("점수 계산 테스트")
    class calculateScore {

        @Test
        @DisplayName("한나라 점수 계산")
        void success_1() {
            Map<Position, Piece> pieces = Map.of(
                    Position.valueOf(1, 1), new Chariot(TeamType.RED),
                    Position.valueOf(2, 1), new Cannon(TeamType.RED)
            );
            Board board = new Board(pieces);
            assertThat(board.calculateScore(TeamType.RED)).isEqualTo(20.0);
        }

        @Test
        @DisplayName("초나라 점수 계산")
        void success_2() {
            Map<Position, Piece> pieces = Map.of(
                    Position.valueOf(1, 1), new Soldier(TeamType.BLUE),
                    Position.valueOf(2, 1), new Guard(TeamType.BLUE)
            );
            Board board = new Board(pieces);
            assertThat(board.calculateScore(TeamType.BLUE)).isEqualTo(6.5);
        }
    }
}
