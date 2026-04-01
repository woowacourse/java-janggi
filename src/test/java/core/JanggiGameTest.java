package core;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import board.Board;
import java.util.Map;
import org.junit.jupiter.api.Test;
import participant.Turn;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Position;

class JanggiGameTest {

    @Test
    void 초의_턴일_때_한의_기물로_공격하는_경우_예외를_던진다() {
        // given
        Position departure = new Position(0, 0);
        Position destination = new Position(1, 0);
        Piece hanPiece = new Piece(Side.HAN, PieceType.CHA);
        Map<Position, Piece> pieces = Map.of(
            departure, hanPiece
        );

        Turn choTurn = Turn.CHO_TURN;
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
        Piece choPiece = new Piece(Side.CHO, PieceType.CHA);
        Map<Position, Piece> pieces = Map.of(
            departure, choPiece
        );

        Turn hanTurn = Turn.HAN_TURN;
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
        Piece choPiece = new Piece(Side.CHO, PieceType.CHA);
        Piece hanPiece = new Piece(Side.HAN, PieceType.CHA);
        Map<Position, Piece> pieces = Map.of(
            choDeparture, choPiece,
            hanDeparture, hanPiece
        );

        Turn choTurn = Turn.CHO_TURN;
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
        Piece choPiece = new Piece(Side.CHO, PieceType.CHA);
        Map<Position, Piece> pieces = Map.of(
            departure, choPiece
        );
        // when
        JanggiGame janggiGame = new JanggiGame(new Board(pieces));
        // then
        assertThatCode(() -> janggiGame.move(departure, destination))
            .doesNotThrowAnyException();
    }
}