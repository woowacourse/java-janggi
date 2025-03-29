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

public class ElephantTest {

    Elephant elephant = new Elephant(HAN);

    @DisplayName("상은 상하좌우 한 칸 그리고 대각선 두 칸 움직일 때의 목적지 좌표로 위치 가능하다면 예와를 발생시키지않는다")
    @Test
    void test() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(3, 4);

        assertThatCode(
                () -> elephant.validateArrival(current, destination)
        ).doesNotThrowAnyException();

    }

    @DisplayName("상은 현재 위치에서 한 방향으로 목적지에 도착할 수 없다면 예외를 발생시킨다.")
    @Test
    void test2() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(3, 5);

        // when & then
        assertThatThrownBy(() -> {
            elephant.validateArrival(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상의 현재 위치에서 목표 좌표까지 이동하는 모든 경로를 반환한다")
    @Test
    void test3() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(3, 4);

        // when
        List<BoardLocation> allPath = elephant.createAllPath(current, destination);

        // then
        assertThat(allPath).containsAll(List.of(new BoardLocation(1, 2), new BoardLocation(2, 3)));
    }

    @DisplayName("이동 경로에 기물이 있다면 예외를 발생시킨다.")
    @Test
    void test4() {
        // given
        List<Piece> pathPiece = List.of(new Pawn(Team.HAN));
        Piece piece = new Elephant(Team.HAN);
        // when & then
        assertThatThrownBy(() -> {
            piece.validateMovePath(pathPiece);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동 경로에 기물이 없으면 예와를 발생시키지않는다")
    @Test
    void test5() {
        // given
        List<Piece> pathPiece = List.of();
        Piece piece = new Elephant(Team.HAN);

        // when & then
        assertThatCode(
                () -> piece.validateMovePath(pathPiece)
        ).doesNotThrowAnyException();
    }

    @DisplayName("목표 위치에 아군 기물이 있다면 예외를 발생시킨다.")
    @Test
    void test11() {
        // given
        Piece destination = new Pawn(HAN);
        Piece start = new Elephant(HAN);

        // when & then
        assertThatThrownBy(() -> {
            start.validateKillable(destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("목표 위치에 적군 기물이 있다면 예와를 발생시키지않는다")
    @Test
    void test12() {
        // given
        Piece destination = new Pawn(CHO);
        Piece start = new Elephant(HAN);

        // when & then
        assertThatCode(
                () -> start.validateKillable(destination)
        ).doesNotThrowAnyException();
    }
}
