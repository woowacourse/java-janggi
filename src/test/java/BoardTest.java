import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void 포지션_목록에_몇개의_기물이_있는지_반환() {
        BoardFactory boardFactory = new BoardFactory();
        Board board = boardFactory.createBoard();
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(Row.ONE, Column.FOUR));
        positions.add(new Position(Row.ONE, Column.FIVE));
        positions.add(new Position(Row.ONE, Column.SIX));

        int pieceCount = board.countPieceOnRoute(positions);

        assertThat(pieceCount).isEqualTo(2);
    }
}
