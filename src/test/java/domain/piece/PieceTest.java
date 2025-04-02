package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.direction.Direction;
import domain.direction.Directions;
import domain.direction.Vector;
import domain.position.Position;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    void 기물_객체를_생성할_수_있다() {
        // given
        final Position position = Position.of(1, 2);

        // when
        final Piece piece = new Piece(position, PieceType.HORSE, MovementRule.HORSE);

        // then
        assertThat(piece.getPosition()).isEqualTo(position);
    }

    @Test
    void 기물이_타겟_위치까지_도달하는_이동_경로를_반환한다() {
        // given
        final Position targetPosition = Position.of(5, 5);

        List<Position> expected = List.of(Position.of(4, 6));

        List<Vector> vectors = List.of(Vector.UP, Vector.UP_RIGHT);
        Set<Direction> directionElements = Set.of(new Direction(vectors));
        Directions directions = new Directions(directionElements, false);

        Piece piece = new Piece(Position.of(4, 7), PieceType.HORSE, MovementRule.HORSE);

        // when
        List<Position> result = piece.getPath(targetPosition);

        // then
        assertThat(result).containsAll(expected);
    }

    @Test
    void 위치가_같은지_판단한다() {
        // given
        final Position position = Position.of(1, 2);
        final Piece piece = new Piece(position, PieceType.HORSE, MovementRule.HORSE);

        // when
        boolean result = piece.isSamePosition(position);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void 위치를_변경한다() {
        // given
        final Position position = Position.of(2, 2);
        final Piece piece = new Piece(position, PieceType.CHARIOT, MovementRule.CHARIOT);

        // when
        Piece result = piece.updatePosition(position);

        // then
        assertThat(result.getPosition())
                .isEqualTo(position);
    }

    @Test
    void 입력받은_타입의_기물인지_판단한다() {
        // given
        final Piece cannon = new Piece(Position.of(1, 2), PieceType.CANNON, MovementRule.CANNON);
        final Piece horse = new Piece(Position.of(1, 2), PieceType.HORSE, MovementRule.HORSE);

        // when & then
        assertAll(
                () -> assertThat(cannon.isEqualType(PieceType.CANNON)).isTrue(),
                () -> assertThat(horse.isEqualType(PieceType.CANNON)).isFalse()
        );
    }

    @Test
    void 이동할_수_없는_위치일_경우_예외를_발생한다() {
        // given
        final Piece piece = new Piece(Position.of(8, 1), PieceType.HORSE, MovementRule.HORSE);
        final Position target = Position.of(7, 2);

        // when & then
        assertThatThrownBy(() -> piece.validateMovablePosition(target))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동할 수 없는 위치입니다.");

    }
}
