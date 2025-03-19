package domain;

import static org.assertj.core.api.Assertions.assertThat;

import fixture.BoardFixture;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("궁 1개, 차 2개, 포 2개, 마 2개, 상 2개, 사 2개, 졸 5개를 갖는다")
    void test() {
        // given & when
        HanBoard hanBoard = HanBoard.createWithPieces();
        Map<Location, Piece> hanPieces = (Map<Location, Piece>)BoardFixture.createHanPieces();

        //when
        Map<Location, Piece> pieces = (Map<Location, Piece>)hanBoard.getPieces();

        // then
        assertThat(pieces).containsExactlyInAnyOrderEntriesOf(hanPieces);
    }

    List<Location> horseLocations = List.of(new Location(2,1), new Location(8,1));
    List<Location> elephantLocations = List.of(new Location(3,1), new Location(7,1));
}
