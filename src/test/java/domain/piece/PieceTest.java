package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.Coordinate;
import domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    @DisplayName("Piece가 움직이면 좌표가 변경된다.")
    void test1() {
        // given
        Piece piece = new Piece(new Coordinate(5, 1), Team.CHO);

        // when
        Piece movedPiece = piece.move(new Coordinate(6, 2));

        // then
        assertThat(movedPiece).isEqualTo(new Piece(new Coordinate(6, 2), Team.CHO));
    }
}
