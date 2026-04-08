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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CannonTest {

    private Piece cannon;
    private Optional<Palace> choPalace;

    @BeforeEach
    void setUp() {
        cannon = new Cannon(Team.CHO);
        choPalace = Optional.of(new Palace(new Position(4, 1)));
    }

    @Test
    void 포는_왼쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(-3, 0);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = cannon.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of(new Offset(-1, 0), new Offset(-2, 0)));
    }

    @Test
    void 포는_오른쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(3, 0);

        Position from = new Position(4, 5);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = cannon.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(List.of(new Offset(1, 0), new Offset(2, 0)));
    }


    @Test
    void 포는_위쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(0, 5);

        Position from = new Position(4, 1);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = cannon.getPathOffset(from, to, Optional.empty());
        assertThat(pathPositions).isEqualTo(
                List.of(new Offset(0, 1),
                        new Offset(0, 2),
                        new Offset(0, 3),
                        new Offset(0, 4))
        );
    }


    @Test
    void 포는_아래쪽_직선으로_가는_경로가_있다() {
        Offset offset = new Offset(0, -6);

        Position from = new Position(4, 8);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = cannon.getPathOffset(from, to, Optional.empty());

        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(0, -1),
                        new Offset(0, -2),
                        new Offset(0, -3),
                        new Offset(0, -4),
                        new Offset(0, -5))
        );
    }


    @Test
    void 포는_기물이_사이에_하나의_기물이_있으면_정상적으로_움직일_수_있다() {
        List<Piece> blockedPieces = List.of(new Horse(Team.HAN));

        assertDoesNotThrow(() -> cannon.validateMove(blockedPieces));
    }


    @Test
    void 포는_기물이_사이에_하나라도_존재하지_않으면_예외를_반환한다() {
        List<Piece> blockedPieces = List.of();

        assertThrows(IllegalStateException.class,
                () -> cannon.validateMove(blockedPieces)
        );
    }

    @Test
    void 포는_기물이_사이에_두개_이상_존재하면_예외를_반환한다() {
        List<Piece> blockedPieces = List.of(new Horse(Team.HAN), new Horse(Team.HAN));

        assertThrows(IllegalStateException.class,
                () -> cannon.validateMove(blockedPieces));
    }

    @Test
    void 포는_기물이_사이에_포가_존재하면_예외를_반환한다() {
        List<Piece> blockedPieces = List.of(new Cannon(Team.HAN));

        assertThrows(IllegalStateException.class,
                () -> cannon.validateMove(blockedPieces));
    }

    @Test
    void 포는_목적지에_포가_존재하면_예외를_반환한다() {

        Board board = new Board(Map.of(
                new Position(5, 1), new Cannon(Team.CHO),
                new Position(5, 3), new Horse(Team.CHO),
                new Position(5, 7), new Cannon(Team.HAN)
        ));

        assertThrows(IllegalStateException.class,
                () -> board.move(new Position(5, 1), new Position(5, 7)));
    }

    @Test
    void 포는_궁성에서_왼쪽_아래_대각선으로_이동할_수_있다() {
        Offset offset = new Offset(-2, -2);

        Position from = new Position(5, 2);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = cannon.getPathOffset(from, to, choPalace);

        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(-1, -1)
                ));
    }

    @Test
    void 포는_궁성에서_오른쪽_아래_대각선으로_이동할_수_있다() {
        Offset offset = new Offset(2, -2);

        Position from = new Position(3, 2);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = cannon.getPathOffset(from, to, choPalace);

        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(1, -1)
                ));
    }

    @Test
    void 포는_궁성에서_왼쪽_위_대각선으로_이동할_수_있다() {
        Offset offset = new Offset(-2, 2);

        Position from = new Position(5, 0);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = cannon.getPathOffset(from, to, choPalace);

        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(-1, 1)
                ));
    }


    @Test
    void 포는_궁성에서_오른쪽_위_대각선으로_이동할_수_있다() {
        Offset offset = new Offset(-2, -2);

        Position from = new Position(5, 2);
        Position to = offset.applyTo(from);

        List<Offset> pathPositions = cannon.getPathOffset(from, to, choPalace);

        assertThat(pathPositions).isEqualTo(
                List.of(
                        new Offset(-1, -1)
                ));
    }

    @Test
    void 포가_대각선으로_이동하는지_확인한다() {
        Board board = new Board(Map.of(
                new Position(5, 2), new Cannon(Team.CHO),
                new Position(4, 1), new General(Team.CHO)
        ));

        board.move(new Position(5, 2), new Position(3, 0));
        Piece piece = board.getRequiredPiece(new Position(3, 0));

        boolean result = piece.isSameTeam(Team.CHO) && piece.isSameType(PieceType.CANNON);
        assertThat(result).isTrue();
    }
}
