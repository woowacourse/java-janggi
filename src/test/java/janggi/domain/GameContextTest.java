package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.piece.General;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import janggi.domain.team.TurnManager;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GameContextTest {

    @Test
    @DisplayName("현재 턴(RED)은 진 팀의 턴이므로, 승리한 팀은 BLUE인 경우")
    void success1() {
        // given
        GameContext gameContext = new GameContext(new TurnManager(), new Board(Map.of()));

        // when
        String winTeamName = gameContext.currentWinTeamTypeToName();

        // then
        assertThat(winTeamName).isEqualTo(TeamType.BLUE.getName());
    }

    @Nested
    @DisplayName("makeMove 테스트")
    class MakeMove {

        private Map<Position, Piece> pieceMap;
        private Position from;
        private Position to;
        private GameContext gameContext;

        @BeforeEach
        void setup() {
            from = Position.valueOf(1, 1);
            to = Position.valueOf(1, 2);
            pieceMap = new HashMap<>();
            pieceMap.put(from, new General(TeamType.RED));
            gameContext = new GameContext(new TurnManager(), new Board(pieceMap));
        }

        @Test
        @DisplayName("기물을 이동하면 턴이 변경된다")
        void success1() {
            // when
            gameContext.makeMove(from, to);

            // then
            assertThat(gameContext.currentTeamType()).isEqualTo(TeamType.BLUE);
        }

        @Test
        @DisplayName("기물을 이동하면 기물의 위치가 변경된다")
        void success2() {
            // when
            gameContext.makeMove(from, to);

            // then
            Map<Position, Piece> result = gameContext.getPositionPieceMap();
            assertThat(result).containsKey(to);
        }

        @Test
        @DisplayName("기물을 이동하면 기존의 위치는 보드에 포함되지 않는다")
        void success3() {
            // when
            gameContext.makeMove(from, to);

            // then
            Map<Position, Piece> result = gameContext.getPositionPieceMap();
            assertThat(result).doesNotContainKey(from);
        }
    }
}