package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.direction.Direction;
import domain.direction.Directions;
import domain.direction.Vector;
import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.Elephant;
import domain.piece.category.General;
import domain.piece.category.Guard;
import domain.piece.category.Horse;
import domain.piece.category.Soldier;
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

        List<Vector> vectors = List.of(Vector.UP, Vector.UP_RIGHT);
        List<Direction> directionElements = List.of(new Direction(vectors, false));
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
    void 입력받은_타입의_기물인지_판단한다() {
        // given
        final Piece cannon = new Cannon(Position.of(1, 2), new Directions(List.of()));
        final Piece king = new General(Position.of(1, 2), new Directions(List.of()));
        final Piece advisor = new Guard(Position.of(1, 2), new Directions(List.of()));
        final Piece elephant = new Elephant(Position.of(1, 2), new Directions(List.of()));
        final Piece horse = new Horse(Position.of(1, 2), new Directions(List.of()));
        final Piece pawn = new Soldier(Position.of(1, 2), new Directions(List.of()));
        final Piece rook = new Chariot(Position.of(1, 2), new Directions(List.of()));
        final Piece piece = new TestPiece(Position.of(1, 2), new Directions(List.of()));

        // when & then
        assertAll(
                () -> assertThat(cannon.isEqualType(PieceType.CANNON)).isTrue(),
                () -> assertThat(king.isEqualType(PieceType.CANNON)).isFalse(),
                () -> assertThat(advisor.isEqualType(PieceType.CANNON)).isFalse(),
                () -> assertThat(elephant.isEqualType(PieceType.CANNON)).isFalse(),
                () -> assertThat(horse.isEqualType(PieceType.CANNON)).isFalse(),
                () -> assertThat(pawn.isEqualType(PieceType.CANNON)).isFalse(),
                () -> assertThat(rook.isEqualType(PieceType.CANNON)).isFalse(),
                () -> assertThat(piece.isEqualType(PieceType.CANNON)).isFalse()
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
        public boolean isEqualType(final PieceType type) {
            return PieceType.GENERAL == type;
        }

        @Override
        public String getName() {
            return "";
        }
    }
}
