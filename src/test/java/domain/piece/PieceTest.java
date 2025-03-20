package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Board;
import domain.Position;
import domain.Team;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PieceTest {

    @Test
    void 기물은_위치를_가지고_있어야_한다() {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Team.BLUE, board);

        assertThat(piece.getPosition()).isEqualTo(new Position(1, 1));
    }

    @Test
    void 기물은_팀_정보를_가지고_있어야_한다() {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Team.BLUE, board);

        assertThat(piece.getTeam()).isEqualTo(Team.BLUE);
    }

    @Test
    void 기물은_맵_정보를_가지고_있다() {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Team.BLUE, board);

        assertThat(piece.getBoard()).isNotNull();
    }

    @Test
    void 기물을_움직일_수_있다() {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Team.BLUE, board);
        board.putPiece(piece);

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
        Piece piece = new Chariot(position, Team.BLUE, board);
        board.putPiece(piece);
        board.putPiece(new Chariot(new Position(row, column), Team.RED, board));

        Position nextPosition = new Position(targetRow, targetColumn);
        piece.move(nextPosition);

        assertThat(piece.getPosition()).isEqualTo(nextPosition);
        assertThat(board.getPieces()).hasSize(1);
    }

    @CsvSource(value = {
            "2,1,3,1", "1,2,1,3", "2,1,2,1"
    })
    @ParameterizedTest
    void 기물의_이동_경로에_아군_기물이_존재하면_움직일_수_없다(int row, int column, int targetRow, int targetColumn) {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Team.BLUE, board);
        Position nextPosition = new Position(row, column);
        board.putPiece(new Chariot(nextPosition, Team.BLUE, board));

        assertThatThrownBy(() -> piece.move(new Position(targetRow, targetColumn)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @CsvSource(value = {
            "10,10", "9,11", "0,1", "1,0"
    })
    @ParameterizedTest
    void 기물은_장기판_밖으로_움직일_수_없다(int row, int column) {
        Position position = new Position(1, 1);
        Board board = new Board(List.of());
        Piece piece = new Chariot(position, Team.BLUE, board);
        Position nextPosition = new Position(row, column);

        assertThatThrownBy(() -> piece.move(nextPosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
