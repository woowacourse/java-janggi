package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.team.TeamType;
import janggi.dto.MoveRoute;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SangTest {

    @Test
    @DisplayName("상은 여덟 방향으로 이동할 수 있다.")
    void isValidMovePattern() {
        // given
        Sang sang = new Sang(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(sang.isValidMovePattern(4, 4, 7, 6)).isTrue(),
            () -> assertThat(sang.isValidMovePattern(4, 4, 1, 6)).isTrue(),
            () -> assertThat(sang.isValidMovePattern(4, 4, 7, 2)).isTrue(),
            () -> assertThat(sang.isValidMovePattern(4, 4, 1, 2)).isTrue(),
            () -> assertThat(sang.isValidMovePattern(4, 4, 6, 7)).isTrue(),
            () -> assertThat(sang.isValidMovePattern(4, 4, 6, 1)).isTrue(),
            () -> assertThat(sang.isValidMovePattern(4, 4, 2, 7)).isTrue(),
            () -> assertThat(sang.isValidMovePattern(4, 4, 2, 1)).isTrue()
        );
    }

    @Test
    @DisplayName("상은 잘못된 이동 패턴이나 제자리 이동을 할 수 없다.")
    void cannotMoveInvalidPattern() {
        // given
        Sang sang = new Sang(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(sang.isValidMovePattern(4, 4, 4, 5)).isFalse(),
            () -> assertThat(sang.isValidMovePattern(4, 4, 5, 5)).isFalse(),
            () -> assertThat(sang.isValidMovePattern(4, 4, 4, 4)).isFalse()
        );
    }

    @Test
    @DisplayName("이동 경로가 비어 있으면 상은 이동할 수 있다.")
    void isObstaclesNotExistWhenIntermediatePositionsAreEmpty() {
        // given
        Sang sang = new Sang(TeamType.CHU);
        MoveRoute moveRoute = new MoveRoute(List.of(), Optional.empty());

        // when
        boolean result = sang.canMove(moveRoute);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("이동 경로 중 하나라도 막혀 있으면 상은 이동할 수 없다.")
    void cannotMoveWhenIntermediatePositionIsBlocked() {
        // given
        Sang sang = new Sang(TeamType.CHU);
        MoveRoute moveRoute = new MoveRoute(
            List.of(PieceType.JOL),
            Optional.empty()
        );

        // when
        boolean result = sang.canMove(moveRoute);

        // then
        assertThat(result).isFalse();
    }
}
