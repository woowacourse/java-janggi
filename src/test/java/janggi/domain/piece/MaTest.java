package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.team.TeamType;
import janggi.dto.MoveRoute;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MaTest {

    @Test
    @DisplayName("마는 여덟 방향의 L자 이동을 할 수 있다.")
    void isValidMovePattern() {
        // given
        Ma ma = new Ma(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(ma.isValidMovePattern(4, 4, 5, 6)).isTrue(),
            () -> assertThat(ma.isValidMovePattern(4, 4, 3, 6)).isTrue(),
            () -> assertThat(ma.isValidMovePattern(4, 4, 5, 2)).isTrue(),
            () -> assertThat(ma.isValidMovePattern(4, 4, 3, 2)).isTrue(),
            () -> assertThat(ma.isValidMovePattern(4, 4, 2, 5)).isTrue(),
            () -> assertThat(ma.isValidMovePattern(4, 4, 2, 3)).isTrue(),
            () -> assertThat(ma.isValidMovePattern(4, 4, 6, 5)).isTrue(),
            () -> assertThat(ma.isValidMovePattern(4, 4, 6, 3)).isTrue()
        );
    }

    @Test
    @DisplayName("마는 직선이나 대각선으로 이동할 수 없고 제자리 이동도 할 수 없다.")
    void cannotMoveInvalidPattern() {
        // given
        Ma ma = new Ma(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(ma.isValidMovePattern(4, 4, 4, 5)).isFalse(),
            () -> assertThat(ma.isValidMovePattern(4, 4, 5, 5)).isFalse(),
            () -> assertThat(ma.isValidMovePattern(4, 4, 4, 4)).isFalse()
        );
    }

    @Test
    @DisplayName("이동 경로의 첫 칸이 비어 있으면 마는 이동할 수 있다.")
    void isValidPathWhenIntermediatePositionIsEmpty() {
        // given
        Ma ma = new Ma(TeamType.CHU);
        MoveRoute moveRoute = new MoveRoute(List.of(), Optional.empty());

        // when
        boolean result = ma.canMove(moveRoute);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("이동 경로의 첫 칸이 막혀 있으면 마는 이동할 수 없다.")
    void cannotMoveWhenIntermediatePositionIsBlocked() {
        // given
        Ma ma = new Ma(TeamType.CHU);
        MoveRoute moveRoute = new MoveRoute(List.of(PieceType.JOL), Optional.empty());

        // when
        boolean result = ma.canMove(moveRoute);

        // then
        assertThat(result).isFalse();
    }
}
