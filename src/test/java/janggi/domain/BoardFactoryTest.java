package janggi.domain;

import janggi.domain.movestorage.ChaMoveStrategy;
import janggi.domain.movestorage.GungseongBoundMoveStrategy;
import janggi.domain.movestorage.JolMoveStrategy;
import janggi.domain.movestorage.MaMoveStrategy;
import janggi.domain.movestorage.PoMoveStrategy;
import janggi.domain.movestorage.SangMoveStrategy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardFactoryTest {

    @Test
    void 한나라_차_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(0), Column.of(0)); // 맨 윗줄 왼쪽 끝
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(ChaMoveStrategy.class);
    }

    @Test
    void 한나라_상_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(0), Column.of(1)); // 차 옆
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(SangMoveStrategy.class);
    }

    @Test
    void 한나라_마_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(0), Column.of(2)); // 상 옆
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(MaMoveStrategy.class);
    }

    @Test
    void 한나라_사_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(0), Column.of(3)); // 궁성 왼쪽 위
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(GungseongBoundMoveStrategy.class);
    }

    @Test
    void 한나라_궁_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(1), Column.of(4)); // 궁성 정중앙
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(GungseongBoundMoveStrategy.class);
    }

    @Test
    void 한나라_포_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(2), Column.of(1)); // 포 위치
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(PoMoveStrategy.class);
    }

    @Test
    void 한나라_졸_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(3), Column.of(0)); // 맨 왼쪽 졸
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.HAN);
        assertThat(piece.getMoveStorage()).isInstanceOf(JolMoveStrategy.class);
    }

    @Test
    void 초나라_차_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(9), Column.of(0)); // 맨 아랫줄 왼쪽 끝
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(ChaMoveStrategy.class);
    }

    @Test
    void 초나라_상_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(9), Column.of(1));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(SangMoveStrategy.class);
    }

    @Test
    void 초나라_마_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(9), Column.of(2));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(MaMoveStrategy.class);
    }

    @Test
    void 초나라_사_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(9), Column.of(3)); // 궁성 왼쪽 아래
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(GungseongBoundMoveStrategy.class);
    }

    @Test
    void 초나라_궁_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(8), Column.of(4)); // 궁성 정중앙
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(GungseongBoundMoveStrategy.class);
    }

    @Test
    void 초나라_포_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(7), Column.of(1));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(PoMoveStrategy.class);
    }

    @Test
    void 초나라_졸_기물이_정상적으로_초기화되는지_확인한다() {
        Board board = new Board(BoardFactory.generate());
        Position position = Position.of(Row.of(6), Column.of(0));
        Piece piece = board.getPieceAt(position);

        assertThat(piece.getTeam()).isEqualTo(Team.CHO);
        assertThat(piece.getMoveStorage()).isInstanceOf(JolMoveStrategy.class);
    }
}
