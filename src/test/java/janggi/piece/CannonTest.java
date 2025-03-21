package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.position.Path;
import janggi.position.Position;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CannonTest {

    private Cannon cannon;

    @BeforeEach
    void setUp() {
        cannon = new Cannon(Team.CHO);
    }

    @Test
    void 포는_움직인다() {
        // Given
        final Soldier soldier = new Soldier(Team.CHO);
        final Cannon cannon = new Cannon(soldier.getTeam());

        Position currentPosition = new Position(8, 1);
        final Position middlePiecePosition = new Position(7, 1);
        Position arrivalPosition = new Position(6, 1);

        // When
        Path path = cannon.makePath(currentPosition, arrivalPosition, Map.of(
                currentPosition, cannon,
                middlePiecePosition, soldier
        ));
        System.out.println(path.getPositions());

        // Then
        assertThat(path).isEqualTo(new Path(List.of(middlePiecePosition, arrivalPosition)));
    }

    @Test
    void 포는_한_번에_여러_방향으로_움직일_수_없다() {
        // Given
        Position currentPosition = new Position(3, 3);
        Position middlePosition = new Position(4, 3);
        Position arrivalPosition = new Position(5, 4);

        // When & Then
        assertThatThrownBy(() -> cannon.makePath(currentPosition, arrivalPosition, Map.of(currentPosition, cannon,
                middlePosition, new Soldier(Team.CHO))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 포는 한 방향으로만 이동할 수 있습니다.");
    }
}
