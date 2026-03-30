package domain.position;

import domain.board.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositionTest {
    @Test
    @DisplayName("장기판안에 맞는 좌표를 생성할 수 있다.")
    void 장기판에_좌표_생성_테스트() {
        Position position = Position.of(1, 2);

        assertThat(position.getRow()).isEqualTo(1);
        assertThat(position.getColumn()).isEqualTo(2);
    }

    @Test
    @DisplayName("장기판안에 맞지 않는 좌표를 생성할 시 예외가 발생한다")
    void 장기판_좌표_생성_실패_테스트() {
        assertThatThrownBy(() -> Position.of(10, 11))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("(5,5)에서 단위 방향(UP)이 들어오면 (6,5)를 반환한다")
    void 기존좌표에서_단위방향을_더한_새로운_좌표_결과_테스트_1(){
        Position position = Position.of(5, 5);
        Direction direction = Direction.UP;

        Position newPosition = position.append(direction);

        assertThat(newPosition.getRow()).isEqualTo(6);
        assertThat(newPosition.getColumn()).isEqualTo(5);
    }

    @Test
    @DisplayName("(5,5)에서 단위 방향(UP_RIGHT)이 들어오면 (6,6)를 반환한다")
    void 기존좌표에서_단위방향을_더한_새로운_좌표_결과_테스트_2(){
        Position position = Position.of(5, 5);
        Direction direction = Direction.UP_RIGHT;

        Position newPosition = position.append(direction);

        assertThat(newPosition.getRow()).isEqualTo(6);
        assertThat(newPosition.getColumn()).isEqualTo(6);
    }

    @Test
    @DisplayName("(5,5)에서 단위 방향(RIGHT)이 들어오면 (5,6)를 반환한다")
    void 기존좌표에서_단위방향을_더한_새로운_좌표_결과_테스트_3(){
        Position position = Position.of(5, 5);
        Direction direction = Direction.RIGHT;

        Position newPosition = position.append(direction);

        assertThat(newPosition.getRow()).isEqualTo(5);
        assertThat(newPosition.getColumn()).isEqualTo(6);
    }

    @Test
    @DisplayName("(5,5)에서 단위 방향(DOWN_RIGHT)이 들어오면 (4,6)를 반환한다")
    void 기존좌표에서_단위방향을_더한_새로운_좌표_결과_테스트_4(){
        Position position = Position.of(5, 5);
        Direction direction = Direction.DOWN_RIGHT;

        Position newPosition = position.append(direction);

        assertThat(newPosition.getRow()).isEqualTo(4);
        assertThat(newPosition.getColumn()).isEqualTo(6);
    }

    @Test
    @DisplayName("(5,5)에서 단위 방향(DOWN)이 들어오면 (4,5)를 반환한다")
    void 기존좌표에서_단위방향을_더한_새로운_좌표_결과_테스트_5(){
        Position position = Position.of(5, 5);
        Direction direction = Direction.DOWN;

        Position newPosition = position.append(direction);

        assertThat(newPosition.getRow()).isEqualTo(4);
        assertThat(newPosition.getColumn()).isEqualTo(5);
    }

    @Test
    @DisplayName("(5,5)에서 단위 방향(DOWN_LEFT)이 들어오면 (4,4)를 반환한다")
    void 기존좌표에서_단위방향을_더한_새로운_좌표_결과_테스트_6(){
        Position position = Position.of(5, 5);
        Direction direction = Direction.DOWN_LEFT;

        Position newPosition = position.append(direction);

        assertThat(newPosition.getRow()).isEqualTo(4);
        assertThat(newPosition.getColumn()).isEqualTo(4);
    }

    @Test
    @DisplayName("(5,5)에서 단위 방향(LEFT)이 들어오면 (5,4)를 반환한다")
    void 기존좌표에서_단위방향을_더한_새로운_좌표_결과_테스트_7(){
        Position position = Position.of(5, 5);
        Direction direction = Direction.LEFT;

        Position newPosition = position.append(direction);

        assertThat(newPosition.getRow()).isEqualTo(5);
        assertThat(newPosition.getColumn()).isEqualTo(4);
    }

    @Test
    @DisplayName("(5,5)에서 단위 방향(UP_LEFT)이 들어오면 (6,4)를 반환한다")
    void 기존좌표에서_단위방향을_더한_새로운_좌표_결과_테스트_8(){
        Position position = Position.of(5, 5);
        Direction direction = Direction.UP_LEFT;

        Position newPosition = position.append(direction);

        assertThat(newPosition.getRow()).isEqualTo(6);
        assertThat(newPosition.getColumn()).isEqualTo(4);
    }

    @Test
    @DisplayName("(4,4)와 (5,5)의 거리를 구할 수 있다.")
    void 출발지_좌표에서_목적지_좌표까지의_거리_구하기_테스트_1(){
        Position startPosition = Position.of(4, 4);
        Position endPosition = Position.of(5, 5);

        Coordinate distance = endPosition.minus(startPosition);

        assertThat(distance.row()).isEqualTo(1);
        assertThat(distance.column()).isEqualTo(1);
    }

    @Test
    @DisplayName("(4,6)와 (5,5)의 거리를 구할 수 있다.")
    void 출발지_좌표에서_목적지_좌표까지의_거리_구하기_테스트_2(){
        Position startPosition = Position.of(4, 6);
        Position endPosition = Position.of(5, 5);

        Coordinate distance = endPosition.minus(startPosition);

        assertThat(distance.row()).isEqualTo(1);
        assertThat(distance.column()).isEqualTo(-1);
    }

    @Test
    @DisplayName("(6,4)와 (5,5)의 거리를 구할 수 있다.")
    void 출발지_좌표에서_목적지_좌표까지의_거리_구하기_테스트_3(){
        Position startPosition = Position.of(6, 4);
        Position endPosition = Position.of(5, 5);

        Coordinate distance = endPosition.minus(startPosition);

        assertThat(distance.row()).isEqualTo(-1);
        assertThat(distance.column()).isEqualTo(1);
    }

    @Test
    @DisplayName("(6,6)와 (5,5)의 거리를 구할 수 있다.")
    void 출발지_좌표에서_목적지_좌표까지의_거리_구하기_테스트_4(){
        Position startPosition = Position.of(6, 6);
        Position endPosition = Position.of(5, 5);

        Coordinate distance = endPosition.minus(startPosition);

        assertThat(distance.row()).isEqualTo(-1);
        assertThat(distance.column()).isEqualTo(-1);
    }
}
