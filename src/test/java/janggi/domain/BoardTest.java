package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class BoardTest {

    @ParameterizedTest
    @CsvSource({

            "CHO,KING,5,9",
            "CHO,SA,4,10","CHO,SA,6,10",
            "CHO,SANG,3,10","CHO,SANG,8,10",
            "CHO,MA,2,10","CHO,MA,7,10",
            "CHO,CHA,1,10","CHO,CHA,9,10",
            "CHO,PO,2,8","CHO,PO,8,8",
            "CHO,ZOL,1,7", "CHO,ZOL,3,7", "CHO,ZOL,5,7", "CHO,ZOL,7,7", "CHO,ZOL,9,7",

            "HAN,KING,5,2",
            "HAN,SA,4,1","HAN,SA,6,1",
            "HAN,SANG,3,1","HAN,SANG,8,1",
            "HAN,MA,2,1","HAN,MA,7,1",
            "HAN,CHA,1,1","HAN,CHA,9,1",
            "HAN,PO,2,3","HAN,PO,8,3",
            "HAN,ZOL,1,4", "HAN,ZOL,3,4", "HAN,ZOL,5,4", "HAN,ZOL,7,4", "HAN,ZOL,9,4"

    })
    @DisplayName("초기 위치에 각나라 졸이 있다.")
    void 초기_위치에_각나라_졸_세팅(Team team, PieceType pieceType, int x, int y){
        //given
        Board board = new Board();
        Position position = new Position(x,y);
        Piece zol = new Piece(team,pieceType);

        //when
        Map<Position, Piece> checkZol = board.getBoard();

        //then
        assertThat(checkZol.get(position)).isEqualTo(zol);
    }
}
