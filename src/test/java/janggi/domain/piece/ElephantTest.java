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

class ElephantTest {
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
        Elephant elephant = new Elephant(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(2, 3);
        Elephant movedHorse = elephant.move(pieces, positionToMove);
        assertThat(movedHorse.getPosition()).isEqualTo(positionToMove);
    }

    @DisplayName("상의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,7", "4,4", "6,5"})
    @ParameterizedTest
    void move2(int x, int y) {
        Elephant elephant = new Elephant(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(x, y);
        assertThatThrownBy(() -> elephant.move(pieces, positionToMove))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상의 초기 위치와 이동 위치 사이에 기물이 존재하는 경우 예외를 던진다.")
    @Test
    void move3() {
        Elephant elephant = new Elephant(new Position(5, 5), TeamType.BLUE);
        Soldier otherSoldier = new Soldier(new Position(3, 4), TeamType.BLUE);
        pieces.put(otherSoldier.getPosition(), otherSoldier);
        assertThatThrownBy(() ->
                elephant.move(pieces, new Position(2, 3)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move4() {
        Elephant elephant = new Elephant(new Position(5, 5), TeamType.BLUE);
        Soldier otherSoldier = new Soldier(new Position(3, 5), TeamType.BLUE);
        pieces.put(otherSoldier.getPosition(), otherSoldier);
        assertThatThrownBy(() ->
                elephant.move(pieces, otherSoldier.getPosition()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상의 모든 이동 경로가 가능하다")
    @CsvSource(value = {
            "2,3",
            "2,7",
            "8,7",
            "8,3",
            "7,2",
            "7,8",
            "3,2",
            "3,8",
    })
    @ParameterizedTest
    void move6(int x, int y) {
        Elephant elephant = new Elephant(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(x, y);
        Elephant movedHorse = elephant.move(pieces, positionToMove);
        assertThat(movedHorse.getPosition()).isEqualTo(positionToMove);
    }
}