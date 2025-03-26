package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import board.Board;
import board.Position;

class PieceTest {

    @Test
    void 기물을_움직일_수_있다() {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Team.BLUE);
        board.putPieces(List.of(piece));

        Position destination = new Position(2, 1);
        piece.move(destination, board);

        assertThat(board.findPieceByPosition(destination)).isEqualTo(piece);
    }

    @CsvSource(value = {
            "2,1,2,1", "1,2,1,2"
    })
    @ParameterizedTest
    void 기물의_이동_경로에_적팀의_기물이_존재하면_취한_후_이동한다(int row, int column, int targetRow, int targetColumn) {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Team.BLUE);
        board.putPieces(List.of(piece, new Chariot(new Position(row, column), Team.RED)));

        Position destination = new Position(targetRow, targetColumn);
        piece.move(destination, board);

        assertThat(board.findPieceByPosition(destination)).isEqualTo(piece);
        assertThat(board.isSameTeamPosition(Team.BLUE, destination)).isTrue();
    }

    @CsvSource(value = {
            "2,1,3,1", "1,2,1,3", "2,1,2,1"
    })
    @ParameterizedTest
    void 기물의_이동_경로에_아군_기물이_존재하면_움직일_수_없다(int row, int column, int targetRow, int targetColumn) {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Team.BLUE);
        Position destination = new Position(row, column);
        board.putPieces(List.of(new Chariot(destination, Team.BLUE)));

        assertThatThrownBy(() -> piece.move(new Position(targetRow, targetColumn), board))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @CsvSource(value = {
            "10,10", "9,11", "0,1", "1,0"
    })
    @ParameterizedTest
    void 기물은_장기판_밖으로_움직일_수_없다(int row, int column) {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Team.BLUE);
        Position destination = new Position(row, column);

        assertThatThrownBy(() -> piece.move(destination, board))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
