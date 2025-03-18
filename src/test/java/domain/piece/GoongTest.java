package domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Coordinate;
import domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GoongTest {

    @Test
    @DisplayName("궁이 움직이면 좌표가 변경된다.")
    void test1() {
        // given
        Goong goong = new Goong(new Piece(new Coordinate(2, 1), Team.CHO));

        // when
        Goong movedGoong = goong.move(new Coordinate(3, 2));

        // then
        assertThat(movedGoong).isEqualTo(new Goong(new Piece(new Coordinate(3, 2), Team.CHO)));
    }

}
