package janggi.domain;


import janggi.domain.movestorage.ChaMoveStorage;
import janggi.domain.movestorage.GungAndSaMoveStorage;
import janggi.domain.movestorage.JolMoveStorage;
import janggi.domain.movestorage.MaMoveStorage;
import janggi.domain.movestorage.PoMoveStorage;
import janggi.domain.movestorage.SangMoveStorage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardFactoryTest {

    @Test
    void 한나라_포_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(1), Column.of(2));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(PoMoveStorage.class);
    }

    @Test
    void 한나라_상_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(1), Column.of(0));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(SangMoveStorage.class);
    }

    @Test
    void 한나라_사_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(3), Column.of(0));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(GungAndSaMoveStorage.class);
    }

    @Test
    void 한나라_궁_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(4), Column.of(1));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(GungAndSaMoveStorage.class);
    }

    @Test
    void 한나라_마_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(2), Column.of(0));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(MaMoveStorage.class);
    }

    @Test
    void 한나라_졸_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(0), Column.of(3));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(JolMoveStorage.class);
    }

    @Test
    void 한나라_차_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(0), Column.of(0));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(ChaMoveStorage.class);
    }

    @Test
    void 초나라_차_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(0), Column.of(9));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(ChaMoveStorage.class);
    }

    @Test
    void 초나라_상_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(1), Column.of(9));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(SangMoveStorage.class);
    }

    @Test
    void 초나라_마_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(2), Column.of(9));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(MaMoveStorage.class);
    }

    @Test
    void 초나라_사_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(3), Column.of(9));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(GungAndSaMoveStorage.class);
    }

    @Test
    void 초나라_궁_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(4), Column.of(8));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(GungAndSaMoveStorage.class);
    }

    @Test
    void 초나라_포_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(1), Column.of(7));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(PoMoveStorage.class);
    }

    @Test
    void 초나라_졸_기물이_정상적으로_초기화되는지_확인한다() {
        // given
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(0), Column.of(6));
        // when
        Piece piece = board.getPieceAt(position);
        // then
        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(JolMoveStorage.class);
    }
}
