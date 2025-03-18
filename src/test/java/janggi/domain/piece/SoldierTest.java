package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SoldierTest {

    @DisplayName("기물이 움직이는 지 확인한다.")
    @Test
    void moveTest() {

        // given
        Piece soldier = new Soldier(new Position(1, 1), Team.RED);
        Position newPosition = new Position(2, 2);

        // when
        soldier.move(newPosition);

        // then
        assertThat(soldier.isSamePosition(newPosition)).isTrue();
    }
}
