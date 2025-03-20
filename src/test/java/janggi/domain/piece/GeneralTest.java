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

class GeneralTest {
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
        General general = new General(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(5, 6);

        General movedGeneral = general.move(pieces, positionToMove);

        assertThat(movedGeneral.getPosition()).isEqualTo(positionToMove);
    }

    @DisplayName("궁의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,5", "5,7"})
    @ParameterizedTest
    void move2(int x, int y) {
        General general = new General(new Position(5, 5), TeamType.BLUE);
        Position positionToMove = new Position(x, y);
        assertThatThrownBy(() -> general.move(pieces, positionToMove))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("궁의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move4() {
        General general = new General(new Position(5, 5), TeamType.BLUE);
        Soldier otherSoldier = new Soldier(new Position(4, 5), TeamType.BLUE);
        pieces.put(otherSoldier.getPosition(), otherSoldier);
        assertThatThrownBy(() ->
                general.move(pieces, otherSoldier.getPosition()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
