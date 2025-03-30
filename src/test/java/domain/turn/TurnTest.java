package domain.turn;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Board;
import domain.TeamType;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    @DisplayName("게임을 시작하는 경우 Playing 객체를 생성한다")
    void startTest() {
        // given
        Map<Position, Piece> pieces = Map.of();

        // when
        Turn start = Turn.start(pieces);

        // then
        assertThat(start.turnState).isEqualTo(new TurnState(false, TeamType.CHO));
    }

    @Test
    @DisplayName("다음 턴에 진행해야 할 팀을 반환한다")
    void getNextPlayerTeamTest() {
        // given
        Map<Position, Piece> pieces = Map.of();
        TeamType playerTeam = TeamType.CHO;
        TurnState turnState = new TurnState(false, playerTeam);
        Turn turn = new Playing(new Board(pieces), turnState);

        // when
        TeamType actual = turn.getNextPlayerTeam();

        // then
        TeamType expected = TeamType.HAN;
        assertThat(actual).isEqualTo(expected);
    }
}
