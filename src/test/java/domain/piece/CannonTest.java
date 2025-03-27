package domain.piece;

import static domain.piece.Team.CHO;
import static domain.piece.Team.DEFAULT;
import static domain.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.BoardLocation;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CannonTest {

    @DisplayName("포는 현재 위치에서 한 방향으로 목적지에 도착할 수 있다면 예외를 발생시키지 않는다")
    @Test
    void test() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(1, 2);

        Piece cannon = new Cannon(DEFAULT);
        // when & then
        assertThatCode(
                () -> cannon.validateArrival(current, destination)
        ).doesNotThrowAnyException();
    }

    @DisplayName("포는 현재 위치에서 한 방향으로 목적지에 도착할 수 없다면 예외를 발생시킨다")
    @Test
    void test2() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(2, 2);

        Piece cannon = new Cannon(DEFAULT);
        // when & then
        assertThatThrownBy(() -> {
            cannon.validateArrival(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포 현재 위치에서 목표 좌표까지 이동하는 모든 경로를 반환한다")
    @Test
    void test3() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(4, 1);

        Piece cannon = new Cannon(DEFAULT);
        // when
        List<BoardLocation> allPath = cannon.createAllPath(current, destination);

        // then
        assertThat(allPath).containsAll(List.of(new BoardLocation(2, 1), new BoardLocation(3, 1)));
    }

    @DisplayName("이동경로에 포가 아닌 기물이 2개 이상이라면 예외를 발생시킨다")
    @Test
    void test6() {
        // given
        List<Piece> pieces = List.of(new Pawn(DEFAULT), new Horse(DEFAULT));
        Piece cannon = new Cannon(DEFAULT);

        assertThatThrownBy(() -> {
            cannon.validateMovePath(pieces);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동경로에 포가 아닌 기물이 0개라면 예외를 발생시킨다")
    @Test
    void test7() {
        // given
        List<Piece> pieces = List.of();
        Piece cannon = new Cannon(DEFAULT);

        // when & then
        assertThatThrownBy(() -> {
            cannon.validateMovePath(pieces);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동경로에 기물이 1개인데, 해당 기물이 포인경우 예외를 발생시킨다")
    @Test
    void test8() {
        // given
        List<Piece> pieces = List.of(new Cannon(DEFAULT));
        Piece cannon = new Cannon(DEFAULT);

        // when & then
        assertThatThrownBy(() -> {
            cannon.validateMovePath(pieces);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동경로에 포가 아닌 기물이 1개라면 예외가 발생하지 않는다")
    @Test
    void test9() {
        // given
        List<Piece> pieces = List.of(new Pawn(DEFAULT));
        Piece cannon = new Cannon(DEFAULT);

        // when & then
        assertThatCode(
                () -> cannon.validateMovePath(pieces)
        ).doesNotThrowAnyException();
    }

    @DisplayName("목표 위치에 포가 있다면 예외를 발생시킨다")
    @Test
    void test10() {
        // given
        Piece destination = new Cannon(DEFAULT);
        Piece cannon = new Cannon(DEFAULT);

        // when & then
        assertThatThrownBy(() -> {
            cannon.validateKillable(destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("목표 위치에 아군 기물이 있다면 예외를 발생시킨다")
    @Test
    void test11() {
        // given
        Piece destination = new Pawn(HAN);
        Piece cannon = new Cannon(HAN);

        // when & then
        assertThatThrownBy(() -> {
            cannon.validateKillable(destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("목표 위치에 적군 기물이 있다면 true를 반환한다")
    @Test
    void test12() {
        // given
        Piece destination = new Pawn(CHO);
        Piece cannon = new Cannon(HAN);

        // when & then
        assertThatCode(
                () -> cannon.validateKillable(destination)
        ).doesNotThrowAnyException();
    }
}
