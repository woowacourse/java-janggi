package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Coordinate;
import domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JolTest {

    @Test
    @DisplayName("졸이 움직이면 좌표가 변경된다.")
    void test1() {
        // given
        Jol jol = new Jol(new Piece(new Coordinate(2, 1), Team.CHO));

        // when
        Jol movedJol = jol.move(new Coordinate(3, 1));

        // then
        assertThat(movedJol).isEqualTo(new Jol(new Piece(new Coordinate(3, 1), Team.CHO)));
    }
}
