package janggi.board;

import janggi.Side;
import janggi.piece.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JanggiBoardTest {

    @Test
    @DisplayName("9 x 10의 빈 장기판 생성 테스트")
    void test1() {
        assertThat(JanggiBoard.initialize().getBoard().size()).isEqualTo(90);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,3", "2,3", "4,3", "6,3", "8,3",
            "0,6", "2,6", "4,6", "6,6", "8,6"
    })
    @DisplayName("졸 초기화 테스트")
    void test2(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(Soldier.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,0", "6,0", "2,9", "6,9"
    })
    @DisplayName("상 초기화 테스트")
    void test4(int x, int y) {

        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(Elephant.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,0", "7,0", "1,9", "7,9"
    })
    @DisplayName("마 초기화 테스트")
    void test5(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(Horse.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,2", "7,2", "1,7", "7,7"
    })
    @DisplayName("포 초기화 테스트")
    void test6(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(Cannon.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,0", "8,0", "0,9", "8,9"
    })
    @DisplayName("차 초기화 테스트")
    void test7(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(Chariot.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "3,9", "5,9", "3,0", "5,0"
    })
    @DisplayName("사 초기화 테스트")
    void test8(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(Guard.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,8", "4,1"
    })
    @DisplayName("궁 초기화 테스트")
    void test9(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(King.class);
    }

    @Test
    @DisplayName("기본 이동 테스트 - 졸")
    void test10() {
        JanggiBoard janggiBoard = JanggiBoard.initialize();

        Position position = new Position(2, 6);
        List<Position> positions = janggiBoard.computeReachableDestination(position);


        assertAll(
                () -> assertThat(positions.size()).isEqualTo(3),
                () -> assertThat(positions).contains(new Position(1, 6)),
                () -> assertThat(positions).contains(new Position(2, 5)),
                () -> assertThat(positions).contains(new Position(3, 6))
        );
    }

    @Test
    @DisplayName("이동 테스트 - 경계의 졸")
    void test11() {
        JanggiBoard janggiBoard = JanggiBoard.initialize();

        Position position = new Position(8, 3);
        List<Position> positions = janggiBoard.computeReachableDestination(position);


        assertAll(
                () -> assertThat(positions.size()).isEqualTo(2),
                () -> assertThat(positions).contains(new Position(7, 3)),
                () -> assertThat(positions).contains(new Position(8, 4))
        );
    }

    @Test
    @DisplayName("상 초기 배치 이동 테스트")
    void test12() {
        JanggiBoard janggiBoard = JanggiBoard.initialize();

        Position position = new Position(2, 9);
        List<Position> positions = janggiBoard.computeReachableDestination(position);

        assertThat(positions.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("마 초기 배치 이동 테스트")
    void test13() {
        JanggiBoard janggiBoard = JanggiBoard.initialize();

        Position position = new Position(1, 9);
        List<Position> positions = janggiBoard.computeReachableDestination(position);

        assertAll(
                () -> assertThat(positions.size()).isEqualTo(2),
                () -> assertThat(positions).contains(new Position(0, 7)),
                () -> assertThat(positions).contains(new Position(2, 7))
        );
    }

    @Test
    @DisplayName("차 초기 배치 이동 테스트")
    void test14() {
        JanggiBoard janggiBoard = JanggiBoard.initialize();

        Position position = new Position(0, 9);
        List<Position> positions = janggiBoard.computeReachableDestination(position);

        assertAll(
                () -> assertThat(positions.size()).isEqualTo(2),
                () -> assertThat(positions).contains(new Position(0, 8)),
                () -> assertThat(positions).contains(new Position(0, 7))
        );
    }

    @Test
    @DisplayName("차 이동 테스트 - 초기 배치에서 5,7에 차 배치")
    void test15() {
        JanggiBoard janggiBoard = JanggiBoard.initialize();

        Position position = new Position(5, 7);
        Piece piece = new Chariot(Side.CHO);
        janggiBoard.getBoard().put(position, piece);

        List<Position> positions = janggiBoard.computeReachableDestination(position);

        assertAll(
                () -> assertThat(positions.size()).isEqualTo(12),
                () -> assertThat(positions).contains(new Position(5, 8)),
                () -> assertThat(positions).contains(new Position(5, 6)),
                () -> assertThat(positions).contains(new Position(5, 5)),
                () -> assertThat(positions).contains(new Position(5, 4)),
                () -> assertThat(positions).contains(new Position(5, 3)),
                () -> assertThat(positions).contains(new Position(5, 2)),
                () -> assertThat(positions).contains(new Position(5, 1)),
                () -> assertThat(positions).contains(new Position(5, 0)),
                () -> assertThat(positions).contains(new Position(6, 7)),
                () -> assertThat(positions).contains(new Position(4, 7)),
                () -> assertThat(positions).contains(new Position(3, 7)),
                () -> assertThat(positions).contains(new Position(2, 7))
        );
    }

    @Test
    @DisplayName("포 초기 이동 테스트 - 이동 불가")
    void test16() {
        JanggiBoard janggiBoard = JanggiBoard.initialize();
        Position position = new Position(1, 7);

        List<Position> positions = janggiBoard.computeReachableDestination(position);

        assertThat(positions.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("포 이동 테스트 - 초기 배치에서 4,7에 포 배치")
    void test17() {
        JanggiBoard janggiBoard = JanggiBoard.initialize();

        Position position = new Position(4, 7);
        Piece piece = new Cannon(Side.CHO);
        janggiBoard.getBoard().put(position, piece);

        List<Position> positions = janggiBoard.computeReachableDestination(position);

        assertAll(
                () -> assertThat(positions.size()).isEqualTo(4),
                () -> assertThat(positions).contains(new Position(4, 9)),
                () -> assertThat(positions).contains(new Position(4, 5)),
                () -> assertThat(positions).contains(new Position(4, 4)),
                () -> assertThat(positions).contains(new Position(4, 3))
        );
    }

    @Test
    @DisplayName("이동 목적지에 상대편 말이 있으면 잡고 상대편 말을 반환한다.")
    void test18() {
        JanggiBoard janggiBoard = JanggiBoard.initialize();

        Position position = new Position(5, 7);
        Position destination = new Position(5, 0);
        Piece piece = new Chariot(Side.CHO);
        janggiBoard.getBoard().put(position, piece);

        Piece catchedPiece = janggiBoard.moveOrCatchPiece(position, destination);

        assertThat(catchedPiece).isInstanceOf(Guard.class);
    }

    @Test
    @DisplayName("이동 목적지에 상대편 말이 없으면 이동만 수행하여 Empty를 반환한다.")
    void test19() {
        JanggiBoard janggiBoard = JanggiBoard.initialize();

        Position position = new Position(5, 7);
        Position destination = new Position(5, 1);

        Piece piece = new Chariot(Side.CHO);
        janggiBoard.getBoard().put(position, piece);

        Piece catchedPiece = janggiBoard.moveOrCatchPiece(position, destination);
        assertThat(catchedPiece).isInstanceOf(Empty.class);
    }

}
