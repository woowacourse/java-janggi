package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Piece;
import fixture.BoardFixture;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChoBoardTest {
    @Test
    @DisplayName("초나라는 궁 1개, 차 2개, 포 2개, 마 2개, 상 2개, 사 2개, 졸 5개를 갖는다")
    void test3() {
        // given & when
        BoardLocation horseBoardLocation1 = new BoardLocation(2, 10);
        BoardLocation horseBoardLocation2 = new BoardLocation(8,10);
        BoardLocation elephantBoardLocation1 = new BoardLocation(3,10);
        BoardLocation elephantBoardLocation2 = new BoardLocation(7,10);
        List<BoardLocation> horseBoardLocations = List.of(horseBoardLocation1, horseBoardLocation2);
        List<BoardLocation> elephantBoardLocations = List.of(elephantBoardLocation1, elephantBoardLocation2);
        ChoBoard choBoardTest = ChoBoard.createWithPieces(horseBoardLocations, elephantBoardLocations);
        Map<BoardLocation, Piece> choPieces = BoardFixture.createChoPieces(horseBoardLocations, elephantBoardLocations);

        //when
        Map<BoardLocation, Piece> pieces = choBoardTest.getPieces();

        // then
        assertThat(pieces).containsExactlyInAnyOrderEntriesOf(choPieces);
    }

    @Test
    @DisplayName("초나라의 마와 상의 위치는 입력값으로 정한다")
    void test4() {
        // given & when
        BoardLocation horseBoardLocation1 = new BoardLocation(2, 10);
        BoardLocation horseBoardLocation2 = new BoardLocation(8,10);
        BoardLocation elephantBoardLocation1 = new BoardLocation(3,10);
        BoardLocation elephantBoardLocation2 = new BoardLocation(7,10);
        List<BoardLocation> horseBoardLocations = List.of(horseBoardLocation1, horseBoardLocation2);
        List<BoardLocation> elephantBoardLocations = List.of(elephantBoardLocation1, elephantBoardLocation2);

        //when
        Map<BoardLocation, Piece> choPieces = BoardFixture.createChoPieces(horseBoardLocations, elephantBoardLocations);

        // then
        Piece horsePiece1 = choPieces.get(horseBoardLocation1);
        Piece horsePiece2 = choPieces.get(horseBoardLocation2);
        Piece elephantPiece1 = choPieces.get(elephantBoardLocation1);
        Piece elephantPiece2 = choPieces.get(elephantBoardLocation2);

//        SoftAssertions.assertSoftly(softly -> {
//            softly.assertThat((Horse)horsePiece1).isInstanceOf(Horse.class);
//            softly.assertThat((Horse)horsePiece2).isInstanceOf(Horse.class);
//            softly.assertThat((Elephant)elephantPiece1).isInstanceOf(Elephant.class);
//            softly.assertThat((Elephant)elephantPiece2).isInstanceOf(Elephant.class);
//        });
    }

}
