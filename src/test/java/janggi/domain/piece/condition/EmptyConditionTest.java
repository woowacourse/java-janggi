package janggi.domain.piece.condition;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.board.BoardInitializer;
import janggi.domain.board.EmptyConditionTestBoardInitializer;
import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;
import java.util.List;
import org.junit.jupiter.api.Test;

public class EmptyConditionTest {

    MoveCondition condition = new EmptyCondition();

    @Test
    void 이동하려는_경로에_기물이_존재하면_예외가_발생한다() {
        //given
        List<Position> path = List.of(
                new Position(0, 0),
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3),
                new Position(0, 4),
                new Position(0, 5)
        );
        Camp camp = Camp.HAN;

        BoardInitializer boardInitializer = new EmptyConditionTestBoardInitializer();
        JanggiBoard board = new JanggiBoard(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board, PieceRule.CHARIOT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
    }

    @Test
    void 도착지점에_아군_기물이_존재하면_예외가_발생한다() {
        //given
        List<Position> path = List.of(
                new Position(0, 0),
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3),
                new Position(0, 4)
        );
        Camp camp = Camp.HAN;

        BoardInitializer boardInitializer = new EmptyConditionTestBoardInitializer();
        JanggiBoard board = new JanggiBoard(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board, PieceRule.CHARIOT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
    }
}
