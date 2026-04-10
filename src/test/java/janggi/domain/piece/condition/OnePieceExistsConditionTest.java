package janggi.domain.piece.condition;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.initializer.BoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class OnePieceExistsConditionTest {

    MoveCondition condition = new OnePieceExistsCondition(PieceType.CANNON);

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
        assertThatThrownBy(() -> condition.checkPath(path, camp, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 정확히 1개의 기물만 뛰어넘을 수 있습니다.");
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
                new Position(0, 3), new Piece(Camp.HAN, PieceType.CHARIOT),
                new Position(0, 4), new Piece(Camp.HAN, PieceType.ELEPHANT)
        );
        Board board = new Board(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 정확히 1개의 기물만 뛰어넘을 수 있습니다.");
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
                new Position(0, 4), new Piece(Camp.CHO, PieceType.CANNON)
        );
        Board board = new Board(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 경로상에 같은 종류의 기물이 존재합니다.");
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
                new Position(0, 3), new Piece(Camp.HAN, PieceType.SOLDIER),
                new Position(0, 5), new Piece(Camp.HAN, PieceType.CHARIOT)
        );
        Board board = new Board(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 목적지에 같은 진영의 기물이 존재합니다.");
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
                new Position(0, 3), new Piece(Camp.HAN, PieceType.SOLDIER),
                new Position(0, 5), new Piece(Camp.CHO, PieceType.CANNON)
        );
        Board board = new Board(boardInitializer);
        //when & then
        assertThatThrownBy(() -> condition.checkPath(path, camp, board))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 목적지에 같은 종류의 기물이 존재합니다.");
    }
}
