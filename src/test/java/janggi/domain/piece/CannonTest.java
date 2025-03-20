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

class CannonTest {
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
        Cannon cannon = new Cannon(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(2, 5);
        Soldier otherSoldier1 = new Soldier(new Position(3, 5), TeamType.BLUE);
        Soldier otherSoldier2 = new Soldier(new Position(1, 1), TeamType.BLUE);
        pieces.put(otherSoldier1.getPosition(), otherSoldier1);
        pieces.put(otherSoldier2.getPosition(), otherSoldier2);
        Cannon movedHorse = cannon.move(pieces, positionToMove);
        assertThat(movedHorse.getPosition()).isEqualTo(positionToMove);
    }

    @DisplayName("포의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,7", "4,4", "6,5"})
    @ParameterizedTest
    void move2(int x, int y) {
        Cannon cannon = new Cannon(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(x, y);
        assertThatThrownBy(() -> cannon.move(pieces, positionToMove))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포의 초기 위치와 이동 위치 사이에 포가 존재하는 경우 예외를 던진다.")
    @Test
    void move3() {
        Cannon cannon = new Cannon(new Position(5, 5), TeamType.BLUE);
        Cannon otherCannon = new Cannon(new Position(3, 5), TeamType.BLUE);
        pieces.put(otherCannon.getPosition(), otherCannon);
        assertThatThrownBy(() ->
                cannon.move(pieces, new Position(2, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move4() {
        Cannon cannon = new Cannon(new Position(5, 5), TeamType.BLUE);
        Soldier otherSoldier = new Soldier(new Position(3, 5), TeamType.BLUE);
        pieces.put(otherSoldier.getPosition(), otherSoldier);
        assertThatThrownBy(() ->
                cannon.move(pieces, otherSoldier.getPosition()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포의 이동 위치에 상대편 포가 있으면 예외를 던진다")
    @Test
    void move5() {
        Cannon cannon = new Cannon(new Position(5, 5), TeamType.BLUE);
        Cannon otherCannon = new Cannon(new Position(3, 5), TeamType.RED);
        pieces.put(otherCannon.getPosition(), otherCannon);
        assertThatThrownBy(() ->
                cannon.move(pieces, otherCannon.getPosition()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포의 모든 이동 경로가 가능하다")
    @CsvSource(value = {
            "2,5",
            "5,2",
            "7,5",
            "5,7"
    })
    @ParameterizedTest
    void move6(int x, int y) {
        Cannon cannon = new Cannon(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(x, y);

        Soldier soldier1 = new Soldier(new Position(3, 5), TeamType.BLUE);
        Soldier soldier2 = new Soldier(new Position(5, 3), TeamType.BLUE);
        Soldier soldier3 = new Soldier(new Position(6, 5), TeamType.BLUE);
        Soldier soldier4 = new Soldier(new Position(5, 6), TeamType.BLUE);
        pieces.put(soldier1.getPosition(), soldier1);
        pieces.put(soldier2.getPosition(), soldier2);
        pieces.put(soldier3.getPosition(), soldier3);
        pieces.put(soldier4.getPosition(), soldier4);

        Cannon movedHorse = cannon.move(pieces, positionToMove);
        assertThat(movedHorse.getPosition()).isEqualTo(positionToMove);
    }
}
