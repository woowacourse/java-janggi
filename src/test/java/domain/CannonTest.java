package domain;

import static domain.Team.CHO;
import static domain.Team.DEFAULT;
import static domain.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Cannon;
import domain.piece.Horse;
import domain.piece.Pawn;
import domain.piece.Piece;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CannonTest {

    private Cannon cannon = new Cannon(DEFAULT);

    @DisplayName("포는 현재 위치에서 한 방향으로 목적지에 도착할 수 있다면 true를 반환한다")
    @Test
    void test() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(1, 2);

        // when
        boolean isMovable = cannon.isMovable(current, destination);

        // then
        assertThat(isMovable).isTrue();
    }

    @DisplayName("포는 현재 위치에서 한 방향으로 목적지에 도착할 수 없다면 false를 반환한다")
    @Test
    void test2() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(2, 2);

        // when
        boolean isMovable = cannon.isMovable(current, destination);

        // then
        assertThat(isMovable).isFalse();
    }

    @DisplayName("포 현재 위치에서 목표 좌표까지 이동하는 모든 경로를 반환한다")
    @Test
    void test3() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(4, 1);

        // when
        List<BoardLocation> allPath = cannon.createAllPath(current, destination);

        // then
        assertThat(allPath).containsAll(List.of(new BoardLocation(2, 1), new BoardLocation(3, 1)));
    }

    @DisplayName("이동경로에 포가 아닌 기물이 2개 이상이라면 false를 반환한다")
    @Test
    void test6() {
        // given
        List<Piece> pieces = List.of(new Pawn(DEFAULT), new Horse(DEFAULT));
        Piece cannon = new Cannon(DEFAULT);

        // when
        boolean canArrive = cannon.canArrive(pieces);

        //then
        assertThat(canArrive).isFalse();
    }

    @DisplayName("이동경로에 포가 아닌 기물이 0개라면 false를 반환한다")
    @Test
    void test7() {
        // given
        List<Piece> pieces = List.of();
        Piece cannon = new Cannon(DEFAULT);

        // when
        boolean canArrive = cannon.canArrive(pieces);

        //then
        assertThat(canArrive).isFalse();
    }

    @DisplayName("이동경로에 기물이 1개인데, 해당 기물이 포인경우 false를 반환한다")
    @Test
    void test8() {
        // given
        List<Piece> pieces = List.of(new Cannon(DEFAULT));
        Piece cannon = new Cannon(DEFAULT);

        // when
        boolean canArrive = cannon.canArrive(pieces);

        //then
        assertThat(canArrive).isFalse();
    }

    @DisplayName("이동경로에 포가 아닌 기물이 1개라면 true를 반환한다")
    @Test
    void test9() {
        // given
        List<Piece> pieces = List.of(new Pawn(DEFAULT));
        Piece cannon = new Cannon(DEFAULT);

        // when
        boolean canArrive = cannon.canArrive(pieces);

        //then
        assertThat(canArrive).isTrue();
    }

    @DisplayName("목표 위치에 포가 있다면 false를 반환한다")
    @Test
    void test10() {
        // given
        Piece destination = new Cannon(DEFAULT);
        Piece cannon = new Cannon(DEFAULT);

        // when
        boolean canArrive = cannon.canDestination(destination);

        //then
        assertThat(canArrive).isFalse();
    }

    @DisplayName("목표 위치에 아군 기물이 있다면 false를 반환한다")
    @Test
    void test11() {
        // given
        Piece destination = new Pawn(HAN);
        Piece cannon = new Cannon(HAN);

        // when
        boolean canArrive = cannon.canDestination(destination);

        //then
        assertThat(canArrive).isFalse();
    }

    @DisplayName("목표 위치에 적군 기물이 있다면 true를 반환한다")
    @Test
    void test12() {
        // given
        Piece destination = new Pawn(CHO);
        Piece cannon = new Cannon(HAN);

        // when
        boolean canArrive = cannon.canDestination(destination);

        //then
        assertThat(canArrive).isTrue();
    }
}
