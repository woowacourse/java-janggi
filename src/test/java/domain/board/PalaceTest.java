package domain.board;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PalaceTest {

    @Test
    @DisplayName("궁성 안에서 대각선으로 움직일 수 있는 지점에 있다면 대각선으로 움직일 수 있는지 테스트")
    void isDiagonalMoveAllowed() {
        BoardLocation current = new BoardLocation(4, 1);
        BoardLocation destination = new BoardLocation(5, 2);

        assertThat(Palace.isDiagonalMoveAllowed(current, destination)).isTrue();
    }

    @Test
    @DisplayName("궁성 안에서 대각선으로 움직일 수 있는 지점에 있다면 대각선으로 움직일 수 있는지 테스트")
    void test() {
        BoardLocation current = new BoardLocation(4, 2);
        BoardLocation destination = new BoardLocation(5, 3);

        assertThat(Palace.isDiagonalMoveAllowed(current, destination)).isFalse();
    }

    @Test
    @DisplayName("현재 위치가 한의 궁성 밖에 있다면 예외 처리하는지 테스트")
    void test1() {
        BoardLocation current = new BoardLocation(5,5);
        BoardLocation destination = new BoardLocation(5,3);
        Team team = Team.HAN;
        Palace palace = team.getPalace();
        assertThatThrownBy(() -> {
            palace.validateInPalace(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("현재 위치는 한의 궁성 안에 있고 목적지가 궁성 밖이라면 예외 처리하는지 테스트")
    void test3() {
        BoardLocation current = new BoardLocation(4,3);
        BoardLocation destination = new BoardLocation(3,3);
        Team team = Team.HAN;
        Palace palace = team.getPalace();
        assertThatThrownBy(() -> {
            palace.validateInPalace(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("현재 위치가 초의 궁성 밖에 있다면 예외 처리하는지 테스트")
    void test2() {
        BoardLocation current = new BoardLocation(3,8);
        BoardLocation destination = new BoardLocation(4,8);
        Team team = Team.CHO;
        Palace palace = team.getPalace();
        assertThatThrownBy(() -> {
            palace.validateInPalace(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("현재 위치가 초의 궁성 밖에 있다면 예외 처리하는지 테스트")
    void test4() {
        BoardLocation current = new BoardLocation(4,8);
        BoardLocation destination = new BoardLocation(3,8);
        Team team = Team.CHO;
        Palace palace = team.getPalace();
        assertThatThrownBy(() -> {
            palace.validateInPalace(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}