package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CannonTest {

    @Test
    @DisplayName("포 이동 가능 후보군 리턴 테스트")
    void test1() {
        Cannon cannon = new Cannon(Side.CHO);
        JanggiBoard board = JanggiBoard.initialize();

        List<Position> destinations = cannon.filterReachableDestinations(new Position(1, 7), board);

        assertAll(
                () -> assertThat(destinations).isEmpty()
        );
    }

}
