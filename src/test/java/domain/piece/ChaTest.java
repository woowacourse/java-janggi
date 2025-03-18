package domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Coordinate;
import domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChaTest {

    @Test
    @DisplayName("차가 움직이면 좌표가 변경된다.")
    void test2() {
        // given
        Cha cha = new Cha(new Piece(new Coordinate(1, 1), Team.CHO));

        // when
        Cha movedCha = cha.move(new Coordinate(9, 1));

        // then
        assertThat(movedCha).isEqualTo(new Cha(new Piece(new Coordinate(9, 1), Team.CHO)));
    }
}
