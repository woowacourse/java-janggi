package chessPiece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @DisplayName("장기판은 기물과 위치를 가진다.")
    @Test
    void board() {
        //given
        ChessPieceInitializer chessPieceInitializer = new ChessPieceInitializer();
        List<ChessPiece> han = chessPieceInitializer.hanInit();
        List<ChessPiece> cho = chessPieceInitializer.choInit();

        //when
        Board board = new Board(han, cho);

        //then
        assertThat(board.getJanggiPan()).hasSize(32);
    }
}
