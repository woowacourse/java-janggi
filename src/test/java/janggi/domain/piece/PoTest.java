package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.team.TeamType;
import janggi.dto.MoveRoute;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PoTest {

    @Test
    @DisplayName("포는 상하좌우로 직선 이동할 수 있다.")
    void isValidMovePatternStraight() {
        // given
        Po po = new Po(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(po.isValidMovePattern(4, 4, 4, 8)).isTrue(),
            () -> assertThat(po.isValidMovePattern(4, 4, 4, 1)).isTrue(),
            () -> assertThat(po.isValidMovePattern(4, 4, 7, 4)).isTrue(),
            () -> assertThat(po.isValidMovePattern(4, 4, 1, 4)).isTrue()
        );
    }

    @Test
    @DisplayName("포는 궁성 밖 대각선이나 제자리로 이동할 수 없다.")
    void cannotMoveInvalidPattern() {
        // given
        Po po = new Po(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(po.isValidMovePattern(4, 4, 5, 5)).isFalse(),
            () -> assertThat(po.isValidMovePattern(4, 4, 4, 4)).isFalse(),
            () -> assertThat(po.isValidMovePattern(4, 2, 5, 1)).isFalse()
        );
    }

    @Test
    @DisplayName("포는 궁성 안에서 대각선 이동할 수 있다.")
    void canMoveDiagonalInsidePalace() {
        // given
        Po po = new Po(TeamType.CHU);

        // when & then
        assertAll(
            () -> assertThat(po.isValidMovePattern(4, 1, 5, 2)).isTrue(),
            () -> assertThat(po.isValidMovePattern(4, 1, 6, 3)).isTrue()
        );
    }

    @Test
    @DisplayName("포는 사이에 기물이 하나만 있으면 이동할 수 있다.")
    void isObstaclesNotExistWhenExactlyOneBridgeExists() {
        // given
        Po po = new Po(TeamType.CHU);
        MoveRoute moveRoute = new MoveRoute(
            List.of(PieceType.JOL),
            Optional.empty()
        );

        // when
        boolean result = po.canMove(moveRoute);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("포는 사이에 기물이 없으면 이동할 수 없다.")
    void cannotMoveWithoutBridge() {
        // given
        Po po = new Po(TeamType.CHU);
        MoveRoute moveRoute = new MoveRoute(List.of(), Optional.empty());

        // when
        boolean result = po.canMove(moveRoute);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("포는 포를 다리로 사용할 수 없고 포를 잡을 수도 없다.")
    void cannotUsePoAsBridgeOrTarget() {
        // given
        Po po = new Po(TeamType.CHU);
        MoveRoute poBridgeRoute = new MoveRoute(
            List.of(PieceType.PO),
            Optional.empty()
        );
        MoveRoute poTargetRoute = new MoveRoute(
            List.of(PieceType.JOL),
            Optional.of(PieceType.PO)
        );

        // when & then
        assertAll(
            () -> assertThat(po.canMove(poBridgeRoute)).isFalse(),
            () -> assertThat(po.canMove(poTargetRoute)).isFalse()
        );
    }

    @Test
    @DisplayName("포는 궁성 대각선에 다리가 하나 있으면 이동할 수 있다.")
    void canMoveDiagonallyInsidePalaceWithBridge() {
        // given
        Po po = new Po(TeamType.CHU);
        MoveRoute moveRoute = new MoveRoute(List.of(PieceType.GUNG), Optional.empty());

        // when
        boolean result = po.canMove(moveRoute);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("포는 궁성 대각선에 다리가 없으면 이동할 수 없다.")
    void cannotMoveDiagonallyInsidePalaceWithoutBridge() {
        // given
        Po po = new Po(TeamType.CHU);
        MoveRoute moveRoute = new MoveRoute(List.of(), Optional.empty());

        // when
        boolean result = po.canMove(moveRoute);

        // then
        assertThat(result).isFalse();
    }

}
