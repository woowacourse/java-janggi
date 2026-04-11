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

public class PawnTest {

    FakeBoard fakeBoard;
    MovingCondition movingCondition;

    @BeforeEach
    void setUp() {
        fakeBoard = new FakeBoard();
        movingCondition = new PawnMovingCondition();
    }

    @Test
    @DisplayName("졸 기물의 움직임(UP) 여부를 판단할 수 있다.")
    void canMove_성공_졸_기물_움직임_여부_판단() {
        // given
        Position startPosition = Position.of(4, 1);
        Position endPosition = Position.of(5, 1);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("졸 기물의 움직임(DOWN) 여부를 판단할 수 있다.")
    void canMove_실패_졸_기물_움직임_여부_판단() {
        // given
        Position startPosition = Position.of(4, 1);
        Position endPosition = Position.of(3, 1);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("졸 기물은 궁성영역 중 대각선으로 이동할 수 있는 영역에서 대각선으로 이동할 수 있다.")
    void canMove_졸_기물_움직임_테스트_1() {
        // given
        Piece hanPawn = Piece.of(Side.HAN, PieceType.PAWN);
        fakeBoard.put(Position.of(3, 4), hanPawn);

        Position startPosition = Position.of(3, 4);
        Position endPosition = Position.of(2, 5);

        // when, then
        assertThat(movingCondition.canMove(fakeBoard, startPosition, endPosition)).isTrue();
    }

    @Test
    @DisplayName("졸 기물은 궁성영역 중 대각선으로 이동할 수 없는 영역에서 대각선으로 이동할 수 없다.")
    void canMove_졸_기물_움직임_테스트_2() {
        // given
        Piece hanPawn = Piece.of(Side.HAN, PieceType.PAWN);
        fakeBoard.put(Position.of(3, 5), hanPawn);

        Position startPosition = Position.of(3, 5);
        Position endPosition = Position.of(2, 4);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("졸 기물은 궁성영역에서 졸 움직임에 맞게 궁성영역을 벗어난 영역으로 이동할 수 있다.")
    void canMove_졸_기물_움직임_테스트_3() {
        // given
        Piece hanPawn = Piece.of(Side.HAN, PieceType.PAWN);
        fakeBoard.put(Position.of(3, 4), hanPawn);

        Position startPosition = Position.of(3, 4);
        Position endPosition = Position.of(3, 3);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("졸 기물은 궁성영역에서 졸 움직임에 맞지않은 궁성영역을 벗어난 영역으로 이동할 수 없다.")
    void canMove_졸_기물_움직임_테스트_4() {
        // given
        Piece hanPawn = Piece.of(Side.HAN, PieceType.PAWN);
        fakeBoard.put(Position.of(3, 4), hanPawn);

        Position startPosition = Position.of(3, 4);
        Position endPosition = Position.of(2, 3);

        // when
        boolean result = movingCondition.canMove(fakeBoard, startPosition, endPosition);
        // then
        assertThat(result).isFalse();
    }
}
