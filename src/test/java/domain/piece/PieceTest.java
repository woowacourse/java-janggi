package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.direction.Direction;
import domain.direction.Directions;
import domain.piece.category.Advisor;
import domain.piece.category.Cannon;
import domain.piece.category.Elephant;
import domain.piece.category.Horse;
import domain.piece.category.King;
import domain.piece.category.Pawn;
import domain.piece.category.Rook;
import java.util.List;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    void 기물_객체를_생성할_수_있다() {
        // given
        final Position position = Position.of(1, 2);

        // when
        final TestPiece piece = new TestPiece(1, 2, new Directions(List.of()));

        // then
        assertThat(piece.getPosition()).isEqualTo(position);
    }

    @Test
    void 기물이_타겟_위치까지_도달하는_이동_경로를_반환한다() {
        // given
        final Position targetPosition = Position.of(5, 5);

        List<Position> expected = List.of(Position.of(4, 6));

        List<Position> positions = List.of(Position.ofDirection(0, -1), Position.ofDirection(1, -1));
        List<Direction> directionElements = List.of(new Direction(positions, false));
        Directions directions = new Directions(directionElements);

        Piece piece = new TestPiece(4, 7, directions);

        // when
        List<Position> result = piece.getPath(targetPosition);

        // then
        assertThat(result).containsAll(expected);
    }

    @Test
    void 위치가_같은지_판단한다() {
        // given
        final Position position = Position.of(1, 2);
        final Piece piece = new TestPiece(1, 2, new Directions(List.of()));

        // when
        boolean result = piece.isSamePosition(position);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 위치를_변경한다() {
        // given
        final Position position = Position.of(2, 2);
        final Piece piece = new TestPiece(1, 2, new Directions(List.of()));

        // when
        Piece result = piece.updatePosition(position);

        // then
        assertThat(result.getPosition())
                .isEqualTo(position);
    }

    @Test
    void 왕인지_판단한다() {
        // given
        final Piece king = new King(1, 2, new Directions(List.of()));
        final Piece advisor = new Advisor(1, 2, new Directions(List.of()));
        final Piece cannon = new Cannon(1, 2, new Directions(List.of()));
        final Piece elephant = new Elephant(1, 2, new Directions(List.of()));
        final Piece horse = new Horse(1, 2, new Directions(List.of()));
        final Piece pawn = new Pawn(1, 2, new Directions(List.of()));
        final Piece rook = new Rook(1, 2, new Directions(List.of()));
        final Piece piece = new TestPiece(1, 2, new Directions(List.of()));

        // when & then
        assertAll(
                () -> assertThat(king.isKing()).isTrue(),
                () -> assertThat(advisor.isKing()).isFalse(),
                () -> assertThat(cannon.isKing()).isFalse(),
                () -> assertThat(elephant.isKing()).isFalse(),
                () -> assertThat(horse.isKing()).isFalse(),
                () -> assertThat(pawn.isKing()).isFalse(),
                () -> assertThat(rook.isKing()).isFalse(),
                () -> assertThat(piece.isKing()).isFalse()
        );
    }

    @Test
    void 포인지_판단한다() {
        // given
        final Piece cannon = new Cannon(1, 2, new Directions(List.of()));
        final Piece king = new King(1, 2, new Directions(List.of()));
        final Piece advisor = new Advisor(1, 2, new Directions(List.of()));
        final Piece elephant = new Elephant(1, 2, new Directions(List.of()));
        final Piece horse = new Horse(1, 2, new Directions(List.of()));
        final Piece pawn = new Pawn(1, 2, new Directions(List.of()));
        final Piece rook = new Rook(1, 2, new Directions(List.of()));
        final Piece piece = new TestPiece(1, 2, new Directions(List.of()));

        // when & then
        assertAll(
                () -> assertThat(cannon.isCannon()).isTrue(),
                () -> assertThat(king.isCannon()).isFalse(),
                () -> assertThat(advisor.isCannon()).isFalse(),
                () -> assertThat(elephant.isCannon()).isFalse(),
                () -> assertThat(horse.isCannon()).isFalse(),
                () -> assertThat(pawn.isCannon()).isFalse(),
                () -> assertThat(rook.isCannon()).isFalse(),
                () -> assertThat(piece.isCannon()).isFalse()
        );
    }

    static class TestPiece extends Piece {
        public TestPiece(int row, int column, Directions directions) {
            super(row, column, directions);
        }

        public TestPiece(final Position position, final Directions directions) {
            super(position, directions);
        }

        @Override
        public TestPiece updatePosition(final Position position) {
            return new TestPiece(position, directions);
        }

        @Override
        public boolean isKing() {
            return false;
        }

        @Override
        public boolean isCannon() {
            return false;
        }

        @Override
        public String getName() {
            return "";
        }
    }
}
