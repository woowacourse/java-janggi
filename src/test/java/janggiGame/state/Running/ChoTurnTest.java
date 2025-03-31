package janggiGame.state.Running;

import static org.assertj.core.api.Assertions.assertThat;

import janggiGame.piece.Dynasty;
import janggiGame.piece.oneMovePiece.King;
import janggiGame.piece.straightMovePiece.Chariot;
import janggiGame.position.Position;
import janggiGame.state.Finished.ChoWin;
import janggiGame.state.Finished.Draw;
import janggiGame.state.State;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChoTurnTest {
    @DisplayName("초나라가 턴을 진행하면 한나라의 턴으로 넘어간다")
    @Test
    void choToHan() {
        // given
        Position origin = Position.getInstanceBy(0, 0);
        Position destination = Position.getInstanceBy(0, 1);
        State choTurn = new ChoTurn(Map.of(origin, new Chariot(Dynasty.CHO)), false);

        // when
        State actual = choTurn.takeTurn(origin, destination);

        // then
        assertThat(actual).isInstanceOf(HanTurn.class);
    }

    @DisplayName("이전 턴이 스킵되었을때 턴을 스킵하면 무승부이다")
    @Test
    void choToDraw() {
        // given
        Position origin = Position.getInstanceBy(0, 0);
        State choTurn = new ChoTurn(Map.of(origin, new Chariot(Dynasty.CHO)), true);

        // when
        State actual = choTurn.skipTurn();

        // then
        assertThat(actual).isInstanceOf(Draw.class);
    }

    @DisplayName("초나라가 턴을 진행할 때 장을 잡으면 초나라 승리이다")
    @Test
    void choToChoWin() {
        // given
        Position origin = Position.getInstanceBy(0, 0);
        Position destination = Position.getInstanceBy(0, 1);
        State choTurn = new ChoTurn(Map.of(origin, new Chariot(Dynasty.CHO), destination, new King(Dynasty.HAN)),
                false);

        // when
        State actual = choTurn.takeTurn(origin, destination);

        // then
        assertThat(actual).isInstanceOf(ChoWin.class);
    }

    @DisplayName("ChoTurn에서 현재 왕조를 조회하면 초나라가 반환된다")
    @Test
    void getCurrentDynasty() {
        // given
        State choTurn = new ChoTurn(Map.of(Position.getInstanceBy(0, 0), new Chariot(Dynasty.CHO)), true);

        // when
        Dynasty actual = choTurn.getCurrentDynasty();

        // then
        assertThat(actual).isEqualTo(Dynasty.CHO);
    }
}
