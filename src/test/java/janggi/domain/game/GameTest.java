package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.board.setup.InElephantSetUp;
import janggi.domain.point.Point;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GameTest {
    public static Stream<Arguments> isTurnPiece() {
        return Stream.of(
                Arguments.of(new Point(0, 0), true),
                Arguments.of(new Point(9, 0), false)
        );
    }

    @ParameterizedTest
    @DisplayName("from에 있는 기물이 turn과 같은 Side라면 기물을 움직일 수 있다.")
    @MethodSource
    void isTurnPiece(Point point, boolean expected) {
        BoardSetUp boardSetUp = new InElephantSetUp();
        Game game = Game.createGame(boardSetUp, boardSetUp);

        assertThat(game.isTurnPiece(point)).isEqualTo(expected);
    }

    @Test
    @DisplayName("제자리 이동을 하면 턴을 넘긴다.")
    void move() {
        Point choPoint = new Point(0, 0);
        BoardSetUp boardSetUp = new InElephantSetUp();
        Game game = Game.createGame(boardSetUp, boardSetUp);

        game.move(choPoint, choPoint);

        assertThat(game.isTurnPiece(choPoint)).isEqualTo(false);
    }
}
