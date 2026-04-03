package core;

import static org.assertj.core.api.Assertions.assertThat;
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
    void 초의_턴으로_시작할_때_한의_기물로_공격하는_경우_예외를_던진다() {
        // given
        Position departure = new Position(0, 0);
        Position destination = new Position(1, 0);
        Piece hanPiece = new Piece(Side.HAN, PieceType.CHA);
        Map<Position, Piece> pieces = Map.of(
            departure, hanPiece
        );
        JanggiGame janggiGame = new JanggiGame(new Board(pieces));
        // when & then
        assertThatThrownBy(() -> janggiGame.move(departure, destination))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 한의_턴일_때_초의_기물로_공격하는_경우_예외를_던진다() {
        // given
        Position choDeparture = new Position(9, 0);
        Position choDestination = new Position(8, 0);
        Piece choPiece = new Piece(Side.CHO, PieceType.CHA);
        Map<Position, Piece> pieces = Map.of(
            choDeparture, choPiece
        );
        JanggiGame game = new JanggiGame(new Board(pieces));
        JanggiGame nextTurnGame = game.move(choDeparture, choDestination);
        // when & then
        assertThatThrownBy(() -> nextTurnGame.move(choDestination, choDeparture))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 기물을_한_번_이동시키면_턴이_바뀐다() {
        // given
        Position choDeparture = new Position(0, 0);
        Position choDestination = new Position(1, 0);
        Position hanDeparture = new Position(9, 0);
        Piece choPiece = new Piece(Side.CHO, PieceType.CHA);
        Piece hanPiece = new Piece(Side.HAN, PieceType.CHA);
        Map<Position, Piece> pieces = Map.of(
            choDeparture, choPiece,
            hanDeparture, hanPiece
        );
        JanggiGame game = new JanggiGame(new Board(pieces));
        // when & then
        game = game.move(choDeparture, choDestination);
        assertThat(game.getTurnSide()).isEqualTo(Side.HAN);
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

    @Test
    void 궁이_잡히면_게임이_종료된다() {
        // given
        Position departure = new Position(3, 4);
        Position destination = new Position(8, 4);
        Piece choPiece = new Piece(Side.CHO, PieceType.CHA);
        Piece hanGung = new Piece(Side.HAN, PieceType.GUNG);
        Map<Position, Piece> pieces = Map.of(
            departure, choPiece,
            destination, hanGung
        );
        JanggiGame game = new JanggiGame(new Board(pieces));
        // when
        game = game.move(departure, destination);
        // then
        assertThat(game.isOver()).isTrue();
    }

    @Test
    void 궁이_잡히지_않으면_게임이_계속_진행된다() {
        // given
        Position departure = new Position(1, 4);
        Position destination = new Position(1, 5);
        Piece choPiece = new Piece(Side.CHO, PieceType.CHA);
        Piece hanGung = new Piece(Side.HAN, PieceType.GUNG);
        Map<Position, Piece> pieces = Map.of(
            departure, choPiece,
            new Position(8, 4), hanGung
        );
        JanggiGame game = new JanggiGame(new Board(pieces));
        // when
        game = game.move(departure, destination);
        // then
        assertThat(game.isOver()).isFalse();
    }

    @Test
    void 게임이_종료된_상태에_기물을_움직일_경우_예외를_던진다() {
        // given
        boolean isOver = true;
        Position departure = new Position(1, 4);
        Position destination = new Position(1, 5);
        Piece choPiece = new Piece(Side.CHO, PieceType.CHA);
        Map<Position, Piece> pieces = Map.of(
            departure, choPiece
        );
        JanggiGame game = new JanggiGame(new Board(pieces), Turn.CHO_TURN, isOver);
        // when & then
        assertThatThrownBy(() -> game.move(departure, destination))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 게임이_종료되면_승리_진영을_조회할_수_있다() {
        // given
        Position departure = new Position(3, 4);
        Position destination = new Position(8, 4);
        Piece choPiece = new Piece(Side.CHO, PieceType.CHA);
        Piece hanGung = new Piece(Side.HAN, PieceType.GUNG);
        Map<Position, Piece> pieces = Map.of(
            departure, choPiece,
            destination, hanGung
        );
        JanggiGame game = new JanggiGame(new Board(pieces));
        // when
        game = game.move(departure, destination);
        // then
        assertThat(game.getWinnerSide()).isEqualTo(Side.CHO);
    }

    @Test
    void 게임이_진행중일_때_승리_진영을_조회하는_경우_예외를_던진다() {
        // given
        boolean isOver = false;
        Map<Position, Piece> pieces = Map.of();
        JanggiGame game = new JanggiGame(new Board(pieces), Turn.CHO_TURN, isOver);
        // when & then
        assertThatThrownBy(game::getWinnerSide)
            .isInstanceOf(IllegalArgumentException.class);
    }
}