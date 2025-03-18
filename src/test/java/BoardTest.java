import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BoardTest {

    @Test
    void 해당_포지션에_있는_기물_확인() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        Piece piece = board.getPieceBy(new Position(Row.ONE, Column.ONE));

        assertThat(piece).isInstanceOf(Chariot.class);
    }


}
