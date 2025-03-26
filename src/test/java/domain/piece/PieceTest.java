package domain.piece;

import domain.MoveInfos;
import domain.direction.Direction;
import domain.direction.Directions;
import domain.piece.category.Cannon;
import domain.piece.category.Chariot;
import domain.piece.category.Elephant;
import domain.piece.category.Guard;
import domain.piece.category.Horse;
import domain.piece.category.King;
import domain.piece.category.PieceCategory;
import domain.piece.category.Soldier;
import domain.spatial.Position;
import domain.spatial.Vector;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PieceTest {

    @Test
    void 기물_객체를_생성할_수_있다() {
        // given
        final Position position = new Position(1, 2);

        // when
        final TestPiece piece = new TestPiece(new Position(1, 2), new Directions(List.of()));

        // then
        assertThat(piece.getPosition()).isEqualTo(position);
    }

    @Test
    void 기물이_타겟_위치까지_도달하는_이동_경로를_반환한다() {
        // given
        final Position targetPosition = new Position(5, 5);
        List<Position> expected = List.of(new Position(4, 6));

        List<Vector> vectors = List.of(new Vector(0, -1), new Vector(1, -1));
        List<Direction> directionElements = List.of(new Direction(vectors, false));
        Directions directions = new Directions(directionElements);

        Piece piece = new TestPiece(new Position(4, 7), directions);

        // when
        List<Position> result = piece.getPaths(targetPosition);

        // then
        assertThat(result).containsAll(expected);
    }

    @Test
    void 위치가_같은지_판단한다() {
        // given
        final Position position = new Position(1, 2);
        final Piece piece = new TestPiece(new Position(1, 2), new Directions(List.of()));

        // when
        boolean result = piece.isSamePosition(position);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 위치를_변경한다() {
        // given
        final Position position = new Position(2, 2);
        final Piece piece = new TestPiece(new Position(1, 2), new Directions(List.of()));

        // when
        Piece result = piece.move(position, new MoveInfos(List.of()));

        // then
        assertThat(result.getPosition())
                .isEqualTo(position);
    }

    @Test
    void 왕인지_판단한다() {
        // given
        final Piece king = new King(new Position(1, 2), new Directions(List.of()));
        final Piece guard = new Guard(new Position(1, 2), new Directions(List.of()));
        final Piece cannon = new Cannon(new Position(1, 2), new Directions(List.of()));
        final Piece elephant = new Elephant(new Position(1, 2), new Directions(List.of()));
        final Piece horse = new Horse(new Position(1, 2), new Directions(List.of()));
        final Piece soldier = new Soldier(new Position(1, 2), new Directions(List.of()));
        final Piece chariot = new Chariot(new Position(1, 2), new Directions(List.of()));
        final Piece piece = new TestPiece(new Position(1, 2), new Directions(List.of()));

        // when & then
        assertAll(
                () -> assertThat(king.isKing()).isTrue(),
                () -> assertThat(guard.isKing()).isFalse(),
                () -> assertThat(cannon.isKing()).isFalse(),
                () -> assertThat(elephant.isKing()).isFalse(),
                () -> assertThat(horse.isKing()).isFalse(),
                () -> assertThat(soldier.isKing()).isFalse(),
                () -> assertThat(chariot.isKing()).isFalse(),
                () -> assertThat(piece.isKing()).isFalse()
        );
    }

    static class TestPiece extends Piece {

        public TestPiece(final Position position, final Directions directions) {
            super(position, directions);
        }

        @Override
        public PieceCategory getCategory() {
            return PieceCategory.NONE;
        }

        @Override
        public TestPiece move(final Position position, final MoveInfos moveInfos) {
            return new TestPiece(position, directions);
        }

        @Override
        public boolean isKing() {
            return false;
        }
    }
}
