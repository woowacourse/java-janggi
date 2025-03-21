package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.piece.Piece;
import janggi.piece.Type;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.view.SetupOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardGeneratorTest {

    @DisplayName("안상을 생성하면 상이 안쪽에 있어야한다.")
    @Test
    void testInnerSetup() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.INNER_SETUP);
        // when
        final Piece piece1 = board.getPiece(new Position(Row.ZERO, Column.TWO));
        final Piece piece2 = board.getPiece(new Position(Row.ZERO, Column.SIX));
        // then
        assertAll(
                () -> assertThat(piece1.type()).isEqualTo(Type.ELEPHANT),
                () -> assertThat(piece2.type()).isEqualTo(Type.ELEPHANT)
        );
    }

    @DisplayName("바깥상을 생성하면 상이 바깥쪽에 있어야한다.")
    @Test
    void testOuterSetup() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.OUTER_SETUP);
        // when
        final Piece piece1 = board.getPiece(new Position(Row.ZERO, Column.ONE));
        final Piece piece2 = board.getPiece(new Position(Row.ZERO, Column.SEVEN));
        // then
        assertAll(
                () -> assertThat(piece1.type()).isEqualTo(Type.ELEPHANT),
                () -> assertThat(piece2.type()).isEqualTo(Type.ELEPHANT)
        );
    }

    @DisplayName("왼상을 생성하면 상이 왼쪽에 있어야한다.")
    @Test
    void testLeftSetup() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.LEFT_SETUP);
        // when
        final Piece piece1 = board.getPiece(new Position(Row.ZERO, Column.TWO));
        final Piece piece2 = board.getPiece(new Position(Row.ZERO, Column.SEVEN));
        // then
        assertAll(
                () -> assertThat(piece1.type()).isEqualTo(Type.ELEPHANT),
                () -> assertThat(piece2.type()).isEqualTo(Type.ELEPHANT)
        );
    }

    @DisplayName("오른상을 생성하면 상이 오른쪽에 있어야한다.")
    @Test
    void testRightSetup() {
        // given
        final Board board = BoardGenerator.generate(SetupOption.RIGHT_SETUP);
        // when
        final Piece piece1 = board.getPiece(new Position(Row.ZERO, Column.ONE));
        final Piece piece2 = board.getPiece(new Position(Row.ZERO, Column.SIX));
        // then
        assertAll(
                () -> assertThat(piece1.type()).isEqualTo(Type.ELEPHANT),
                () -> assertThat(piece2.type()).isEqualTo(Type.ELEPHANT)
        );
    }
}
