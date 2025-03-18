package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    void 기물은_위치를_가지고_있어야_한다() {
        Position position = new Position(0, 0);
        Board board = new Board();
        Piece piece = new Piece(position, Team.BLUE, board);

        Assertions.assertThat(piece.getPosition()).isEqualTo(new Position(0, 0));
    }

    @Test
    void 기물은_팀_정보를_가지고_있어야_한다() {
        Position position = new Position(0, 0);
        Board board = new Board();
        Piece piece = new Piece(position, Team.BLUE, board);

        Assertions.assertThat(piece.getTeam()).isEqualTo(Team.BLUE);
    }

    @Test
    void 기물은_맵_정보를_가지고_있다() {
        Position position = new Position(0, 0);
        Board board = new Board();
        Piece piece = new Piece(position, Team.BLUE, board);

        Assertions.assertThat(piece.getBoard()).isNotNull();
    }
}
