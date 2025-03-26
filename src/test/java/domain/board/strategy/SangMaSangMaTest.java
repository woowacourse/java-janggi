package domain.board.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.PieceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SangMaSangMaTest {

    @DisplayName("장기판을 상마상마로 세팅한다.")
    @Test
    void sangMaSangMaTest() {
        Board board = new Board(new SangMaSangMa(), new SangMaSangMa());

        assertThat(board.findPieceByCoordinate(new Coordinate(1, 8)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 7)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 3)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(1, 2)).getType()).isEqualTo(PieceType.MA);

        assertThat(board.findPieceByCoordinate(new Coordinate(10, 2)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 3)).getType()).isEqualTo(PieceType.MA);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 7)).getType()).isEqualTo(PieceType.SANG);
        assertThat(board.findPieceByCoordinate(new Coordinate(10, 8)).getType()).isEqualTo(PieceType.MA);
    }
}
