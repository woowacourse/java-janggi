package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotTest {
    Map<Position, Piece> pieces;

    @BeforeEach
    void setUp() {
        pieces = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                pieces.put(new Position(i, j), new None());
            }
        }
    }

    @DisplayName("이동 위치 값을 입력 받아 이동한다.")
    @Test
    void move() {
        Chariot chariot = new Chariot(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(5, 10);
        Soldier soldier = new Soldier(new Position(1, 1), TeamType.BLUE);
        pieces.put(soldier.getPosition(), soldier);
        Chariot movedChariot = chariot.move(pieces, positionToMove);
        assertThat(movedChariot.getPosition()).isEqualTo(positionToMove);
    }

    @DisplayName("차의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,7", "4,4"})
    @ParameterizedTest
    void move2(int x, int y) {
        Chariot chariot = new Chariot(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(x, y);
        assertThatThrownBy(() -> chariot.move(pieces, positionToMove))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("차의 초기 위치와 이동 위치 사이에 말이 존재하는 경우 예외를 던진다.")
    @Test
    void move3() {
        Chariot chariot = new Chariot(new Position(5, 5), TeamType.BLUE);
        Soldier otherSoldier = new Soldier(new Position(3, 5), TeamType.BLUE);
        pieces.put(otherSoldier.getPosition(), otherSoldier);
        assertThatThrownBy(() ->
                chariot.move(pieces, new Position(1, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("차의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move4() {
        Chariot chariot = new Chariot(new Position(5, 5), TeamType.BLUE);
        Soldier otherSoldier = new Soldier(new Position(4, 5), TeamType.BLUE);
        pieces.put(otherSoldier.getPosition(), otherSoldier);
        assertThatThrownBy(() ->
                chariot.move(pieces, otherSoldier.getPosition()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
