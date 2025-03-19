package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {
    @DisplayName("이동 위치 값을 입력 받아 이동한다.")
    @Test
    void move() {
        Soldier soldier = new Soldier(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(5, 6);
        Soldier movedSoldier = soldier.move(positionToMove);

        assertThat(movedSoldier.getPosition()).isEqualTo(positionToMove);
    }

    @DisplayName("청졸의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"6,5", "5,7"})
    @ParameterizedTest
    void move2(int x, int y) {
        Soldier soldier = new Soldier(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(x, y);

        assertThatThrownBy(() -> soldier.move(positionToMove))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("홍졸의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"4,5", "5,7"})
    @ParameterizedTest
    void move3(int x, int y) {
        Soldier soldier = new Soldier(new Position(5, 5), TeamType.RED);
        Position positionToMove = new Position(x, y);

        assertThatThrownBy(() -> soldier.move(positionToMove))
                .isInstanceOf(IllegalArgumentException.class);
    }
}