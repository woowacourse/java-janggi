package janggiGame.state.Running;

import static org.assertj.core.api.Assertions.assertThat;

import janggiGame.piece.Dynasty;
import janggiGame.piece.oneMovePiece.King;
import janggiGame.piece.straightMovePiece.Chariot;
import janggiGame.position.Position;
import janggiGame.state.Finished.Draw;
import janggiGame.state.Finished.HanWin;
import janggiGame.state.State;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HanTurnTest {
    @DisplayName("한나라가 턴을 진행하면 초나라의 턴으로 넘어간다")
    @Test
    void hanToCho() {
        // given
        Position origin = Position.getInstanceBy(0, 0);
        Position destination = Position.getInstanceBy(0, 1);
        State hanTurn = new HanTurn(Map.of(origin, new Chariot(Dynasty.HAN)), false);

        // when
        State actual = hanTurn.takeTurn(origin, destination);

        // then
        assertThat(actual).isInstanceOf(ChoTurn.class);
    }

    @DisplayName("이전 턴이 스킵되었을때 턴을 스킵하면 무승부이다")
    @Test
    void hanToDraw() {
        // given
        Position origin = Position.getInstanceBy(0, 0);
        State hanTurn = new HanTurn(Map.of(origin, new Chariot(Dynasty.HAN)), true);

        // when
        State actual = hanTurn.skipTurn();

        // then
        assertThat(actual).isInstanceOf(Draw.class);
    }

    @DisplayName("한나라가 턴을 진행할 때 장을 잡으면 초나라 승리이다")
    @Test
    void hanToHanWin() {
        // given
        Position origin = Position.getInstanceBy(0, 0);
        Position destination = Position.getInstanceBy(0, 1);
        State hanTurn = new HanTurn(Map.of(origin, new Chariot(Dynasty.HAN), destination, new King(Dynasty.CHO)),
                false);

        // when
        State actual = hanTurn.takeTurn(origin, destination);

        // then
        assertThat(actual).isInstanceOf(HanWin.class);
    }

    @DisplayName("HanTurn에서 현재 왕조를 조회하면 한나라가 반환된다")
    @Test
    void getCurrentDynasty() {
        // given
        State hanTurn = new HanTurn(Map.of(Position.getInstanceBy(0, 0), new Chariot(Dynasty.HAN)), true);

        // when
        Dynasty actual = hanTurn.getCurrentDynasty();

        // then
        assertThat(actual).isEqualTo(Dynasty.HAN);
    }
}
