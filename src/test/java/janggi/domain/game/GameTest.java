package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        BoardSetUp choSetUp = (side) -> Map.of(
                Point.of(0, 0), new Soldier(Side.CHO),
                Point.of(5, 0), new Soldier(Side.CHO)
        );
        BoardSetUp hanSetUp = (side) -> Map.of(
                Point.of(3, 0), new Soldier(Side.HAN),
                Point.of(6, 0), new General(Side.HAN)
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
        assertThat(game.getTurn()).isEqualTo(Side.HAN);
    }

    @Test
    @DisplayName("move(): 적 진영의 궁을 잡으면 이긴다.")
    void checkGeneral() {
        assertThat(game.move(Point.of(5, 0), Point.of(6, 0)).getWinner()).isEqualTo(Side.CHO);
    }

    @Test
    @DisplayName("getScore(): 기물 점수의 합을 반환한다.")
    void getScore_CHO_Side() {
        // CHO : 졸(2) + 졸(2) = 4.0
        assertThat(game.getScore(Side.CHO)).isEqualTo(4.0);
    }

    @Test
    @DisplayName("getScore(): 기물 점수의 합을 반환한다. (HAN 진영은 보너스 1.5점)")
    void getScore_HAN_Side() {
        // HAN : 병(2) + 궁(0) + 보너스(1.5) = 3.5
        assertThat(game.getScore(Side.HAN)).isEqualTo(3.5);
    }
}
