package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class BoardInitiatorTest {
    @ParameterizedTest
    @CsvSource({

            "CHO,KING,5,9",
            "CHO,SA,4,10", "CHO,SA,6,10",
            "CHO,SANG,3,10", "CHO,SANG,8,10",
            "CHO,MA,2,10", "CHO,MA,7,10",
            "CHO,CHA,1,10", "CHO,CHA,9,10",
            "CHO,PO,2,8", "CHO,PO,8,8",
            "CHO,ZOL,1,7", "CHO,ZOL,3,7", "CHO,ZOL,5,7", "CHO,ZOL,7,7", "CHO,ZOL,9,7",

            "HAN,KING,5,2",
            "HAN,SA,4,1", "HAN,SA,6,1",
            "HAN,SANG,3,1", "HAN,SANG,8,1",
            "HAN,MA,2,1", "HAN,MA,7,1",
            "HAN,CHA,1,1", "HAN,CHA,9,1",
            "HAN,PO,2,3", "HAN,PO,8,3",
            "HAN,ZOL,1,4", "HAN,ZOL,3,4", "HAN,ZOL,5,4", "HAN,ZOL,7,4", "HAN,ZOL,9,4"

    })
    @DisplayName("초기화된 보드의 지정된 위치에 각 나라의 기물이 알맞게 배치되어 있다 (초, 한 모두 기본 상차림인 마상마상)")
    void 보드_초기화_기물_배치_확인(Team team, PieceType pieceType, int x, int y) {
        //given
        BoardInitiator boardInitiator = new BoardInitiator();
        Board board = new Board();
        BoardFormation hanFormation = BoardFormation.MA_SANG_MA_SANG;
        BoardFormation choFormation = BoardFormation.MA_SANG_MA_SANG;
        Position position = new Position(x, y);
        Piece piece = new Piece(team, pieceType);

        //when
        boardInitiator.initializeByFormation(board, hanFormation, Team.HAN);
        boardInitiator.initializeByFormation(board, choFormation, Team.CHO);

        //then
        assertThat(board.pieceAt(position)).isEqualTo(piece);
    }

    @ParameterizedTest
    @CsvSource({

            "CHO,KING,5,9",
            "CHO,SA,4,10", "CHO,SA,6,10",
            "CHO,SANG,2,10", "CHO,SANG,7,10",
            "CHO,MA,3,10", "CHO,MA,8,10",
            "CHO,CHA,1,10", "CHO,CHA,9,10",
            "CHO,PO,2,8", "CHO,PO,8,8",
            "CHO,ZOL,1,7", "CHO,ZOL,3,7", "CHO,ZOL,5,7", "CHO,ZOL,7,7", "CHO,ZOL,9,7",

            "HAN,KING,5,2",
            "HAN,SA,4,1", "HAN,SA,6,1",
            "HAN,SANG,3,1", "HAN,SANG,8,1",
            "HAN,MA,2,1", "HAN,MA,7,1",
            "HAN,CHA,1,1", "HAN,CHA,9,1",
            "HAN,PO,2,3", "HAN,PO,8,3",
            "HAN,ZOL,1,4", "HAN,ZOL,3,4", "HAN,ZOL,5,4", "HAN,ZOL,7,4", "HAN,ZOL,9,4"

    })
    @DisplayName("한나라는 마상마상, 초나라는 상마상마의 상차람으로 보드를 초기화한다.")
    void 한_마상마상_초_상마상마의_상차림으로_보드_초기화(Team team, PieceType pieceType, int x, int y) {
        // given
        BoardInitiator boardInitiator = new BoardInitiator();
        Board board = new Board();
        BoardFormation hanFormation = BoardFormation.MA_SANG_MA_SANG;
        BoardFormation choFormation = BoardFormation.SANG_MA_SANG_MA;
        Position position = new Position(x, y);
        Piece piece = new Piece(team, pieceType);

        // when
        boardInitiator.initializeByFormation(board, hanFormation, Team.HAN);
        boardInitiator.initializeByFormation(board, choFormation, Team.CHO);

        // then
        assertThat(board.pieceAt(position)).isEqualTo(piece);
    }
}
