package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
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
        Position hanDeparture = new Position(9, 0);
        Position hanDestination = new Position(8, 0);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(choChaDeparture, new Cha(Side.CHO));
        pieces.put(choChaDestination, new EmptyPiece());
        pieces.put(hanDeparture, new Cha(Side.HAN));
        pieces.put(hanDestination, new EmptyPiece());
        JanggiGame janggiGame = new JanggiGame(new Board(pieces));
        // when & then
        assertThatThrownBy(() -> janggiGame.move(hanDeparture, hanDestination))
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

        janggiGame.move(choChaDeparture, choChaDestination);
        // when & then
        assertThatThrownBy(() -> janggiGame.move(choChaDestination, new Position(2, 0)))
            .isInstanceOf(InvalidTurnException.class)
            .hasMessage(GameErrorMessage.HAN_TURN.message());
    }

    @Test
    void 이동이_성공하면_보드_상태가_변경된다() {
        // given
        Position departure = new Position(0, 0);
        Position destination = new Position(1, 0);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(departure, new Cha(Side.CHO));
        pieces.put(destination, new EmptyPiece());
        JanggiGame janggiGame = new JanggiGame(new Board(pieces));

        // when
        janggiGame.move(departure, destination);

        // then
        assertThat(janggiGame.board().pieces().get(departure)).isInstanceOf(EmptyPiece.class);
        assertThat(janggiGame.board().pieces().get(destination)).isEqualTo(new Cha(Side.CHO));
    }

    @Test
    void 이동이_성공하면_현재_턴이_바뀐다() {
        // given
        Position departure = new Position(0, 0);
        Position destination = new Position(1, 0);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(departure, new Cha(Side.CHO));
        pieces.put(destination, new EmptyPiece());
        JanggiGame janggiGame = new JanggiGame(new Board(pieces));

        // when
        janggiGame.move(departure, destination);

        // then
        assertThat(janggiGame.currentTurn()).isEqualTo(Side.HAN);
    }
}
