package domain;

import static domain.Team.CHO;
import static domain.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Pawn;
import domain.piece.Piece;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PawnTest {
    private Pawn choPawn = new Pawn(Team.CHO);
    private Pawn hanPawn = new Pawn(Team.HAN);

    @DisplayName("졸의 경우 현재 위치에서 출력 기준 상,좌,우 방향으로 한 칸만 이동하여 목적지에 도착할 수 있다면 true를 반환한다")
    @Test
    void test() {
        // given
        BoardLocation current = new BoardLocation(1, 2);
        BoardLocation destination = new BoardLocation(1, 1);

        // when
        boolean isMovable = choPawn.isMovable(current, destination);

        // then
        assertThat(isMovable).isTrue();
    }

    @DisplayName("졸의 경우 현재 위치에서 출력 기준 상,좌,우 방향으로 한 칸만 이동하여 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test2() {
        // given
        BoardLocation current = new BoardLocation(1, 3);
        BoardLocation destination = new BoardLocation(1, 1);

        // when
        boolean isMovable = choPawn.isMovable(current, destination);

        // then
        assertThat(isMovable).isFalse();
    }

    @DisplayName("졸의 경우 현재 위치에서 출력 기준 하방향으로 한 칸만 이동하여 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test3() {
        // given
        BoardLocation current = new BoardLocation(3, 2);
        BoardLocation destination = new BoardLocation(3, 3);

        // when
        boolean isMovable = choPawn.isMovable(current, destination);

        // then
        assertThat(isMovable).isFalse();
    }

    @DisplayName("졸 현재 위치에서 목표 좌표까지 이동하는 모든 경로를 반환한다")
    @Test
    void test4() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(2, 1);

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

        // when
        boolean isMovable = hanPawn.isMovable(current, destination);

        // then
        assertThat(isMovable).isTrue();
    }

    @DisplayName("병의 경우 현재 위치에서 출력 기준 하,좌,우 방향으로 한 칸만 이동하여 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test6() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(1, 3);

        // when
        boolean isMovable = hanPawn.isMovable(current, destination);

        // then
        assertThat(isMovable).isFalse();
    }

    @DisplayName("병의 경우 현재 위치에서 출력 기준 상방향으로 한 칸만 이동하여 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test7() {
        // given
        BoardLocation current = new BoardLocation(3, 3);
        BoardLocation destination = new BoardLocation(3, 2);

        // when
        boolean isMovable = hanPawn.isMovable(current, destination);

        // then
        assertThat(isMovable).isFalse();
    }

    @DisplayName("병 현재 위치에서 목표 좌표까지 이동하는 모든 경로를 반환한다")
    @Test
    void test8() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(2, 1);

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
        // when
        boolean canArrive = piece.canArrive(pathPiece);
        // then
        assertThat(canArrive).isFalse();
    }

    @DisplayName("이동 경로에 기물이 없으면 true를 반환한다")
    @Test
    void test10() {
        // given
        List<Piece> pathPiece = List.of();
        Piece piece = new Pawn(Team.DEFAULT);

        // when
        boolean canArrive = piece.canArrive(pathPiece);

        // then
        assertThat(canArrive).isTrue();
    }

    @DisplayName("목표 위치에 아군 기물이 있다면 false를 반환한다")
    @Test
    void test11() {
        // given
        Piece destination = new Pawn(HAN);
        Piece start = new Pawn(HAN);

        // when
        boolean canArrive = start.canDestination(destination);

        //then
        assertThat(canArrive).isFalse();
    }

    @DisplayName("목표 위치에 적군 기물이 있다면 true를 반환한다")
    @Test
    void test12() {
        // given
        Piece destination = new Pawn(CHO);
        Piece start = new Pawn(HAN);

        // when
        boolean canArrive = start.canDestination(destination);

        //then
        assertThat(canArrive).isTrue();
    }
}
