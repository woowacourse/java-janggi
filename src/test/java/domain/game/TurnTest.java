package domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.exception.GameErrorMessage;
import domain.pieces.Cha;
import domain.pieces.Piece;
import domain.pieces.Side;
import org.junit.jupiter.api.Test;

class TurnTest {

    @Test
    void 초_턴은_한나라_기물을_현재_턴의_기물이_아닌_것으로_판단한다() {
        // given
        Turn turn = Turn.start();
        Piece piece = new Cha(Side.HAN);

        // when & then
        assertThat(turn.isNotCurrentTurnPiece(piece)).isTrue();
    }

    @Test
    void 초_턴은_초나라_기물을_현재_턴의_기물로_판단한다() {
        // given
        Turn turn = Turn.start();
        Piece piece = new Cha(Side.CHO);

        // when & then
        assertThat(turn.isNotCurrentTurnPiece(piece)).isFalse();
    }

    @Test
    void 초_턴의_에러_메시지를_반환한다() {
        // given
        Turn turn = Turn.start();

        // when & then
        assertThat(turn.errorMessage()).isEqualTo(GameErrorMessage.CHO_TURN);
    }

    @Test
    void 다음_턴으로_전환한다() {
        // given
        Turn turn = Turn.start();

        // when
        Turn nextTurn = turn.next();

        // then
        assertThat(nextTurn.side()).isEqualTo(Side.HAN);
    }
}
