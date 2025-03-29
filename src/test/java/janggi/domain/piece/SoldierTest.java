package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Team;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SoldierTest {

    Map<Position, Piece> pieces;

    @BeforeEach
    void setUp() {
        pieces = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                pieces.put(new Position(i, j), new None(new Position(i, j)));
            }
        }
    }

    @DisplayName("이동 위치 값을 입력 받아 이동한다.")
    @Test
    void move() {
        Soldier soldier = new Soldier(new Position(10, 4), Team.BLUE);
        Position positionToMove = new Position(9, 4);

        Piece movedSoldier = soldier.move(pieces, positionToMove);
        assertThat(movedSoldier.getPosition()).isEqualTo(positionToMove);
    }

    @DisplayName("청졸의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"6,5", "5,7"})
    @ParameterizedTest
    void move2(int x, int y) {
        Soldier soldier = new Soldier(new Position(5, 5), Team.BLUE);
        Position positionToMove = new Position(x, y);
        assertThatThrownBy(() -> soldier.move(pieces, positionToMove))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("홍졸의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"4,5", "5,7"})
    @ParameterizedTest
    void move3(int x, int y) {
        Soldier soldier = new Soldier(new Position(5, 5), Team.RED);
        Position positionToMove = new Position(x, y);
        assertThatThrownBy(() ->
            soldier.move(pieces, positionToMove))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("청졸은 홍팀의 궁성에서 간선을 타고 이동이 가능하다")
    @CsvSource(value = {"3, 4, 2, 5", "3, 6, 2, 5", "2, 5, 1, 4", "2, 5, 2, 6"})
    @ParameterizedTest
    void move4(int soldierX, int soldierY, int targetX, int targetY) {
        Soldier soldier = new Soldier(new Position(soldierX, soldierY), Team.BLUE);
        Piece movedSoldier = soldier.move(pieces, new Position(targetX, targetY));
        assertThat(movedSoldier.getPosition()).isEqualTo(new Position(targetX, targetY));
    }

    @DisplayName("간선이 없는 곳에서는 대각 이동이 불가능하다")
    @CsvSource(value = {"3, 4, 2, 3", "3, 6, 2, 7"})
    @ParameterizedTest
    void move5(int soldierX, int soldierY, int targetX, int targetY) {
        Soldier soldier = new Soldier(new Position(soldierX, soldierY), Team.BLUE);
        assertThatThrownBy(
            () -> soldier.move(pieces, new Position(targetX, targetY))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
