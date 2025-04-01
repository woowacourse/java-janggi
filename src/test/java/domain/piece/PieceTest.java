package domain.piece;

import domain.MoveInfo;
import domain.MoveInfos;
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
        final TestPiece piece = new TestPiece(new Position(1, 2), new Directions(List.of(), false));

        // then
        assertThat(piece.getPosition()).isEqualTo(position);
    }

    @Test
    void 위치가_같은지_판단한다() {
        // given
        final Position position = new Position(1, 2);
        final Piece piece = new TestPiece(new Position(1, 2), new Directions(List.of(), false));

        // when
        boolean result = piece.isSamePosition(position);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 위치를_변경한다() {
        // given
        final Position target = new Position(2, 2);
        final Piece piece = new TestPiece(new Position(1, 2), new Directions(List.of(), false));

        // when
        MoveInfos dummy = new MoveInfos(List.of(
                new MoveInfo(new Position(1, 1), PieceCategory.NONE),
                new MoveInfo(new Position(1, 2), PieceCategory.NONE))
        );

        Piece result = piece.move(target, dummy);

        // then
        assertThat(result.getPosition())
                .isEqualTo(target);
    }

    @Test
    void 왕인지_판단한다() {
        // given
        final Piece king = new King(new Position(1, 2), new Directions(List.of(), false));
        final Piece guard = new Guard(new Position(1, 2), new Directions(List.of(), false));
        final Piece cannon = new Cannon(new Position(1, 2), new Directions(List.of(), false));
        final Piece elephant = new Elephant(new Position(1, 2), new Directions(List.of(), false));
        final Piece horse = new Horse(new Position(1, 2), new Directions(List.of(), false));
        final Piece soldier = new Soldier(new Position(1, 2), new Directions(List.of(), false));
        final Piece chariot = new Chariot(new Position(1, 2), new Directions(List.of(), false));
        final Piece piece = new TestPiece(new Position(1, 2), new Directions(List.of(), false));

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
        public List<Position> getPaths(final Position target) {
            return List.of();
        }

        @Override
        public PieceCategory getCategory() {
            return PieceCategory.NONE;
        }

        @Override
        public TestPiece move(final Position target, final MoveInfos moveInfos) {
            return new TestPiece(target, directions);
        }
    }
}
