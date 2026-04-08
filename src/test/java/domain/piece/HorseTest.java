package domain.piece;

import domain.Offset;
import domain.board.Board;
import domain.board.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HorseTest {
    private Piece horse;

    @BeforeEach
    void setUp() {
        horse = new Horse(Team.CHO);
    }

    @Test
    void 마는_위로_두칸_왼쪽으로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-1, 2);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = horse.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of(new Offset(0, 1)));
    }

    @Test
    void 마는_위로_두칸_오른쪽으로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(1, 2);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = horse.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of(new Offset(0, 1)));
    }


    @Test
    void 마는_위로_한칸_왼쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-2, 1);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = horse.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of(new Offset(-1, 0)));

    }


    @Test
    void 마는_아래로_한칸_왼쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-2, -1);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = horse.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of(new Offset(-1, 0)));
    }


    @Test
    void 마는_아래로_두칸_왼쪽으로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(-1, -2);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = horse.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of(new Offset(0, -1)));

    }


    @Test
    void 마는_아래로_두칸_오른쪽으로_한칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(1, -2);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = horse.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of(new Offset(0, -1)));
    }


    @Test
    void 마는_위로_한칸_오른쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(2, 1);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = horse.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0)));
    }


    @Test
    void 마는_아래로_한칸_오른쪽으로_두칸_움직일_수_있는_경로가_있다() {
        Offset offset = new Offset(2, -1);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = horse.getPathOffset(from, to, Optional.empty());
        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0)));
    }


    @Test
    void 기물이_이동할_경로에_대해_다른_기물이_없으면_마는_이동할_수_있다() {

        Board board = new Board(Map.of(
                new Position(4, 0), new Horse(Team.CHO),
                new Position(4, 2), new Chariot(Team.HAN)
        ));

        board.move(new Position(4, 0), new Position(5, 2));

        Piece piece = board.getPiece(new Position(5, 2)).get();
        boolean result = piece.isSameTeam(Team.CHO) && piece.isSameType(PieceType.HORSE);
        assertThat(result).isTrue();
    }


    @Test
    void 기물이_이동할_경로에_대해_다른_기물이_있으면_마는_이동할_수_없다() {
        Board board = new Board(Map.of(
                new Position(4, 0), new Horse(Team.CHO),
                new Position(4, 1), new Chariot(Team.HAN)
        ));

        assertThrows(IllegalStateException.class, () -> board.move(new Position(4, 0), new Position(5, 2)));
    }

    @Test
    void 기물이_이동할_경로에_대해_다른_팀의_기물이_있다면_잡는다() {
        Board board = new Board(Map.of(
                new Position(4, 0), new Horse(Team.CHO),
                new Position(2, 1), new Chariot(Team.HAN)
        ));

        board.move(new Position(4, 0), new Position(2, 1));

        Piece piece = board.getPiece(new Position(2, 1)).get();
        boolean result = piece.isSameTeam(Team.CHO) && piece.isSameType(PieceType.HORSE);
        assertThat(result).isTrue();

    }
}
