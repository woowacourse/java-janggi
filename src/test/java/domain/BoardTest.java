package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 장기판의_행은_10이고_열은_9이다() {
        Board board = new Board();

        assertThat(board.getBoard().length).isEqualTo(10);
        assertThat(board.getBoard()[0].length).isEqualTo(9);
    }

    @Test
    void 장기판에_기물을_놓을_수_있다() {
        Board board = new Board();
        Position position = new Position(0, 0);
        Piece piece = new Piece(position, Team.BLUE, board);

        board.putPiece(piece);

        assertThat(board.getBoard()[0][0]).isEqualTo(piece);
    }

    @Test
    void 장기판_기물이_존재하는지_확인한다() {
        Board board = new Board();
        Position position = new Position(0, 0);
        Piece piece = new Piece(position, Team.BLUE, board);
        board.putPiece(piece);

        assertThat(board.isExists(0, 0)).isTrue();
    }

    @Test
    void 장기판_기물이_같은팀_기물인지_확인한다() {
        Board board = new Board();
        Position position = new Position(0, 0);
        Piece piece = new Piece(position, Team.BLUE, board);
        Piece piece1 = new Piece(new Position(1, 1), Team.BLUE, board);
        board.putPiece(piece);
        board.putPiece(piece1);

        assertThat(board.isSameTeam(1, 1, Team.BLUE)).isTrue();
    }

}
