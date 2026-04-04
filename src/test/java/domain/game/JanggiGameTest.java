package domain.game;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.game.exception.GameErrorMessage;
import domain.game.exception.InvalidTurnException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import domain.pieces.Cha;
import domain.pieces.EmptyPiece;
import domain.pieces.Piece;
import domain.pieces.Side;
import domain.position.Position;

class JanggiGameTest {

    @Test
    void 초의_턴일_때_한이_공격하는_경우_예외를_던진다() {
        // given
        Position choChaDeparture = new Position(0, 0);
        Position choChaDestination = new Position(1, 0);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(choChaDeparture, new Cha(Side.CHO));
        pieces.put(choChaDestination, new EmptyPiece());
        JanggiGame janggiGame = new JanggiGame(new Board(pieces));
        // when & then
        assertThatThrownBy(() -> janggiGame.move(choChaDeparture, choChaDestination, Side.HAN))
            .isInstanceOf(InvalidTurnException.class)
            .hasMessage(GameErrorMessage.CHO_TURN.message());
    }

    @Test
    void 한의_턴일_때_초가_공격하는_경우_예외를_던진다() {
        // given
        Position choChaDeparture = new Position(0, 0);
        Position choChaDestination = new Position(1, 0);
        Position hanDeparture = new Position(9, 0);
        Position hanDestination = new Position(8, 0);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(choChaDeparture, new Cha(Side.CHO));
        pieces.put(choChaDestination, new EmptyPiece());
        pieces.put(hanDeparture, new Cha(Side.HAN));
        pieces.put(hanDestination, new EmptyPiece());
        JanggiGame janggiGame = new JanggiGame(new Board(pieces));

        janggiGame.move(choChaDeparture, choChaDestination, Side.CHO);
        // when & then
        assertThatThrownBy(() -> janggiGame.move(hanDeparture, hanDestination, Side.CHO))
            .isInstanceOf(InvalidTurnException.class)
            .hasMessage(GameErrorMessage.HAN_TURN.message());
    }
}
