package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceTest {

    @Test
    void 기물은_위치를_가지고_있어야_한다() {
        Position position = new Position(0, 0);
        Board board = new Board();
        Piece piece = new Piece(position, Team.BLUE, board);

        assertThat(piece.getPosition()).isEqualTo(new Position(0, 0));
    }

    @Test
    void 기물은_팀_정보를_가지고_있어야_한다() {
        Position position = new Position(0, 0);
        Board board = new Board();
        Piece piece = new Piece(position, Team.BLUE, board);

        assertThat(piece.getTeam()).isEqualTo(Team.BLUE);
    }

    @Test
    void 기물은_맵_정보를_가지고_있다() {
        Position position = new Position(0, 0);
        Board board = new Board();
        Piece piece = new Piece(position, Team.BLUE, board);

        assertThat(piece.getBoard()).isNotNull();
    }

    @CsvSource(value = {"1,1,false", "1,0,true"})
    @ParameterizedTest
    void 같은팀_기물_위로는_움직일_수_없다(int x, int y, boolean expected) {
        Position position = new Position(0, 0);
        Position position1 = new Position(x, y);
        Board board = new Board();
        Piece piece = new Piece(position, Team.BLUE, board);
        Piece piece1 = new Piece(position1, Team.BLUE, board);
        board.putPiece(piece);
        board.putPiece(piece1);

        assertThat(piece.canMove(1, 1)).isEqualTo(expected);
    }

}
