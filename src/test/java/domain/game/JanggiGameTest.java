package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.SangSetup;
import domain.game.exception.GameEndedException;
import domain.game.exception.GameErrorMessage;
import domain.game.exception.InvalidTurnException;
import domain.pieces.Cha;
import domain.pieces.EmptyPiece;
import domain.pieces.Gung;
import domain.pieces.Piece;
import domain.pieces.Side;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

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

    @Test
    void 왕을_잡으면_게임이_종료된다() {
        // given
        Position departure = new Position(7, 4);
        Position destination = new Position(8, 4);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(departure, new Cha(Side.CHO));
        pieces.put(destination, new Gung(Side.HAN));
        JanggiGame janggiGame = new JanggiGame(new Board(pieces));

        // when
        janggiGame.move(departure, destination);

        // then
        assertThat(janggiGame.gameResult().isEnded()).isTrue();
        assertThat(janggiGame.gameResult().winner()).isEqualTo(Side.CHO);
    }

    @Test
    void 게임이_종료된_후_추가_이동하면_예외를_던진다() {
        // given
        Position departure = new Position(7, 4);
        Position destination = new Position(8, 4);
        Position nextDestination = new Position(9, 4);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(departure, new Cha(Side.CHO));
        pieces.put(destination, new Gung(Side.HAN));
        pieces.put(nextDestination, new EmptyPiece());
        JanggiGame janggiGame = new JanggiGame(new Board(pieces));

        janggiGame.move(departure, destination);

        // when & then
        assertThatThrownBy(() -> janggiGame.move(destination, nextDestination))
                .isInstanceOf(GameEndedException.class)
                .hasMessage(GameErrorMessage.GAME_ALREADY_ENDED.message());
    }

    @Test
    void 점수로_게임을_종료하면_게임결과가_종료상태로_변경된다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(0, 0), new Cha(Side.CHO));
        pieces.put(new Position(8, 4), new Gung(Side.HAN));
        JanggiGame janggiGame = new JanggiGame(new Board(pieces));

        // when
        GameScore gameScore = janggiGame.finishByScore();

        // then
        assertThat(janggiGame.gameResult().isEnded()).isTrue();
        assertThat(janggiGame.gameResult().winner()).isEqualTo(gameScore.winner());
    }

    @Test
    void 복원하면_현재_턴과_게임_결과와_보드_상태를_유지한다() {
        // given
        Position position1 = new Position(7, 4);
        Position position2 = new Position(8, 4);

        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(position1, new Cha(Side.CHO));
        pieces.put(position2, new Gung(Side.HAN));

        Board board = new Board(pieces);
        Side currentTurn = Side.CHO;
        GameResult gameResult = GameResult.running();

        // when
        JanggiGame restoredGame = JanggiGame.restore(1L, board, currentTurn, gameResult);

        // then
        assertThat(restoredGame.gameId()).isEqualTo(1L);
        assertThat(restoredGame.currentTurn()).isEqualTo(currentTurn);
        assertThat(restoredGame.gameResult()).isEqualTo(gameResult);
        assertThat(restoredGame.board()).isEqualTo(board);
    }

    @Test
    void 생성하면_초와_한_보드를_합쳐_초의_턴인_진행중_게임을_만든다() {
        // given & when
        Board choBoard = new Board(Map.of(
                new Position(0, 0), new Cha(Side.CHO)
        ));
        Board hanBoard = new Board(Map.of(
                new Position(9, 0), new Cha(Side.HAN)
        ));

        SangSetup choSetup = side -> choBoard;
        SangSetup hanSetup = side -> hanBoard;

        JanggiGame janggiGame = JanggiGame.of(choSetup, hanSetup);

        // then
        assertThat(janggiGame.gameId()).isNull();
        assertThat(janggiGame.currentTurn()).isEqualTo(Side.CHO);
        assertThat(janggiGame.gameResult()).isEqualTo(GameResult.running());
        assertThat(janggiGame.board()).isEqualTo(choBoard.merge(hanBoard));
    }

}
