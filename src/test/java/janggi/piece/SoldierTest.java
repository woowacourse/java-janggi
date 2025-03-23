package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.board.Position;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    @ParameterizedTest
    @DisplayName("BLUE 병사가 위 혹은 좌우로 움직이지 않으면 false를 반환한다.")
    @CsvSource(value = {
            "5, 4",
            "6, 6",
            "4, 6",
            "3, 5",
            "7, 5"
    })
    void shouldFalseWhenBlueSide(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Side.BLUE);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = soldier.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isFalse();
    }

    @ParameterizedTest
    @DisplayName("RED 병사가 아래 혹은 좌우로 움직이지 않으면 false를 반환한다.")
    @CsvSource(value = {
            "5, 6",
            "6, 4",
            "4, 4",
            "3, 5",
            "7, 5"
    })
    void shouldFalseWhenRedSide(int destX, int destY) {
        // given
        Soldier soldier = new Soldier(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = soldier.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isFalse();
    }
}
