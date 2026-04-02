package domain.piece;

import domain.Offset;
import domain.board.Board;
import domain.board.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChariotTest {

    private Piece chariot;

    @BeforeEach
    void setUp() {
        chariot = new Chariot(Team.CHO);
    }

    @Test
    void 차는_왼쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(-3, 0);

        List<Offset> pathPositions = chariot.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(-1, 0), new Offset(-2, 0)));
    }

    @Test
    void 차는_오른쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(3, 0);

        List<Offset> pathPositions = chariot.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0), new Offset(2, 0)));
    }


    @Test
    void 차는_위쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(0, 1);

        List<Offset> pathPositions = chariot.getPathOffset(offset);

        assertThat(pathPositions).isEqualTo(List.of());
    }


    @Test
    void 차는_아래쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(0, -5);
        List<Offset> pathPositions = chariot.getPathOffset(offset);


        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(0, -1),
                        new Offset(0, -2),
                        new Offset(0, -3),
                        new Offset(0, -4)
                ));
    }

    @Test
    void 기물이_이동할_경로에_대해_다른_기물이_없으면_차는_이동할_수_있다() {

        Board board = new Board(Map.of(
                new Position(0, 0), new Chariot(Team.CHO),
                new Position(5, 0), new Chariot(Team.HAN)
        ));

        board.move(new Position(0,0), new Position(4,0));

        Piece piece = board.getPiece(new Position(4, 0)).get();
        assertThat(piece).isEqualTo(new Chariot(Team.CHO));
    }


    @Test
    void 기물이_이동할_경로에_대해_다른_기물이_있으면_차는_이동할_수_없다() {
        Board board = new Board(Map.of(
                new Position(0, 0), new Chariot(Team.CHO),
                new Position(5, 0), new Chariot(Team.HAN)
        ));

        assertThrows(IllegalStateException.class, () -> board.move(new Position(0, 0), new Position(7, 0)));
    }

    @Test
    void 기물이_이동할_경로에_대해_다른_팀의_기물이_있다면_잡는다 () {
        Board board = new Board(Map.of(
                new Position(0, 0), new Chariot(Team.CHO),
                new Position(5, 0), new Chariot(Team.HAN)
        ));

        board.move(new Position(0,0), new Position(5,0));

        Piece piece = board.getPiece(new Position(5, 0)).get();
        assertThat(piece).isEqualTo(new Chariot(Team.CHO));
    }
}
