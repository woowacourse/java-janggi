package domain.piece;

import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CannonTest {

    private Piece cannon;

    @BeforeEach
    void setUp() {
        cannon = new Piece(PieceType.CANNON, Team.CHO, new CannonStrategy());
    }

    @Test
    void 포는_왼쪽_직선으로_가는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(2, 5);

        List<Position> pathPositions = cannon.getPathPositions(from, to);


        assertThat(pathPositions).isEqualTo(List.of(new Position(4, 5), new Position(3, 5)));
    }

    @Test
    void 포는_오른쪽_직선으로_가는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(8, 5);

        List<Position> pathPositions = cannon.getPathPositions(from, to);


        assertThat(pathPositions).isEqualTo(List.of(new Position(6, 5), new Position(7, 5)));
    }


    @Test
    void 포는_위쪽_직선으로_가는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(5, 7);

        List<Position> pathPositions = cannon.getPathPositions(from, to);


        assertThat(pathPositions).isEqualTo(List.of(new Position(5, 6)));
    }


    @Test
    void 포는_아래쪽_직선으로_가는_경로가_있다() {
        Position from = new Position(5, 5);
        Position to = new Position(5, 0);

        List<Position> pathPositions = cannon.getPathPositions(from, to);


        assertThat(pathPositions).isEqualTo(List.of(
                new Position(5, 4), new Position(5, 3), new Position(5, 2), new Position(5, 1)));
    }

}
