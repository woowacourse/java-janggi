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

class ChariotTest {

    Chariot chariot = new Chariot(HAN);

    @DisplayName("차(車)는 현재 위치에서 한 방향으로 목적지에 도착할 수 있다면 예외를 발생시키지 않는다")
    @Test
    void test() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(1, 2);

        Piece cannon = new Cannon(HAN);
        // when & then
        assertThatCode(
                () -> cannon.validateArrival(current, destination)
        ).doesNotThrowAnyException();
    }

    @DisplayName("차(車)는 현재 위치에서 한 방향으로 목적지에 도착할 수 없다면 예외를 발생시킨다")
    @Test
    void test2() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(2, 2);

        Piece cannon = new Cannon(HAN);
        // when & then
        assertThatThrownBy(() -> {
            cannon.validateArrival(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("차 현재 위치에서 목표 좌표까지 이동하는 모든 경로를 반환한다")
    @Test
    void test3() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(4, 1);

        // when
        List<BoardLocation> allPath = chariot.createAllPath(current, destination);

        // then
        assertThat(allPath).containsAll(List.of(new BoardLocation(2, 1), new BoardLocation(3, 1)));
    }

    @DisplayName("이동 경로에 기물이 있다면 예외를 발생시킨다")
    @Test
    void test4() {
        // given
        List<Piece> pathPiece = List.of(new Pawn(HAN));
        Piece piece = new Chariot(HAN);

        // when & then
        assertThatThrownBy(() -> {
            piece.validateMovePath(pathPiece);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동 경로에 기물이 없으면 예외를 발생시키지 않는다")
    @Test
    void test5() {
        // given
        List<Piece> pathPiece = List.of();
        Piece piece = new Chariot(HAN);

        // when & then
        assertThatCode(
                () -> piece.validateMovePath(pathPiece)
        ).doesNotThrowAnyException();
    }

    @DisplayName("목표 위치에 아군 기물이 있다면 예외를 발생시킨다")
    @Test
    void test11() {
        // given
        Piece destination = new Pawn(HAN);
        Piece start = new Chariot(HAN);

        // when & then
        assertThatThrownBy(() -> {
            start.validateKillable(destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("목표 위치에 적군 기물이 있다면 예외를 발생시키지 않는다")
    @Test
    void test12() {
        // given
        Piece destination = new Pawn(CHO);
        Piece start = new Chariot(HAN);

        // when & then
        assertThatCode(
                () -> start.validateKillable(destination)
        ).doesNotThrowAnyException();
    }

    @DisplayName("궁성 좌표라면 궁성 대각선으로 이동 가능")
    @Test
    void test13() {
        // give n
        BoardLocation current = new BoardLocation(4, 3);
        BoardLocation destination = new BoardLocation(6, 1);
        Piece chariot = new Chariot(HAN);
        // when & then
        assertThatCode(()->
                chariot.validateArrival(current, destination)
        ).doesNotThrowAnyException();
    }

    @DisplayName("한나라 궁성에서 (4,3) 좌표에서 (6,1) 좌표 대각선으로 2칸 이동했을 경우 이동경로 1을 반환한다 ")
    @Test
    void test14() {
        // give n
        BoardLocation current = new BoardLocation(4, 3);
        BoardLocation destination = new BoardLocation(6, 1);
        Piece chariot = new Chariot(HAN);
        // when & then
        List<BoardLocation> allPath = chariot.createAllPath(current, destination);

        assertThat(allPath.size()).isEqualTo(1);
    }

    @DisplayName("한나라 궁성에서 (6,3) 좌표에서 (4,1) 좌표대각선으로 2칸 이동했을 경우 이동경로 1을 반환한다 ")
    @Test
    void test15() {
        // give n
        BoardLocation current = new BoardLocation(6, 3);
        BoardLocation destination = new BoardLocation(4, 1);
        Piece chariot = new Chariot(HAN);
        // when & then
        List<BoardLocation> allPath = chariot.createAllPath(current, destination);

        assertThat(allPath.size()).isEqualTo(1);
    }

    @DisplayName("한나라 궁성에서 (4,1) 좌표에서 (6,3) 좌표대각선으로 2칸 이동했을 경우 이동경로 1을 반환한다 ")
    @Test
    void test16() {
        // give n
        BoardLocation current = new BoardLocation(4, 1);
        BoardLocation destination = new BoardLocation(6, 3);
        Piece chariot = new Chariot(HAN);
        // when & then
        List<BoardLocation> allPath = chariot.createAllPath(current, destination);

        assertThat(allPath.size()).isEqualTo(1);
    }

    @DisplayName("한나라 궁성에서 (6,1) 좌표에서 (4,3) 좌표대각선으로 2칸 이동했을 경우 이동경로 1을 반환한다 ")
    @Test
    void test17() {
        // give n
        BoardLocation current = new BoardLocation(6, 1);
        BoardLocation destination = new BoardLocation(4, 3);
        Piece chariot = new Chariot(HAN);
        // when & then
        List<BoardLocation> allPath = chariot.createAllPath(current, destination);

        assertThat(allPath.size()).isEqualTo(1);
    }
}
