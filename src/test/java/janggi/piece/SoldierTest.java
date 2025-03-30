package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierTest {

    @Test
    @DisplayName("현재 위치를 받아와 움직일 수 있는 위치 후보군을 반환한다 - 초나라")
    void computeCandidateRoutes() {

        Soldier soldier = new Soldier(Side.CHO);
        JanggiBoard board = JanggiBoard.initialize();

        List<Position> destinations = soldier.filterReachableDestinations(new Position(0, 6), board);

        assertAll(
                () -> assertThat(destinations).hasSize(2),
                () -> assertThat(destinations.getFirst()).isEqualTo(new Position(1, 6))
        );
    }

    @Test
    @DisplayName("현재 위치를 받아와 움직일 수 있는 위치 후보군을 반환한다 - 한나라")
    void computeCandidateRoutes2() {

        Soldier soldier = new Soldier(Side.HAN);
        JanggiBoard board = JanggiBoard.initialize();

        List<Position> destinations = soldier.filterReachableDestinations(new Position(2, 3), board);

        assertAll(
                () -> assertThat(destinations).hasSize(3),
                () -> assertThat(destinations.getFirst()).isEqualTo(new Position(1, 3))
        );
    }

}
