package domain;

import static org.assertj.core.api.Assertions.assertThat;

import fixture.BoardFixture;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("궁 1개, 차 2개, 포 2개, 마 2개, 상 2개, 사 2개, 졸 5개를 갖는다")
    void test() {
        // given & when
        Location horseLocation1 = new Location(2, 1);
        Location horseLocation2 = new Location(8,1);
        Location elephantLocation1 = new Location(3,1);
        Location elephantLocation2 = new Location(7,1);
        List<Location> horseLocations = List.of(horseLocation1, horseLocation2);
        List<Location> elephantLocations = List.of(elephantLocation1, elephantLocation2);
        HanBoard hanBoard = HanBoard.createWithPieces(horseLocations, elephantLocations);
        Map<Location, Piece> hanPieces = BoardFixture.createHanPieces(horseLocations, elephantLocations);

        //when
        Map<Location, Piece> pieces = hanBoard.getPieces();

        // then
        assertThat(pieces).containsExactlyInAnyOrderEntriesOf(hanPieces);
    }

    @Test
    @DisplayName("마와 상의 위치는 입력값으로 정한다")
    void test2() {
        // given & when
        Location horseLocation1 = new Location(2, 1);
        Location horseLocation2 = new Location(8,1);
        Location elephantLocation1 = new Location(3,1);
        Location elephantLocation2 = new Location(7,1);
        List<Location> horseLocations = List.of(horseLocation1, horseLocation2);
        List<Location> elephantLocations = List.of(elephantLocation1, elephantLocation2);

        //when
        Map<Location, Piece> hanPieces = BoardFixture.createHanPieces(horseLocations, elephantLocations);

        // then
        Piece horsePiece1 = hanPieces.get(horseLocation1);
        Piece horsePiece2 = hanPieces.get(horseLocation2);
        Piece elephantPiece1 = hanPieces.get(elephantLocation1);
        Piece elephantPiece2 = hanPieces.get(elephantLocation2);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat((Horse)horsePiece1).isInstanceOf(Horse.class);
            softly.assertThat((Horse)horsePiece2).isInstanceOf(Horse.class);
            softly.assertThat((Elephant)elephantPiece1).isInstanceOf(Elephant.class);
            softly.assertThat((Elephant)elephantPiece2).isInstanceOf(Elephant.class);
        });
    }
}
