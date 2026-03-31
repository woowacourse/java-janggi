package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        BoardSetUp choSetUp = (side) -> Map.of(
                Point.of(0, 0), new Soldier(Side.CHO)
        );
        BoardSetUp hanSetUp = (side) -> Map.of(
                Point.of(3, 0), new Soldier(Side.HAN)
        );
        game = Game.createGame(choSetUp, hanSetUp);
    }

    @Test
    @DisplayName("move(): 자신의 진영이 아닌 기물을 이동시키면 예외가 발생한다")
    void notTurn() {
        assertThatThrownBy(() -> game.move(Point.of(3, 0), Point.of(4, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("move(): 이동을 시킨 후 차례가 변경된다.")
    void switchTurn() {
        game.move(Point.of(0, 0), Point.of(1, 0));
        assertThatThrownBy(() -> game.move(Point.of(1, 0), Point.of(2, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
