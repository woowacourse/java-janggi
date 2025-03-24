package janggiGame;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.arrangement.InnerElephantStrategy;
import janggiGame.board.Board;
import janggiGame.board.Dot;
import janggiGame.piece.Dynasty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiGameTest {

    @DisplayName("입력받은 위치에 기물이 없다면 예외를 발생시킨다")
    @Test
    void validateEmptySpace() {
        // given
        JanggiGame janggiGame = new JanggiGame();
        janggiGame.arrangeChoPieces(new InnerElephantStrategy());

        Dynasty dynasty = Dynasty.CHO;
        Dot origin = Board.findBy(0, 2);
        Dot destination = Board.findBy(1, 4);

        // when // then
        assertThatCode(() -> janggiGame.processTurn(dynasty, origin, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("입력받은 위치의 기물이 다른 편이라면 예외를 발생시킨다")
    @Test
    void validatePieceDynasty() {
        // given
        JanggiGame janggiGame = new JanggiGame();
        janggiGame.arrangeChoPieces(new InnerElephantStrategy());

        Dynasty dynasty = Dynasty.HAN;
        Dot origin = Board.findBy(0, 0);
        Dot destination = Board.findBy(0, 1);

        // when // then
        assertThatCode(() -> janggiGame.processTurn(dynasty, origin, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("말이 이동했다면 원래의 위치는 비어있고 새로운 위치에 말이 들어간다")
    @Test
    void checkStatusAfterMoving() {
        // given
        JanggiGame janggiGame = new JanggiGame();
        janggiGame.arrangeChoPieces(new InnerElephantStrategy());

        Dynasty dynasty = Dynasty.CHO;
        Dot origin = Board.findBy(0, 0);
        Dot destination = Board.findBy(0, 1);

        janggiGame.processTurn(dynasty, origin, destination);
        // when
        boolean originActual = janggiGame.getPieces().containsKey(origin);
        boolean destinationActual = janggiGame.getPieces().containsKey(destination);

        // then
        assertThat(originActual).isFalse();
        assertThat(destinationActual).isTrue();
    }
}
