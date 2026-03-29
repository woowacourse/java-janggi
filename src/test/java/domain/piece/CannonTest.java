package domain.piece;

import domain.Offset;
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
        Offset offset = new Offset(-3, 0);

        List<Offset> pathPositions = cannon.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(-1, 0), new Offset(-2, 0)));
    }

    @Test
    void 포는_오른쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(3, 0);

        List<Offset> pathPositions = cannon.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0), new Offset(2, 0)));
    }


    @Test
    void 포는_위쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(3, 0);

        List<Offset> pathPositions = cannon.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0), new Offset(2, 0)));
    }


    @Test
    void 포는_아래쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(0, -6);

        List<Offset> pathPositions = cannon.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(0, -1),
                        new Offset(0, -2),
                        new Offset(0, -3),
                        new Offset(0, -4),
                        new Offset(0, -5))
        );
    }

}
