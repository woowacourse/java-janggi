package core;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import board.Board;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import participant.ChoTurn;
import participant.HanTurn;
import participant.Turn;
import pieces.Cha;
import pieces.EmptyPiece;
import pieces.Piece;
import pieces.Side;
import position.Position;

class JanggiGameTest {

    @Test
    void 초의_턴일_때_한의_기물로_공격하는_경우_예외를_던진다() {
        // given
        Position departure = new Position(0, 0);
        Position destination = new Position(1, 0);
        Piece hanPiece = new Cha(Side.HAN);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(departure, hanPiece);
        pieces.put(destination, new EmptyPiece());

        Turn choTurn = new ChoTurn();
        JanggiGame janggiGame = new JanggiGame(new Board(pieces), choTurn);
        // when & then
        assertThatThrownBy(() -> janggiGame.move(departure, destination))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 한의_턴일_때_초의_기물로_공격하는_경우_예외를_던진다() {
        // given
        Position departure = new Position(9, 0);
        Position destination = new Position(8, 0);
        Piece choPiece = new Cha(Side.CHO);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(departure, choPiece);
        pieces.put(destination, new EmptyPiece());

        Turn hanTurn = new HanTurn();
        JanggiGame janggiGame = new JanggiGame(new Board(pieces), hanTurn);
        // when & then
        assertThatThrownBy(() -> janggiGame.move(departure, destination))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 기물을_한_번_이동시키면_턴이_바뀐다() {
        // given
        Position choDeparture = new Position(0, 0);
        Position choDestination = new Position(1, 0);
        Position hanDeparture = new Position(9, 0);
        Position hanDestination = new Position(8, 0);
        Piece choPiece = new Cha(Side.CHO);
        Piece hanPiece = new Cha(Side.HAN);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(choDeparture, choPiece);
        pieces.put(choDestination, new EmptyPiece());
        pieces.put(hanDeparture, hanPiece);
        pieces.put(hanDestination, new EmptyPiece());

        Turn choTurn = new ChoTurn();
        JanggiGame janggiGame = new JanggiGame(new Board(pieces), choTurn);
        // when & then
        janggiGame.move(choDeparture, choDestination);
        assertThatCode(() -> janggiGame.move(hanDeparture, hanDestination))
            .doesNotThrowAnyException();
        assertThatCode(() -> janggiGame.move(choDestination, choDeparture))
            .doesNotThrowAnyException();
    }

    @Test
    void 기본_게임은_초가_먼저_시작한다() {
        // given
        Position departure = new Position(9, 0);
        Position destination = new Position(8, 0);
        Piece choPiece = new Cha(Side.CHO);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(departure, choPiece);
        pieces.put(destination, new EmptyPiece());
        // when
        JanggiGame janggiGame = new JanggiGame(new Board(pieces));
        // then
        assertThatCode(() -> janggiGame.move(departure, destination))
            .doesNotThrowAnyException();
    }
}