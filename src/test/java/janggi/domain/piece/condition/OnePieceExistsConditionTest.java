package janggi.domain.piece.condition;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.board.BoardInitializer;
import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
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
        JanggiBoard board = new JanggiBoard(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board, PieceRule.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
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
                new Position(0, 3), new Piece(PieceRule.CHARIOT, Camp.HAN),
                new Position(0, 4), new Piece(PieceRule.ELEPHANT, Camp.HAN)
        );
        JanggiBoard board = new JanggiBoard(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board, PieceRule.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
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
                new Position(0, 4), new Piece(PieceRule.CANNON, Camp.CHO)
        );
        JanggiBoard board = new JanggiBoard(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board, PieceRule.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
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
                new Position(0, 5), new Piece(PieceRule.CHARIOT, Camp.HAN)
        );
        JanggiBoard board = new JanggiBoard(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board, PieceRule.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
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
                new Position(0, 5), new Piece(PieceRule.CANNON, Camp.CHO)
        );
        JanggiBoard board = new JanggiBoard(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board, PieceRule.CANNON))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
    }
}
