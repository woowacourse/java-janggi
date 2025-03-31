package domain.piece;

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

class PieceTest {

    @Test
    void 기물은_위치를_가지고_있어야_한다() {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Color.BLUE, board);

        assertThat(piece.getPosition()).isEqualTo(new Position(1, 1));
    }

    @Test
    void 기물은_팀_정보를_가지고_있어야_한다() {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Color.BLUE, board);

        assertThat(piece.getTeam()).isEqualTo(Color.BLUE);
    }

    @Test
    void 기물은_맵_정보를_가지고_있다() {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Color.BLUE, board);

        assertThat(piece.getBoard()).isNotNull();
    }

    @Test
    void 기물을_움직일_수_있다() {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Color.BLUE, board);
        board.putPieces(List.of(piece));

        Position nextPosition = new Position(2, 1);
        piece.move(nextPosition);

        assertThat(piece.getPosition()).isEqualTo(nextPosition);
    }

    @CsvSource(value = {
            "2,1,2,1", "1,2,1,2"
    })
    @ParameterizedTest
    void 기물의_이동_경로에_적팀의_기물이_존재하면_취한_후_이동한다(int row, int column, int targetRow, int targetColumn) {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Color.BLUE, board);
        board.putPieces(List.of(
                piece,
                new Chariot(new Position(row, column), Color.RED, board)
        ));

        Position nextPosition = new Position(targetRow, targetColumn);
        piece.move(nextPosition);

        assertThat(piece.getPosition()).isEqualTo(nextPosition);
        assertThat(board.findPiece(nextPosition)).isNotNull();
        assertThatThrownBy(() -> board.findPiece(position)).isInstanceOf(IllegalArgumentException.class);
    }

    @CsvSource(value = {
            "2,1,3,1", "1,2,1,3", "2,1,2,1"
    })
    @ParameterizedTest
    void 기물의_이동_경로에_아군_기물이_존재하면_움직일_수_없다(int row, int column, int targetRow, int targetColumn) {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Color.BLUE, board);
        Position nextPosition = new Position(row, column);
        board.putPieces(List.of(new Chariot(nextPosition, Color.BLUE, board)));

        assertThatThrownBy(() -> piece.move(new Position(targetRow, targetColumn)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
