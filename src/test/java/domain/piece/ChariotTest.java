package domain.piece;

import domain.Offset;
import domain.board.Board;
import domain.board.Palace;
import domain.board.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChariotTest {

    private Piece chariot;
    private Optional<Palace> choPalace;


    @BeforeEach
    void setUp() {
        chariot = new Chariot(Team.CHO);
        choPalace = Optional.of(new Palace(new Position(4, 1)));
    }

    @Test
    void 차는_왼쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(-3, 0);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = chariot.getPathOffset(from, to, Optional.empty());


        assertThat(pathPositions).isEqualTo(List.of(new Offset(-1, 0), new Offset(-2, 0)));
    }

    @Test
    void 차는_오른쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(3, 0);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = chariot.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0), new Offset(2, 0)));
    }


    @Test
    void 차는_위쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(0, 1);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = chariot.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of());
    }


    @Test
    void 차는_아래쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(0, -5);
        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = chariot.getPathOffset(from, to, Optional.empty());


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

        board.move(new Position(0, 0), new Position(4, 0));

        Piece piece = board.getPiece(new Position(4, 0)).get();
        boolean result = piece.isSameTeam(Team.CHO) && piece.isSameType(PieceType.CHARIOT);
        assertThat(result).isTrue();
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
    void 기물이_이동할_경로에_대해_다른_팀의_기물이_있다면_잡는다() {
        Board board = new Board(Map.of(
                new Position(0, 0), new Chariot(Team.CHO),
                new Position(5, 0), new Chariot(Team.HAN)
        ));

        board.move(new Position(0, 0), new Position(5, 0));

        Piece piece = board.getPiece(new Position(5, 0)).get();
        boolean result = piece.isSameTeam(Team.CHO) && piece.isSameType(PieceType.CHARIOT);
        assertThat(result).isTrue();
    }


    @Test
    void 차는_궁성에서_왼쪽_아래_대각선으로_이동할_수_있다() {
        Offset offset = new Offset(-2, -2);

        Position from = new Position(5, 2);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = chariot.getPathOffset(from, to, choPalace);

        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(-1, -1)
                ));
    }

    @Test
    void 차는_궁성에서_오른쪽_아래_대각선으로_이동할_수_있다() {
        Offset offset = new Offset(2, -2);

        Position from = new Position(3, 2);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = chariot.getPathOffset(from, to, choPalace);

        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(1, -1)
                ));
    }

    @Test
    void 차는_궁성에서_왼쪽_위_대각선으로_이동할_수_있다() {
        Offset offset = new Offset(-2, 2);

        Position from = new Position(5, 0);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = chariot.getPathOffset(from, to, choPalace);

        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(-1, 1)
                ));
    }


    @Test
    void 차는_궁성에서_오른쪽_위_대각선으로_이동할_수_있다() {
        Offset offset = new Offset(-2, -2);

        Position from = new Position(5, 2);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = chariot.getPathOffset(from, to, choPalace);

        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(-1, -1)
                ));
    }

    @Test
    void 차가_대각선으로_이동하는지_확인한다() {
        Board board = new Board(Map.of(
                new Position(4, 1), new Chariot(Team.CHO),
                new Position(3, 1), new General(Team.CHO)
        ));

        board.move(new Position(4, 1), new Position(5, 2));
        Piece piece = board.getRequiredPiece(new Position(5, 2));

        boolean result = piece.isSameTeam(Team.CHO) && piece.isSameType(PieceType.CHARIOT);
        assertThat(result).isTrue();

    }
}
