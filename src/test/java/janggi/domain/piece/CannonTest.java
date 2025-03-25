package janggi.domain.piece;

import static janggi.domain.TestFixture.BLUE_CANNON;
import static janggi.domain.TestFixture.BLUE_ELEPHANT;
import static janggi.domain.TestFixture.BLUE_HORSE;
import static janggi.domain.TestFixture.RED_CANNON;
import static janggi.domain.TestFixture.RED_ELEPHANT;
import static janggi.domain.TestFixture.RED_HORSE;
import static janggi.domain.TestFixture.RED_SOLDIER;
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
        Piece cannon = RED_CANNON;

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
        Piece cannon = RED_CANNON;
        Piece destination = RED_ELEPHANT;

        List<Piece> piecesOnRoute = new ArrayList<>();

        boolean canMove = cannon.canMove(cannon, destination, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 포의_이동경로에_기물이_없으면_이동불가() {
        Piece cannon = RED_CANNON;
        Piece elephant = BLUE_ELEPHANT;

        List<Piece> piecesOnRoute = List.of();

        boolean canMove = cannon.canMove(cannon, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 포의_이동경로에_기물이_두개면_이동불가() {
        Piece cannon = RED_CANNON;
        Piece elephant = BLUE_ELEPHANT;

        List<Piece> piecesOnRoute = List.of(RED_HORSE, RED_SOLDIER);

        boolean canMove = cannon.canMove(cannon, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 포의_이동경로에_기물이_하나고_목적지가_같은팀이_아니면_이동가능() {
        Piece cannon = RED_CANNON;
        Piece elephant = BLUE_ELEPHANT;

        List<Piece> piecesOnRoute = List.of(BLUE_HORSE);

        boolean canMove = cannon.canMove(cannon, elephant, piecesOnRoute);
        assertThat(canMove).isTrue();
    }

    @Test
    void 포의_이동경로에_포가_있으면_기물이_하나여도_이동불가() {
        Piece cannon = RED_CANNON;
        Piece elephant = BLUE_ELEPHANT;

        List<Piece> piecesOnRoute = List.of(BLUE_CANNON);
        boolean canMove = cannon.canMove(cannon, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }

    @Test
    void 포의_목적지가_포라면_이동불가() {
        Piece cannon = RED_CANNON;
        Piece elephant = BLUE_CANNON;

        List<Piece> piecesOnRoute = List.of(RED_SOLDIER);

        boolean canMove = cannon.canMove(cannon, elephant, piecesOnRoute);
        assertThat(canMove).isFalse();
    }
}
