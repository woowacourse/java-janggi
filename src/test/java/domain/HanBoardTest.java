package domain;

import static org.assertj.core.api.Assertions.assertThat;

import fixture.BoardFixture;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HanBoardTest {

    @Test
    @DisplayName("한나라는 궁 1개, 차 2개, 포 2개, 마 2개, 상 2개, 사 2개, 졸 5개를 갖는다")
    void test() {
        // given & when
        BoardLocation horseBoardLocation1 = new BoardLocation(2, 1);
        BoardLocation horseBoardLocation2 = new BoardLocation(8,1);
        BoardLocation elephantBoardLocation1 = new BoardLocation(3,1);
        BoardLocation elephantBoardLocation2 = new BoardLocation(7,1);
        List<BoardLocation> horseBoardLocations = List.of(horseBoardLocation1, horseBoardLocation2);
        List<BoardLocation> elephantBoardLocations = List.of(elephantBoardLocation1, elephantBoardLocation2);
        HanBoard hanBoard = HanBoard.createWithPieces(horseBoardLocations, elephantBoardLocations);
        Map<BoardLocation, Piece> hanPieces = BoardFixture.createHanPieces(horseBoardLocations, elephantBoardLocations);

        //when
        Map<BoardLocation, Piece> pieces = hanBoard.getPieces();

        // then
        assertThat(pieces).containsExactlyInAnyOrderEntriesOf(hanPieces);
    }

    @Test
    @DisplayName("한나라의 마와 상의 위치는 입력값으로 정한다")
    void test2() {
        // given & when
        BoardLocation horseBoardLocation1 = new BoardLocation(2, 1);
        BoardLocation horseBoardLocation2 = new BoardLocation(8,1);
        BoardLocation elephantBoardLocation1 = new BoardLocation(3,1);
        BoardLocation elephantBoardLocation2 = new BoardLocation(7,1);
        List<BoardLocation> horseBoardLocations = List.of(horseBoardLocation1, horseBoardLocation2);
        List<BoardLocation> elephantBoardLocations = List.of(elephantBoardLocation1, elephantBoardLocation2);

        //when
        Map<BoardLocation, Piece> hanPieces = BoardFixture.createHanPieces(horseBoardLocations, elephantBoardLocations);

        // then
        Piece horsePiece1 = hanPieces.get(horseBoardLocation1);
        Piece horsePiece2 = hanPieces.get(horseBoardLocation2);
        Piece elephantPiece1 = hanPieces.get(elephantBoardLocation1);
        Piece elephantPiece2 = hanPieces.get(elephantBoardLocation2);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat((Horse)horsePiece1).isInstanceOf(Horse.class);
            softly.assertThat((Horse)horsePiece2).isInstanceOf(Horse.class);
            softly.assertThat((Elephant)elephantPiece1).isInstanceOf(Elephant.class);
            softly.assertThat((Elephant)elephantPiece2).isInstanceOf(Elephant.class);
        });
    }
}
