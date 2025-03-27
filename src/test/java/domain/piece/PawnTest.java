package domain.piece;

import static domain.piece.Team.CHO;
import static domain.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.BoardLocation;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PawnTest {

    @DisplayName("졸의 경우 현재 위치에서 출력 기준 상,좌,우 방향으로 한 칸만 이동하여 목적지에 도착할 수 있다면 true를 반환한다")
    @Test
    void test() {
        // given
        BoardLocation current = new BoardLocation(1, 2);
        BoardLocation destination = new BoardLocation(1, 1);

        Pawn choPawn = new Pawn(Team.CHO);
        // when & then
        assertThatCode(
                () -> choPawn.validateArrival(current, destination)
        ).doesNotThrowAnyException();
    }

    @DisplayName("졸의 경우 현재 위치에서 출력 기준 상,좌,우 방향으로 한 칸만 이동하여 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test2() {
        // given
        BoardLocation current = new BoardLocation(1, 3);
        BoardLocation destination = new BoardLocation(1, 1);

        Pawn choPawn = new Pawn(Team.CHO);
        // when & then
        assertThatThrownBy(() -> {
            choPawn.validateArrival(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("졸의 경우 현재 위치에서 출력 기준 하방향으로 한 칸만 이동하여 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test3() {
        // given
        BoardLocation current = new BoardLocation(3, 2);
        BoardLocation destination = new BoardLocation(3, 3);

        Pawn choPawn = new Pawn(Team.CHO);
        // when & then
        assertThatThrownBy(() -> {
            choPawn.validateArrival(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("졸 현재 위치에서 목표 좌표까지 이동하는 모든 경로를 반환한다")
    @Test
    void test4() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(2, 1);

        Pawn choPawn = new Pawn(Team.CHO);
        // when
        List<BoardLocation> allPath = choPawn.createAllPath(current, destination);

        // then
        assertThat(allPath).containsAll(List.of());
    }

    @DisplayName("병의 경우 현재 위치에서 출력 기준 하,좌,우 방향으로 한 칸만 이동하여 목적지에 도착할 수 있다면 true를 반환한다")
    @Test
    void test5() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(1, 2);

        Pawn hanPawn = new Pawn(Team.HAN);

        // when & then
        assertThatCode(
                () -> hanPawn.validateArrival(current, destination)
        ).doesNotThrowAnyException();
    }

    @DisplayName("병의 경우 현재 위치에서 출력 기준 하,좌,우 방향으로 한 칸만 이동하여 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test6() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(1, 3);

        Pawn hanPawn = new Pawn(Team.HAN);

        // when & then
        assertThatThrownBy(() -> {
            hanPawn.validateArrival(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("병의 경우 현재 위치에서 출력 기준 상방향으로 한 칸만 이동하여 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test7() {
        // given
        BoardLocation current = new BoardLocation(3, 3);
        BoardLocation destination = new BoardLocation(3, 2);

        Pawn hanPawn = new Pawn(Team.HAN);

        // when & then
        assertThatThrownBy(() -> {
            hanPawn.validateArrival(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("병 현재 위치에서 목표 좌표까지 이동하는 모든 경로를 반환한다")
    @Test
    void test8() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(2, 1);

        Pawn hanPawn = new Pawn(Team.HAN);
        // when
        List<BoardLocation> allPath = hanPawn.createAllPath(current, destination);

        // then
        assertThat(allPath).containsAll(List.of());
    }

    @DisplayName("이동 경로에 기물이 있다면 false를 반환한다")
    @Test
    void test9() {
        // given
        List<Piece> pathPiece = List.of(new Pawn(Team.DEFAULT));
        Piece piece = new Pawn(Team.DEFAULT);

        // when & then
        assertThatThrownBy(() -> {
            piece.validateMovePath(pathPiece);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동 경로에 기물이 없으면 true를 반환한다")
    @Test
    void test10() {
        // given
        List<Piece> pathPiece = List.of();
        Piece piece = new Pawn(Team.DEFAULT);

        // when & then
        assertThatCode(
                () -> piece.validateMovePath(pathPiece)
        ).doesNotThrowAnyException();
    }

    @DisplayName("목표 위치에 아군 기물이 있다면 false를 반환한다")
    @Test
    void test11() {
        // given
        Piece destination = new Pawn(HAN);
        Piece start = new Pawn(HAN);

        // when & then
        assertThatThrownBy(() -> {
            start.validateKillable(destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("목표 위치에 적군 기물이 있다면 true를 반환한다")
    @Test
    void test12() {
        // given
        Piece destination = new Pawn(CHO);
        Piece start = new Pawn(HAN);

        // when & then
        assertThatCode(
                () -> start.validateKillable(destination)
        ).doesNotThrowAnyException();
    }
}
