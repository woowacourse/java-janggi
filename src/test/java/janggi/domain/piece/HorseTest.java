package janggi.domain.piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HorseTest {
    Map<Position, Piece> pieces;
    @BeforeEach
    void setUp() {
        pieces = new HashMap<>();
        for(int i = 1; i <= 10; i ++) {
            for(int j = 1; j <= 9; j ++) {
                pieces.put(new Position(i, j), new None());
            }
        }
    }

    @DisplayName("이동 위치 값을 입력 받아 이동한다.")
    @Test
    void move() {
        Horse horse = new Horse(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(3, 4);
        Horse movedHorse = horse.move(pieces, positionToMove);
        assertThat(movedHorse.getPosition()).isEqualTo(positionToMove);
    }

    @DisplayName("마의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,7", "4,4", "6,5"})
    @ParameterizedTest
    void move2(int x, int y) {
        Horse horse = new Horse(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(x, y);
        assertThatThrownBy(() -> horse.move(pieces, positionToMove))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("마의 초기 위치와 이동 위치 사이에 기물이 존재하는 경우 예외를 던진다.")
    @Test
    void move3() {
        Horse horse = new Horse(new Position(5, 5), TeamType.BLUE);
        Soldier otherSoldier = new Soldier(new Position(4, 5), TeamType.BLUE);
        pieces.put(otherSoldier.getPosition(), otherSoldier);
        assertThatThrownBy(() ->
                horse.move(pieces, new Position(3, 4)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("마의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move4() {
        Horse horse = new Horse(new Position(5, 5), TeamType.BLUE);
        Soldier otherSoldier = new Soldier(new Position(3, 5), TeamType.BLUE);
        pieces.put(otherSoldier.getPosition(), otherSoldier);
        assertThatThrownBy(() ->
                horse.move(pieces, otherSoldier.getPosition()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("마의 모든 이동 경로가 가능하다")
    @CsvSource(value = {
            "3,4",
            "3,6",
            "4,3",
            "4,7",
            "6,3",
            "6,7",
            "7,4",
            "7,6",
    })
    @ParameterizedTest
    void move6(int x, int y) {
        Horse horse = new Horse(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(x, y);
        Horse movedHorse = horse.move(pieces, positionToMove);
        assertThat(movedHorse.getPosition()).isEqualTo(positionToMove);
    }
}
