package domain.piece.strategy;

import domain.board.FakeBoard;
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
}
