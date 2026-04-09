package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void 왕이_잡히면_게임이_종료된다() {
        // given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 4);
        Board board = new Board(() -> Map.of(
                source, new Piece(PieceType.CHARIOT, Camp.CHO),
                destination, new Piece(PieceType.GENERAL, Camp.HAN)
        ));
        Game game = Game.newGame(board);

        // when
        boolean gameEnded = game.play(source, destination);

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(gameEnded).isTrue();
            assertSoftly.assertThat(game.currentTurn()).isEqualTo(Camp.CHO);
        });
    }

    @Test
    void 왕이_아닌_기물이_잡히면_게임이_종료되지_않는다() {
        // given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 4);
        Board board = new Board(() -> Map.of(
                source, new Piece(PieceType.CHARIOT, Camp.CHO),
                destination, new Piece(PieceType.HORSE, Camp.HAN)
        ));
        Game game = Game.newGame(board);

        // when
        boolean gameEnded = game.play(source, destination);

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(gameEnded).isFalse();
            assertSoftly.assertThat(game.currentTurn()).isEqualTo(Camp.HAN);
        });
    }

    @Test
    void 기물이_잡히지_않으면_게임이_종료되지_않는다() {
        // given
        Position source = new Position(0, 0);
        Position destination = new Position(0, 4);
        Board board = new Board(() -> Map.of(
                source, new Piece(PieceType.CHARIOT, Camp.CHO)
        ));
        Game game = Game.newGame(board);

        // when
        boolean gameEnded = game.play(source, destination);

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(gameEnded).isFalse();
            assertSoftly.assertThat(game.currentTurn()).isEqualTo(Camp.HAN);
        });
    }

    @Test
    void 각_진영별_보너스_점수를_더한_최종_점수를_계산한다() {
        // given
        Board board = new Board(() -> Map.of(
                new Position(1, 4), new Piece(PieceType.GENERAL, Camp.CHO),
                new Position(0, 0), new Piece(PieceType.CHARIOT, Camp.CHO),
                new Position(0, 8), new Piece(PieceType.CHARIOT, Camp.CHO),

                new Position(4, 1), new Piece(PieceType.SOLDIER, Camp.HAN),
                new Position(7, 1), new Piece(PieceType.CANNON, Camp.HAN),
                new Position(7, 7), new Piece(PieceType.CANNON, Camp.HAN)
        ));
        Game game = Game.newGame(board);

        // when
        Map<Camp, Double> score = game.calculateScore();

        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(score.get(Camp.CHO)).isEqualTo(26);
            assertSoftly.assertThat(score.get(Camp.HAN)).isEqualTo(17.5);
        });
    }
}
