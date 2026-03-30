package domain.piece;

import domain.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChariotTest {

    private Piece chariot;

    @BeforeEach
    void setUp() {
        chariot = new Chariot(Team.CHO);
    }

    @Test
    void 차는_왼쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(-3, 0);

        List<Offset> pathPositions = chariot.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(-1, 0), new Offset(-2, 0)));
    }

    @Test
    void 차는_오른쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(3, 0);

        List<Offset> pathPositions = chariot.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0), new Offset(2, 0)));
    }


    @Test
    void 차는_위쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(0, 1);

        List<Offset> pathPositions = chariot.getPathPositions(offset);

        assertThat(pathPositions).isEqualTo(List.of());
    }


    @Test
    void 차는_아래쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(0, -5);
        List<Offset> pathPositions = chariot.getPathPositions(offset);


        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(0, -1),
                        new Offset(0, -2),
                        new Offset(0, -3),
                        new Offset(0, -4)
                ));
    }


}
