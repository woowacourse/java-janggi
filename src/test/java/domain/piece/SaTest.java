package domain.piece;

import domain.Coordinate;
import domain.Team;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SaTest {

    @Test
    @DisplayName("사가 움직이면 좌표가 변경된다.")
    void test1() {
        // given
        Sa sa = new Sa(new Piece(new Coordinate(5, 1), Team.CHO));

        // when
        Sa movedSa = sa.move(new Coordinate(6, 1));

        // then
        AssertionsForClassTypes.assertThat(movedSa).isEqualTo(new Sa(new Piece(new Coordinate(6, 1), Team.CHO)));
    }
}
