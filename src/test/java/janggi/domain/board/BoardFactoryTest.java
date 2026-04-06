package janggi.domain.board;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.movestrategy.FixedStepMoveStrategy;
import janggi.domain.movestrategy.OrStrategy;
import janggi.domain.movestrategy.PalaceBoundStrategy;
import janggi.domain.movestrategy.SlidingMoveStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardFactoryTest {

    @Test
    void 한나라_차_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(0), Column.of(0));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(SlidingMoveStrategy.class);
    }

    @Test
    void 한나라_상_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(0), Column.of(1));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(FixedStepMoveStrategy.class);
    }

    @Test
    void 한나라_마_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(0), Column.of(2));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(FixedStepMoveStrategy.class);
    }

    @Test
    void 한나라_사_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(0), Column.of(3));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(PalaceBoundStrategy.class);
    }

    @Test
    void 한나라_궁_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(1), Column.of(4));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(PalaceBoundStrategy.class);
    }

    @Test
    void 한나라_포_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(2), Column.of(1));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(SlidingMoveStrategy.class);
    }

    @Test
    void 한나라_졸_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(3), Column.of(0));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(OrStrategy.class);
    }

    @Test
    void 초나라_차_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(9), Column.of(0));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(SlidingMoveStrategy.class);
    }

    @Test
    void 초나라_상_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(9), Column.of(1));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(FixedStepMoveStrategy.class);
    }

    @Test
    void 초나라_마_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(9), Column.of(2));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(FixedStepMoveStrategy.class);
    }

    @Test
    void 초나라_사_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(9), Column.of(3));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(PalaceBoundStrategy.class);
    }

    @Test
    void 초나라_궁_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(8), Column.of(4));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(PalaceBoundStrategy.class);
    }

    @Test
    void 초나라_포_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(7), Column.of(1));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(SlidingMoveStrategy.class);
    }

    @Test
    void 초나라_졸_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(6), Column.of(0));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(OrStrategy.class);
    }
}
