package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessPiecePositionsTest {

    @Test
    @DisplayName("기물들의 위치를 초기화 한다")
    void test1() {
        //given
        final ChessPosition chessPosition = new ChessPosition(3, 0);
        //when
        final ChessPiecePositions chessPiecePositions = ChessPiecePositions.empty();
        chessPiecePositions.initialize();
        final Map<ChessPosition, ChessPiece> chessPieces = chessPiecePositions.getChessPieces();

        //then
        assertThat(chessPieces.get(chessPosition).getChessPieceType()).isEqualTo(ChessPieceType.PAWN);

    }

}
