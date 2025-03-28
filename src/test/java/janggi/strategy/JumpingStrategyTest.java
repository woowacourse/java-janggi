package janggi.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.direction.PieceMovement;
import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.position.Position;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JumpingStrategyTest {

    private final JumpingStrategy jumpingStrategy = new JumpingStrategy(PieceMovement.CANNON);
    private final WalkingStrategy walkingStrategy = new WalkingStrategy(PieceMovement.CHARIOT);

    @Test
    void 이동_경로_중간에_기물이_존재하는_경우에만_움직인다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Piece cannon = new Piece(jumpingStrategy, currentPosition);
        final Piece soldier = new Piece(walkingStrategy, new Position(7, 1));

        final Position arrivalPosition = new Position(6, 1);

        // When & Then
        Assertions.assertThatCode(() -> {
            jumpingStrategy.validatePath(currentPosition, arrivalPosition, Pieces.from(Set.of(cannon, soldier)));
        }).doesNotThrowAnyException();
    }

    @Test
    void 경로상에_두개_이상의_기물이_존재하는_경우_움직일_수_없다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Piece cannon = new Piece(jumpingStrategy, currentPosition);
        final Piece soldier = new Piece(walkingStrategy, new Position(7, 1));
        final Piece guard = new Piece(walkingStrategy, new Position(6, 1));

        final Position arrivalPosition = new Position(5, 1);

        // When & Then
        assertThatThrownBy(
                () -> jumpingStrategy.validatePath(currentPosition, arrivalPosition, Pieces.from(Set.of(cannon, soldier, guard))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
    }

    @Test
    void 같은_전략의_기물을_뛰어넘을_수_없다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Piece cannon = new Piece(jumpingStrategy, currentPosition);
        final Piece cannon2 = new Piece(jumpingStrategy, new Position(7, 1));
        final Position arrivalPosition = new Position(6, 1);

        // When & Then
        assertThatThrownBy(
                () -> jumpingStrategy.validatePath(currentPosition, arrivalPosition, Pieces.from(Set.of(cannon, cannon2))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 포는 포끼리 뛰어넘거나 잡을 수 없습니다.");
    }

    @Test
    void 같은_전략의_기물을_잡을_수_없다() {
        // Given
        final Position currentPosition = new Position(8, 1);
        final Piece cannon = new Piece(jumpingStrategy, currentPosition);
        final Position arrivalPosition = new Position(7, 1);
        final Piece cannon2 = new Piece(jumpingStrategy, arrivalPosition);

        // When & Then
        assertThatThrownBy(
                () -> jumpingStrategy.validatePath(currentPosition, arrivalPosition, Pieces.from(Set.of(cannon, cannon2))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
    }


    @Test
    void 한_번에_여러_방향으로_움직일_수_없다() {
        // Given
        final Position currentPosition = new Position(3, 3);
        final Piece cannon = new Piece(jumpingStrategy, currentPosition);
        final Position arrivalPosition = new Position(5, 4);

        // When & Then
        assertThatThrownBy(() -> jumpingStrategy.validatePath(currentPosition, arrivalPosition, Pieces.from(Set.of(cannon))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
    }
}
