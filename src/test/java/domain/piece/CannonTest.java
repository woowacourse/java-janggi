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

public class CannonTest {

    @DisplayName("포는 현재 위치에서 한 방향으로 목적지에 도착할 수 있다면 예외를 발생시키지 않는다")
    @Test
    void validateArrival1() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(1, 2);

        Piece cannon = new Cannon(CHO);
        // when & then
        assertThatCode(
                () -> cannon.validateArrival(current, destination)
        ).doesNotThrowAnyException();
    }

    @DisplayName("포는 현재 위치에서 한 방향으로 목적지에 도착할 수 없다면 예외를 발생시킨다")
    @Test
    void validateArrival2() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(2, 2);

        Piece cannon = new Cannon(HAN);
        // when & then
        assertThatThrownBy(() -> {
            cannon.validateArrival(current, destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포 현재 위치에서 목표 좌표까지 이동하는 모든 경로를 반환한다")
    @Test
    void extractIntermediatePath() {
        // given
        BoardLocation current = new BoardLocation(1, 1);
        BoardLocation destination = new BoardLocation(4, 1);

        Piece cannon = new Cannon(HAN);
        // when
        List<BoardLocation> allPath = cannon.extractIntermediatePath(current, destination);

        // then
        assertThat(allPath).containsAll(List.of(new BoardLocation(2, 1), new BoardLocation(3, 1)));
    }

    @DisplayName("이동경로에 포가 아닌 기물이 2개 이상이라면 예외를 발생시킨다")
    @Test
    void validateMovePath1() {
        // given
        List<Piece> pieces = List.of(new Pawn(HAN), new Horse(HAN));
        Piece cannon = new Cannon(HAN);

        assertThatThrownBy(() -> {
            cannon.validateMovePath(pieces);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동경로에 포가 아닌 기물이 0개라면 예외를 발생시킨다")
    @Test
    void validateMovePath2() {
        // given
        List<Piece> pieces = List.of();
        Piece cannon = new Cannon(HAN);

        // when & then
        assertThatThrownBy(() -> {
            cannon.validateMovePath(pieces);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동경로에 기물이 1개인데, 해당 기물이 포인경우 예외를 발생시킨다")
    @Test
    void validateMovePath3() {
        // given
        List<Piece> pieces = List.of(new Cannon(HAN));
        Piece cannon = new Cannon(HAN);

        // when & then
        assertThatThrownBy(() -> {
            cannon.validateMovePath(pieces);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이동경로에 포가 아닌 기물이 1개라면 예외가 발생하지 않는다")
    @Test
    void validateMovePath4() {
        // given
        List<Piece> pieces = List.of(new Pawn(HAN));
        Piece cannon = new Cannon(HAN);

        // when & then
        assertThatCode(
                () -> cannon.validateMovePath(pieces)
        ).doesNotThrowAnyException();
    }

    @DisplayName("목표 위치에 포가 있다면 예외를 발생시킨다")
    @Test
    void validateKillable1() {
        // given
        Piece destination = new Cannon(HAN);
        Piece cannon = new Cannon(HAN);

        // when & then
        assertThatThrownBy(() -> {
            cannon.validateKillable(destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("목표 위치에 아군 기물이 있다면 예외를 발생시킨다")
    @Test
    void validateKillable2() {
        // given
        Piece destination = new Pawn(HAN);
        Piece cannon = new Cannon(HAN);

        // when & then
        assertThatThrownBy(() -> {
            cannon.validateKillable(destination);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("목표 위치에 적군 기물이 있다면 예외를 발생시킨다")
    @Test
    void validateKillable3() {
        // given
        Piece destination = new Pawn(CHO);
        Piece cannon = new Cannon(HAN);

        // when & then
        assertThatCode(
                () -> cannon.validateKillable(destination)
        ).doesNotThrowAnyException();
    }

    @DisplayName("(4,3)궁성 좌표에서 (6,1)궁성 대각선 이동 가능")
    @Test
    void validateKillable4() {
        // give n
        BoardLocation current = new BoardLocation(4, 3);
        BoardLocation destination = new BoardLocation(6, 1);
        Piece cannon = new Cannon(HAN);
        // when & then
        assertThatCode(()->
                cannon.validateArrival(current, destination)
        ).doesNotThrowAnyException();
    }

    @DisplayName("한나라 궁성에서 (4,3) 좌표에서 (6,1) 좌표 대각선으로 2칸 이동했을 경우 이동경로 1을 반환한다 ")
    @Test
    void extractIntermediatePath2() {
        // give n
        BoardLocation current = new BoardLocation(4, 3);
        BoardLocation destination = new BoardLocation(6, 1);
        Piece cannon = new Cannon(HAN);
        // when & then
        List<BoardLocation> allPath = cannon.extractIntermediatePath(current, destination);

        assertThat(allPath.size()).isEqualTo(1);
    }

    @DisplayName("한나라 궁성에서 (6,3) 좌표에서 (4,1) 좌표대각선으로 2칸 이동했을 경우 이동경로 1을 반환한다 ")
    @Test
    void extractIntermediatePath3() {
        // give n
        BoardLocation current = new BoardLocation(6, 3);
        BoardLocation destination = new BoardLocation(4, 1);
        Piece cannon = new Cannon(HAN);
        // when & then
        List<BoardLocation> allPath = cannon.extractIntermediatePath(current, destination);

        assertThat(allPath.size()).isEqualTo(1);
    }

    @DisplayName("한나라 궁성에서 (4,1) 좌표에서 (6,3) 좌표대각선으로 2칸 이동했을 경우 이동경로 1을 반환한다 ")
    @Test
    void extractIntermediatePath4() {
        // give n
        BoardLocation current = new BoardLocation(4, 1);
        BoardLocation destination = new BoardLocation(6, 3);
        Piece cannon = new Cannon(HAN);
        // when & then
        List<BoardLocation> allPath = cannon.extractIntermediatePath(current, destination);

        assertThat(allPath.size()).isEqualTo(1);
    }

    @DisplayName("한나라 궁성에서 (6,1) 좌표에서 (4,3) 좌표대각선으로 2칸 이동했을 경우 이동경로 1을 반환한다 ")
    @Test
    void extractIntermediatePath5() {
        // give n
        BoardLocation current = new BoardLocation(6, 1);
        BoardLocation destination = new BoardLocation(4, 3);
        Piece cannon = new Cannon(HAN);
        // when & then
        List<BoardLocation> allPath = cannon.extractIntermediatePath(current, destination);

        assertThat(allPath.size()).isEqualTo(1);
    }
}
