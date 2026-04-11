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

public class PalacePieceTest {

    FakeBoard fakeBoard;
    MovingCondition movingCondition;

    @BeforeEach
    void setUp() {
        fakeBoard = new FakeBoard();
        movingCondition = new PalacePieceMovingCondition();
    }

    @Test
    @DisplayName("궁성 기물(사)은 궁성에서만 움직일 수 있다.")
    void canMove_궁성_기물_움직임_테스트_1() {
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        fakeBoard.put(Position.of(1, 4), choCounselor);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(2, 4);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁성 기물(사)은 궁성을 벗어날 수 없다.")
    void canMove_궁성_기물_움직임_테스트_2() {
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        fakeBoard.put(Position.of(1, 4), choCounselor);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(1, 3);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁성 기물(사)은 궁성에서 대각선으로 이동 가능한 경우 대각선으로 이동할 수 있다.")
    void canMove_궁성_기물_움직임_테스트_3() {
        Piece choCounselor = Piece.of(Side.CHO, PieceType.COUNSELOR);
        fakeBoard.put(Position.of(1, 4), choCounselor);

        Position startPosition = Position.of(1, 4);
        Position endPosition = Position.of(2, 5);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁성 기물(궁)은 궁성에서 대각선으로 이동 불가능한 경우 대각선으로 이동할 수 없다.")
    void canMove_궁성_기물_움직임_테스트_4() {
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        fakeBoard.put(Position.of(3, 5), choGeneral);

        Position startPosition = Position.rotate180from(Position.of(3, 5));
        Position endPosition = Position.rotate180from(Position.of(2, 4));

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("궁성 기물(궁)은 궁성에서 대각선(DOWN_LEFT)으로 이동 가능한 경우 대각선으로 이동할 수 있다.")
    void canMove_궁성_기물_움직임_테스트_5() {
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        fakeBoard.put(Position.of(2, 5), choGeneral);

        Position startPosition = Position.of(2, 5);
        Position endPosition = Position.of(1, 4);
        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("궁성 기물(궁)은 궁성에서 왼쪽으로 이동 가능한 경우 왼쪽으로 이동할 수 있다.")
    void canMove_궁성_기물_움직임_테스트_6() {
        Piece choGeneral = Piece.of(Side.CHO, PieceType.GENERAL);
        fakeBoard.put(Position.of(2, 5), choGeneral);

        Position startPosition = Position.of(2, 5);
        Position endPosition = Position.of(1, 5);
        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }
}
