package janggi.domain.piece.condition;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.exception.ExceptionMessage;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OnePieceExistsConditionTest {

    MoveCondition condition = new OnePieceExistsCondition();

    @Test
    void 이동하려는_경로에_기물이_존재하지_않으면_예외가_발생한다() {
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

        BoardInitializer boardInitializer = Map::of;
        Board board = new Board(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board, PieceType.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage());
    }

    @Test
    void 이동하려는_경로에_기물이_2개_이상_존재하면_예외가_발생한다() {
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

        BoardInitializer boardInitializer = () -> Map.of(
                new Position(0, 3), new Piece(PieceType.CHARIOT, Camp.HAN),
                new Position(0, 4), new Piece(PieceType.ELEPHANT, Camp.HAN)
        );
        Board board = new Board(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board, PieceType.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage());
    }

    @Test
    void 이동하려는_경로에_있는_기물이_포이면_예외가_발생한다() {
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

        BoardInitializer boardInitializer = () -> Map.of(
                new Position(0, 4), new Piece(PieceType.CANNON, Camp.CHO)
        );
        Board board = new Board(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board, PieceType.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.SAME_PIECE_TYPE_IN_PATH.getMessage());
    }

    @Test
    void 목적지에_있는_기물이_아군이면_예외가_발생한다() {
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

        BoardInitializer boardInitializer = () -> Map.of(
                new Position(0, 3), new Piece(PieceType.SOLDIER, Camp.HAN),
                new Position(0, 5), new Piece(PieceType.CHARIOT, Camp.HAN)
        );
        Board board = new Board(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board, PieceType.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.SAME_CAMP_PIECE_AT_DESTINATION.getMessage());
    }

    @Test
    void 목적지에_있는_기물이_상대_포면_예외가_발생한다() {
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

        BoardInitializer boardInitializer = () -> Map.of(
                new Position(0, 3), new Piece(PieceType.SOLDIER, Camp.HAN),
                new Position(0, 5), new Piece(PieceType.CANNON, Camp.CHO)
        );
        Board board = new Board(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board, PieceType.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.SAME_PIECE_TYPE_AT_DESTINATION.getMessage());
    }
}
