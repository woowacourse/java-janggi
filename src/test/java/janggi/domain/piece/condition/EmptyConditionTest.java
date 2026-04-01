package janggi.domain.piece.condition;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.List;
import java.util.Map;
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
        Position blockingPosition = new Position(0, 4);
        Board board = new Board(Map.of(
                blockingPosition, new Piece(PieceRule.CHARIOT, Camp.HAN)
        ));
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, Camp.HAN, board, PieceRule.CHARIOT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
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
        Position sameCampPiecePosition = new Position(0, 4);
        Board board = new Board(Map.of(
                sameCampPiecePosition, new Piece(PieceRule.CHARIOT, Camp.HAN)
        ));
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, Camp.HAN, board, PieceRule.CHARIOT))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.SAME_CAMP_PIECE_AT_DESTINATION.getMessage());
    }
}
