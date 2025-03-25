package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.janggi.domain.Board;
import domain.janggi.domain.Color;
import domain.janggi.domain.Position;
import domain.janggi.piece.Chariot;
import domain.janggi.piece.Piece;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardTest {

    @Test
    void 장기판에_기물을_놓을_수_있다() {
        Board board = new Board(List.of());
        Position position = new Position(1, 1);
        Piece piece = new Chariot(position, Color.BLUE, board);

        board.putPieces(List.of(piece));

        assertThat(board.findPiece(position)).isNotNull();
    }

    @Test
    void 위치를_알려주면_해당_위치의_기물을_장기판에서_제거한다() {
        Board board = new Board(List.of());
        Position position = new Position(1, 1);
        Piece piece = new Chariot(position, Color.BLUE, board);
        board.putPieces(List.of(piece));

        board.remove(position);

        assertThatThrownBy(() -> board.findPiece(position)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 장기판_기물이_존재하는지_확인한다() {
        Board board = new Board(List.of());
        Position position = new Position(1, 1);
        Piece piece = new Chariot(position, Color.BLUE, board);
        board.putPieces(List.of(piece));

        assertThat(board.isExists(position)).isTrue();
    }

    @CsvSource(value = {
            "BLUE,true", "RED,false"
    })
    @ParameterizedTest
    void 장기판_기물이_같은팀_기물인지_확인한다(Color color, boolean expected) {
        Board board = new Board(List.of());
        Chariot piece = new Chariot(new Position(1, 1), Color.BLUE, board);
        board.putPieces(List.of(
                piece,
                new Chariot(new Position(2, 1), color, board)
        ));
        assertThat(board.anyMatchSameTeam(piece, new Position(2, 1))).isEqualTo(expected);
    }

    @Test
    void 장기판에서_특정_위치의_기물을_찾는다() {
        Board board = new Board(List.of());
        Piece piece = new Chariot(new Position(2, 1), Color.BLUE, board);
        board.putPieces(List.of(piece));
        assertThat(board.findPiece(new Position(2, 1))).isEqualTo(piece);
    }

    @Test
    void 장기판에서_내_팀의_특정_위치에_있는_기물을_찾는다() {
        Board board = new Board(List.of());
        Piece piece = new Chariot(new Position(2, 1), Color.BLUE, board);
        board.putPieces(List.of(piece));
        Piece findPiece = board.findPiece(new Position(2, 1));
        assertThat(findPiece).isEqualTo(piece);
        assertThat(findPiece.getTeam()).isEqualTo(piece.getTeam());
    }

    @Test
    void 장기판에서_특정_위치의_기물을_찾지_못한다() {
        Board board = new Board(List.of());
        Piece piece = new Chariot(new Position(2, 1), Color.BLUE, board);
        board.putPieces(List.of(piece));
        assertThatThrownBy(() -> board.findPiece(new Position(2, 2)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
