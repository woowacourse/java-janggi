package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Column;
import domain.board.MovePath;
import domain.board.Position;
import domain.board.Row;
import org.junit.jupiter.api.Test;

class GuardTest {

    @Test
    void 사는_궁성_밖으로_이동불가능() {
        Piece guard = new Guard(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.FOUR);
        Position destination = new Position(Row.ONE, Column.THREE);
        MovePath movePath = new MovePath(source, destination);

        boolean canMove = guard.isValidMovement(movePath);

        assertThat(canMove).isFalse();
    }

    @Test
    void 사는_궁성_안에서_직선_한칸_이동가능() {
        Piece guard = new Guard(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.FOUR);
        Position destination = new Position(Row.ONE, Column.FIVE);
        MovePath movePath = new MovePath(source, destination);

        boolean canMove = guard.isValidMovement(movePath);

        assertThat(canMove).isTrue();
    }

    @Test
    void 사는_궁성_안에서_대각선_한칸_이동가능() {
        Piece guard = new Guard(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.FOUR);
        Position destination = new Position(Row.TWO, Column.FIVE);
        MovePath movePath = new MovePath(source, destination);

        boolean canMove = guard.isValidMovement(movePath);

        assertThat(canMove).isTrue();
    }

    @Test
    void 궁은_선이없는_대각방향으로_이동불가능() {
        Piece piece = new Guard(PieceColor.RED);
        Position source = new Position(Row.ONE, Column.FIVE);
        Position destination = new Position(Row.TWO, Column.FOUR);
        MovePath movePath = new MovePath(source, destination);

        boolean canMove = piece.isValidMovement(movePath);

        assertThat(canMove).isFalse();
    }
}
