package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChariotTest {
    Map<Position, Piece> board;
    Position beforePosition = new Position(5, 5);

    @BeforeEach
    void setUp() {
        board = new HashMap<>();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 9; j++) {
                board.put(new Position(i, j), new None());
            }
        }
    }

    @DisplayName("이동 위치 값을 입력 받아 이동한다.")
    @Test
    void move() {
        Chariot chariot = new Chariot(Team.BLUE);
        Position afterPosition = new Position(5, 9);
        Soldier soldier = new Soldier(Team.BLUE);
        board.put(new Position(5, 5), soldier);
        assertThatCode(() ->
                chariot.getMovableValidator(beforePosition, afterPosition).accept(board)).doesNotThrowAnyException();
    }

    @DisplayName("차의 이동 위치 값이 불가능한 값인 경우 예외를 던진다.")
    @CsvSource(value = {"7,7", "4,4"})
    @ParameterizedTest
    void move2(int x, int y) {
        Chariot chariot = new Chariot(Team.BLUE);
        Position afterPosition = new Position(x, y);
        assertThatThrownBy(() -> chariot.getMovableValidator(beforePosition, afterPosition).accept(board))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("차의 초기 위치와 이동 위치 사이에 말이 존재하는 경우 예외를 던진다.")
    @Test
    void move3() {
        Chariot chariot = new Chariot(Team.BLUE);
        Soldier otherSoldier = new Soldier(Team.BLUE);
        Position afterPosition = new Position(2, 5);
        board.put(new Position(3, 5), otherSoldier);
        assertThatThrownBy(() -> chariot.getMovableValidator(beforePosition, afterPosition).accept(board))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("차의 이동 위치에 같은 편 기물이 있으면 예외를 던진다")
    @Test
    void move4() {
        Chariot chariot = new Chariot(Team.BLUE);
        Soldier otherSoldier = new Soldier(Team.BLUE);
        Position afterPosition = new Position(2, 5);
        board.put(afterPosition, otherSoldier);
        assertThatThrownBy(() -> chariot.getMovableValidator(beforePosition, afterPosition).accept(board))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
