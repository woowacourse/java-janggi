package domain.piece.strategy;

import domain.board.FakeBoard;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Side;
import domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ChariotTest {

    FakeBoard fakeBoard;
    MovingCondition movingCondition;

    @BeforeEach
    void setUp() {
        fakeBoard = new FakeBoard();
        movingCondition = new ChariotMovingCondition();
    }

    @Test
    @DisplayName("차 기물이 움직임의 여부를 판단할 수 있다.")
    void canMove_이동성공_차_기물_움직임_여부_판단() {
        // given
        FakeBoard fakeBoard = new FakeBoard();
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        fakeBoard.put(Position.of(1, 9), choChariot);

        Position startPosition = Position.of(1, 9);
        Position endPosition = Position.of(10, 9);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("초 진영의 차 기물은 뒤로 움직일 수 있다.")
    void canMove_이동성공_차_기물_움직임_여부_판단_2() {
        // given
        FakeBoard fakeBoard = new FakeBoard();
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        fakeBoard.put(Position.of(4, 9), choChariot);

        Position startPosition = Position.of(4, 9);
        Position endPosition = Position.of(2, 9);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("차 기물은 도착 지점으로 가는 경로 내에 기물이 있다면 움직일 수 없다.")
    void canMove_이동실패_차_기물_움직임_여부_판단() {
        // given
        FakeBoard fakeBoard = new FakeBoard();
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        Piece choPawn = Piece.of(Side.CHO, PieceType.CHARIOT);
        fakeBoard.put(Position.of(1, 9), choChariot);
        fakeBoard.put(Position.of(4, 9), choPawn);

        Position startPosition = Position.of(1, 9);
        Position endPosition = Position.of(10, 9);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("차 기물은 대각선으로 이동할 수 없다.")
    void canMove_이동실패_차_기물_움직임_여부_판단_2() {
        // given
        FakeBoard fakeBoard = new FakeBoard();
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        fakeBoard.put(Position.of(1, 9), choChariot);

        Position startPosition = Position.of(1, 9);
        Position endPosition = Position.of(2, 8);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("차 기물은 궁성영역 중 대각선으로 이동할 수 있는 영역에서 대각선으로 이동할 수 있다.")
    void canMove_차_기물_움직임_테스트_1() {
        // given
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        fakeBoard.put(Position.of(1, 6), choChariot);

        Position startPosition = Position.of(1, 6);
        Position endPosition = Position.of(3, 4);

        // when, then
        assertThat(movingCondition.canMove(fakeBoard, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("차 기물은 궁성영역 중 대각선으로 이동할 수 없는 영역에서 대각선으로 이동할 수 없다.")
    void canMove_차_기물_움직임_테스트_2() {
        // given
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        fakeBoard.put(Position.of(1, 6), choChariot);

        Position startPosition = Position.of(1, 6);
        Position endPosition = Position.of(3, 5);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("차 기물은 궁성영역에서 차 움직임에 맞게 궁성영역을 벗어난 영역으로 이동할 수 있다.")
    void canMove_차_기물_움직임_테스트_3() {
        // given
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        fakeBoard.put(Position.of(1, 6), choChariot);

        Position startPosition = Position.of(1, 6);
        Position endPosition = Position.of(1, 1);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("차 기물은 궁성영역에서 차 움직임에 맞지않은 궁성영역을 벗어난 영역으로 이동할 수 없다.")
    void canMove_차_기물_움직임_테스트_4() {
        // given
        Piece choChariot = Piece.of(Side.CHO, PieceType.CHARIOT);
        fakeBoard.put(Position.of(1, 6), choChariot);

        Position startPosition = Position.of(1, 6);
        Position endPosition = Position.of(3, 8);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }
}
