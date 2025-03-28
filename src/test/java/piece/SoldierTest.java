package piece;

import board.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import position.LineDirection;
import position.Position;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SoldierTest {

    @DisplayName("Soldier는 팀에 따라 이동 방향이 바뀐다.")
    @Test
    void moveActions() {
        // given

        // when

        // then
    }

    @DisplayName("Soldier는 뒷 방향을 제외하고 한 칸을 이동할 수 있다.")
    @Test
    void isAbleToMoveByDirection() {

        // given
        final Position src = new Position(2, 3);
        final Country country = Country.HAN;
        Country.assignDirection(country, LineDirection.UP);
        final Piece soldier = new Soldier(src, country);
        final Board board = new Board(Map.of(
                src, soldier
        ));

        // when & then: 1 : success
        final Position ableDest1 = new Position(2, 2);
        assertThatCode(
                () -> soldier.validateMove(src, ableDest1, board)
        ).doesNotThrowAnyException();

        // when & then : 2 : failure
        final Position ableDest2 = new Position(2, 4);
        assertThatThrownBy(
                () -> soldier.validateMove(src, ableDest2, board)
        ).isInstanceOf(IllegalArgumentException.class);

        // when & then: 1 : success
        final Position ableDest3 = new Position(3, 3);
        assertThatCode(
                () -> soldier.validateMove(src, ableDest3, board)
        ).doesNotThrowAnyException();

        // when & then: 1 : success
        final Position ableDest4 = new Position(1, 3);
        assertThatCode(
                () -> soldier.validateMove(src, ableDest4, board)
        ).doesNotThrowAnyException();
    }
}
