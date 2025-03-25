package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.board.Column;
import janggi.domain.board.PiecePath;
import janggi.domain.board.Position;
import janggi.domain.board.Row;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CannonTest {
    @Test
    void 포의_목적지까지의_이동경로에_포함되는_좌표를_반환() {
        // given
        Cannon cannon = new Cannon(TeamColor.RED);

        Position source = new Position(Row.ONE, Column.ONE);
        Position destination = new Position(Row.ONE, Column.FIVE);
        PiecePath path = new PiecePath(source, destination);

        // when
        List<Position> allRoute = cannon.findAllRoute(path);

        // then
        assertAll(
                () -> assertThat(allRoute).hasSize(3),
                () -> assertThat(allRoute.get(0)).isEqualTo(new Position(Row.ONE, Column.TWO)),
                () -> assertThat(allRoute.get(1)).isEqualTo(new Position(Row.ONE, Column.THREE)),
                () -> assertThat(allRoute.get(2)).isEqualTo(new Position(Row.ONE, Column.FOUR))
        );
    }

    @Test
    void 포의_목적지에_같은팀이_있으면_이동불가() {
        Piece piece = new Cannon(TeamColor.RED);
        Piece elephant = new Elephant(TeamColor.RED);

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = piece.canMove(piece, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 포의_이동경로에_기물이_없으면_이동불가() {
        Piece piece = new Cannon(TeamColor.RED);
        Piece elephant = new Elephant(TeamColor.BLUE);

        List<Piece> piecesOnRoute = List.of();

        boolean canMove = piece.canMove(piece, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 포의_이동경로에_기물이_두개면_이동불가() {
        Piece piece = new Cannon(TeamColor.RED);
        Piece elephant = new Elephant(TeamColor.BLUE);

        List<Piece> piecesOnRoute = List.of(new Elephant(TeamColor.BLUE), new Elephant(TeamColor.RED));

        boolean canMove = piece.canMove(piece, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 포의_이동경로에_기물이_하나고_목적지가_같은팀이_아니면_이동가능() {
        Piece piece = new Cannon(TeamColor.RED);
        Piece elephant = new Elephant(TeamColor.BLUE);
        List<Piece> piecesOnRoute = List.of(new Elephant(TeamColor.BLUE));

        boolean canMove = piece.canMove(piece, elephant, piecesOnRoute);
        assertThat(canMove).isTrue();
    }

    @Test
    void 포의_이동경로에_포가_있으면_기물이_하나여도_이동불가() {
        Piece piece = new Cannon(TeamColor.RED);
        Piece elephant = new Elephant(TeamColor.BLUE);
        List<Piece> piecesOnRoute = List.of(new Cannon(TeamColor.BLUE));

        boolean canMove = piece.canMove(piece, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 포의_목적지가_포라면_이동불가() {
        Piece piece = new Cannon(TeamColor.RED);
        Piece cannon = new Cannon(TeamColor.BLUE);
        List<Piece> piecesOnRoute = List.of(new Elephant(TeamColor.BLUE));

        boolean canMove = piece.canMove(piece, cannon, piecesOnRoute);
        assertThat(canMove).isFalse();
    }
}
