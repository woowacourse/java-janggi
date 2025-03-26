package janggi.piece;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Position;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CannonTest {

    private Cannon cannon;

    @BeforeEach
    void setUp() {
        cannon = new Cannon(Team.CHO, new Position(8, 2));
    }

    @Test
    void 포는_이동_경로_중간에_기물이_존재하는_경우에만_움직인다() {
        // Given
        final Cannon cannon = new Cannon(Team.CHO, new Position(8, 1));
        final Soldier soldier = new Soldier(Team.CHO, new Position(7, 1));

        final Position arrivalPosition = new Position(6, 1);

        // When & Then
        Assertions.assertThatCode(() -> cannon.checkMovement(arrivalPosition, Team.CHO, new Pieces(Set.of(
                cannon, soldier)))
        ).doesNotThrowAnyException();
    }

    @Test
    void 포는_경로상에_두개_이상의_기물이_존재하는_경우_움직일_수_없다() {
        // Given
        final Cannon cannon = new Cannon(Team.CHO, new Position(8, 1));
        final Soldier soldier = new Soldier(Team.CHO, new Position(7, 1));
        final Guard guard = new Guard(Team.CHO, new Position(6, 1));

        final Position arrivalPosition = new Position(5, 1);

        // When & Then
        assertThatThrownBy(() -> cannon.checkMovement(arrivalPosition, Team.CHO, new Pieces(Set.of(
                cannon, soldier, guard))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
    }

    @Test
    void 포는_포를_뛰어넘을_수_없다() {
        // Given
        final Cannon cannon = new Cannon(Team.CHO, new Position(8, 1));
        final Cannon middleCannon = new Cannon(Team.CHO, new Position(7, 1));

        final Position arrivalPosition = new Position(5, 1);

        // When & Then
        assertThatThrownBy(() -> cannon.checkMovement(arrivalPosition, Team.CHO, new Pieces(Set.of(
                cannon, middleCannon))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 포는 포끼리 뛰어넘거나 잡을 수 없습니다.");
    }

    @Test
    void 포는_포를_잡을_수_없다() {
        // Given
        final Cannon startCannon = new Cannon(Team.CHO, new Position(8, 1));
        final Soldier soldier = new Soldier(Team.CHO, new Position(7, 1));
        Position arrivalPosition = new Position(6, 1);
        final Cannon arrivalCannon = new Cannon(Team.CHO, arrivalPosition);

        // When & Then
        assertThatThrownBy(() -> startCannon.checkMovement(arrivalPosition, Team.CHO, new Pieces(Set.of(
                startCannon, soldier, arrivalCannon))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 포는 포끼리 뛰어넘거나 잡을 수 없습니다.");
    }


    @Test
    void 포는_한_번에_여러_방향으로_움직일_수_없다() {
        // Given
        final Position currentPosition = new Position(3, 3);
        final Position middlePosition = new Position(4, 3);
        final Position arrivalPosition = new Position(5, 4);

        // When & Then
        assertThatThrownBy(() -> cannon.checkMovement(arrivalPosition, Team.CHO, new Pieces(Set.of(cannon,
                new Soldier(Team.CHO, currentPosition), new Soldier(Team.CHO, middlePosition)))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 적절한 움직임이 아닙니다.");
    }
}
