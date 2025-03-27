package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import board.Board;
import board.Position;

class PieceTest {

    @CsvSource(value = {
            "2,1,true", "2,2,false"
    })
    @ParameterizedTest
    void 시작위치와_목적지를_알려주면_움직일_수_있는지_알려준다(int targetRow, int targetColumn, boolean expected) {
        Position position = new Position(1, 1);
        Piece piece = new Chariot(Team.BLUE);
        Board board = new Board(Map.of(
                position, piece
        ));

        Position destination = new Position(targetRow, targetColumn);
        assertThat(piece.canMove(new Position(1, 1), destination, board)).isEqualTo(expected);
    }

    @CsvSource(value = {
            "2,1", "1,2", "2,1"
    })
    @ParameterizedTest
    void 기물의_이동_경로에_아군_기물이_존재하면_움직일_수_없다(int row, int column) {
        Position position = new Position(1, 1);
        Piece piece = new Chariot(Team.BLUE);
        Position destination = new Position(row, column);
        Board board = new Board(Map.of(
                position, piece,
                destination, new Chariot(Team.BLUE)
        ));

        assertThat(piece.canMove(position, destination, board)).isFalse();
    }

    @CsvSource(value = {
            "10,10", "9,11", "0,1", "1,0"
    })
    @ParameterizedTest
    void 기물은_장기판_밖으로_움직일_수_없다(int row, int column) {
        Position position = new Position(1, 1);
        Piece piece = new Chariot(Team.BLUE);
        Position destination = new Position(row, column);
        Board board = new Board(Map.of(position, piece));

        assertThat(piece.canMove(position, destination, board)).isFalse();
    }

}
