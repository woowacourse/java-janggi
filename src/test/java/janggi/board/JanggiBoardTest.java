package janggi.board;

import janggi.domain.Turn;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Position;
import janggi.domain.piece.Empty;
import janggi.domain.piece.Piece;
import janggi.domain.piece.limit.*;
import janggi.domain.piece.unlimit.Cannon;
import janggi.domain.piece.unlimit.Chariot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JanggiBoardTest {

    @Test
    @DisplayName("9 x 10의 빈 장기판 생성 테스트")
    void test1() {
        assertThat(JanggiBoard.initializeWithPieces().getBoard().size()).isEqualTo(90);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,3", "2,3", "4,3", "6,3", "8,3",
            "0,6", "2,6", "4,6", "6,6", "8,6"
    })
    @DisplayName("졸 초기화 테스트")
    void test2(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(Soldier.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,0", "6,0", "2,9", "6,9"
    })
    @DisplayName("상 초기화 테스트")
    void test4(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(Elephant.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,0", "7,0", "1,9", "7,9"
    })
    @DisplayName("마 초기화 테스트")
    void test5(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(Horse.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1,2", "7,2", "1,7", "7,7"
    })
    @DisplayName("포 초기화 테스트")
    void test6(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(Cannon.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "0,0", "8,0", "0,9", "8,9"
    })
    @DisplayName("차 초기화 테스트")
    void test7(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(Chariot.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "3,9", "5,9", "3,0", "5,0"
    })
    @DisplayName("사 초기화 테스트")
    void test8(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(Guard.class);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4,8", "4,1"
    })
    @DisplayName("궁 초기화 테스트")
    void test9(int x, int y) {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        assertThat(janggiBoard.getBoard().get(new Position(x, y))).isInstanceOf(King.class);
    }

    @Test
    @DisplayName("기본 이동 테스트 - 졸")
    void test10() {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        Position position = new Position(2, 6);

        List<Position> positions = janggiBoard.computeReachableDestination(Turn.CHO, position);

        assertThat(positions).contains(new Position(1, 6), new Position(2, 5), new Position(3, 6));
    }

    @Test
    @DisplayName("이동 테스트 - 경계의 졸")
    void test11() {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        Position position = new Position(8, 3);

        List<Position> positions = janggiBoard.computeReachableDestination(Turn.HAN, position);

        assertThat(positions).contains(new Position(7, 3), new Position(8, 4));
    }

    @Test
    @DisplayName("상 초기 배치에서 이동 가능 경우가 0개이므로 예외를 던진다.")
    void test12() {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        Position position = new Position(2, 9);

        assertThatThrownBy(() -> janggiBoard.computeReachableDestination(Turn.CHO, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동 가능한 목적지가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("마 초기 배치 이동 테스트")
    void test13() {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        Position position = new Position(1, 9);

        List<Position> positions = janggiBoard.computeReachableDestination(Turn.CHO, position);

        assertThat(positions).contains(new Position(0, 7), new Position(2, 7));
    }

    @Test
    @DisplayName("차 초기 배치 이동 테스트")
    void test14() {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        Position position = new Position(0, 9);

        List<Position> positions = janggiBoard.computeReachableDestination(Turn.CHO, position);

        assertThat(positions).contains(new Position(0, 8), new Position(0, 7));
    }

    @Test
    @DisplayName("차 이동 테스트 - 초기 배치에서 5,7에 차 배치")
    void test15() {
        Position position = new Position(5, 7);
        Turn turn = Turn.CHO;
        Piece piece = new Chariot(turn);
        JanggiBoard modifiedJanggiBoard = JanggiBoardFixture.modifyInitialBoardWithOnePiece(position, piece);

        List<Position> positions = modifiedJanggiBoard.computeReachableDestination(turn, position);

        assertThat(positions).contains(new Position(5, 8),
                                        new Position(5, 6),
                                        new Position(5, 5),
                                        new Position(5, 4),
                                        new Position(5, 3),
                                        new Position(5, 2),
                                        new Position(5, 1),
                                        new Position(5, 0),
                                        new Position(6, 7),
                                        new Position(4, 7),
                                        new Position(3, 7),
                                        new Position(2, 7));
    }

    @Test
    @DisplayName("포 초기 배치에서 이동 가능 경우가 0개이므로 예외를 던진다.")
    void test16() {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();
        Position position = new Position(1, 7);

        assertThatThrownBy(() -> janggiBoard.computeReachableDestination(Turn.CHO, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이동 가능한 목적지가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("포 이동 테스트 - 초기 배치에서 4,7에 포 배치")
    void test17() {
        Position position = new Position(4, 7);
        Turn turn = Turn.CHO;
        Piece piece = new Cannon(turn);
        JanggiBoard modifiedBoard = JanggiBoardFixture.modifyInitialBoardWithOnePiece(position, piece);

        List<Position> positions = modifiedBoard.computeReachableDestination(turn, position);

        assertThat(positions).contains(new Position(4, 9), new Position(4, 5), new Position(4, 4), new Position(4, 3));
    }

    @Test
    @DisplayName("이동 목적지에 상대편 말이 있으면 잡고 상대편 말을 반환한다.")
    void test18() {
        Position position = new Position(5, 7);
        Piece piece = new Chariot(Turn.CHO);
        JanggiBoard modifiedBoard = JanggiBoardFixture.modifyInitialBoardWithOnePiece(position, piece);
        Position destination = new Position(5, 0);

        Piece catchedPiece = modifiedBoard.moveOrCatchPiece(position, destination);

        assertThat(catchedPiece).isInstanceOf(Guard.class);
    }

    @Test
    @DisplayName("이동 목적지에 상대편 말이 없으면 이동만 수행하여 Empty를 반환한다.")
    void test19() {
        Position position = new Position(5, 7);
        Piece piece = new Chariot(Turn.CHO);
        JanggiBoard modifiedBoard = JanggiBoardFixture.modifyInitialBoardWithOnePiece(position, piece);
        Position destination = new Position(5, 1);

        Piece catchedPiece = modifiedBoard.moveOrCatchPiece(position, destination);

        assertThat(catchedPiece).isInstanceOf(Empty.class);
    }

    @Test
    @DisplayName("왕을 잡으면 게임 종료")
    void test21() {
        Position position = new Position(5, 1);
        Turn turn = Turn.CHO;
        Piece piece = new Chariot(turn);
        JanggiBoard modifiedBoard = JanggiBoardFixture.modifyInitialBoardWithOnePiece(position, piece);
        Position destination = new Position(4, 1);

        modifiedBoard.moveOrCatchPiece(position, destination);

        assertThat(modifiedBoard.checkGameIsOver(turn)).isTrue();
    }

    @Test
    @DisplayName("이동할 기물 선택에서 빈 칸을 선택하면 예외를 던진다.")
    void test22() {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();

        Position position = new Position(0, 8);

        assertThatThrownBy(() -> janggiBoard.computeReachableDestination(Turn.CHO, position)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 위치에 움직일 수 있는 기물이 없습니다.");
    }

    @Test
    @DisplayName("포 이동 테스트 - 초기 배치에서 궁성의 중앙 (4, 8)에 포 배치 + 양 옆에 기물 배치")
    void test23() {
        Position position = new Position(4, 8);
        Turn turn = Turn.CHO;
        Piece piece = new Cannon(turn);

        Map<Position, Piece> piecePositions = new HashMap<>();
        piecePositions.put(position, piece);

        //포의 양 옆에 졸 배치해서 점프할 수 있도록 함.
        piecePositions.put(new Position(3, 8), new Soldier(Turn.CHO));
        piecePositions.put(new Position(5, 8), new Soldier(Turn.CHO));
        JanggiBoard modifiedBoard = JanggiBoardFixture.modifyInitialBoardWithManyPieces(piecePositions);

        List<Position> positions = modifiedBoard.computeReachableDestination(turn, position);

        assertThat(positions).contains(new Position(4, 5), new Position(2, 8), new Position(6, 8));
    }

    @Test
    @DisplayName("포 이동 테스트 - 초기 배치에서 궁성의 중앙 (4, 8)에 포 배치 + 왼쪽 대각선에 기물 배치 - 대각선 이동 불가")
    void test24() {
        Position position = new Position(4, 8);
        Turn turn = Turn.CHO;
        Piece piece = new Cannon(turn);

        Map<Position, Piece> piecePositions = new HashMap<>();
        piecePositions.put(position, piece);

        //포의 양 옆에 졸 배치해서 점프할 수 있도록 함.
        piecePositions.put(new Position(3, 7), new Soldier(Turn.CHO));
        piecePositions.put(new Position(2, 6), new Empty());
        JanggiBoard modifiedBoard = JanggiBoardFixture.modifyInitialBoardWithManyPieces(piecePositions);

        List<Position> positions = modifiedBoard.computeReachableDestination(turn, position);

        assertThat(positions).contains(new Position(4, 5))
                            .doesNotContain(new Position(2, 6));
    }

    @Test
    @DisplayName("초기 상태 기물 점수 계산 테스트")
    void test25() {
        JanggiBoard janggiBoard = JanggiBoard.initializeWithPieces();

        assertThat(janggiBoard.sumSideTotalScore(Turn.CHO)).isEqualTo(72);
    }

    @Test
    @DisplayName("임의의 상태 기물 점수 계산 테스트 - 차와 마만 남은 경우 (상대방의 기물은 고려하지 않음)")
    void test26() {
        Map<Position, Piece> piecePositions = new HashMap<>();
        piecePositions.put(new Position(0, 8), new Chariot(Turn.CHO));
        piecePositions.put(new Position(1, 8), new Horse(Turn.CHO));
        piecePositions.put(new Position(2, 8), new Horse(Turn.HAN));

        JanggiBoard janggiBoard = JanggiBoardFixture.modifyEmptyBoardWithManyPieces(piecePositions);

        assertThat(janggiBoard.sumSideTotalScore(Turn.CHO)).isEqualTo(18);
    }
}
