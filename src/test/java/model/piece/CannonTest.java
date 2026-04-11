package model.piece;

import model.Board;
import model.Janggi;
import model.Team;
import model.coordinate.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CannonTest {

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#직선_이동_가능한_위치")
    void 포는_직선으로_이동할_수_있다(Position current, Position next) {
        // given
        Piece cannon = new Cannon(Team.HAN);
        // when
        boolean canMove = cannon.canMove(current, next);
        // then
        assertThat(canMove).isTrue();
    }

    @ParameterizedTest
    @MethodSource("model.fixture.PieceTestFixture#직선_이동_불가능한_위치")
    void 포는_대각선으로_이동할_수_없다(Position current, Position next) {
        // given
        Piece cannon = new Cannon(Team.HAN);
        // when
        boolean canMove = cannon.canMove(current, next);
        // then
        assertThat(canMove).isFalse();
    }

    @Test
    void 포가_1개의_기물을_뛰어넘어_이동한다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 0), new Cannon(Team.CHO));
        pieces.put(new Position(3, 0), new Soldier(Team.HAN));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when
        janggi.move(new Position(5, 0), new Position(1, 0));

        // then
        assertThat(board.pickPiece(new Position(1, 0)).isCannon()).isTrue();
    }

    @Test
    void 포가_가로로_1개의_기물을_뛰어넘어_이동한다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 0), new Cannon(Team.CHO));
        pieces.put(new Position(5, 3), new Soldier(Team.HAN));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when
        janggi.move(new Position(5, 0), new Position(5, 6));

        // then
        assertThat(board.pickPiece(new Position(5, 6)).isCannon()).isTrue();
    }

    @Test
    void 포가_경로에_기물이_없으면_예외가_발생한다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 0), new Cannon(Team.CHO));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when & then
        assertThatThrownBy(() -> janggi.move(new Position(5, 0), new Position(1, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포가_경로에_기물이_2개_이상이면_예외가_발생한다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 0), new Cannon(Team.CHO));
        pieces.put(new Position(4, 0), new Soldier(Team.HAN));
        pieces.put(new Position(3, 0), new Soldier(Team.HAN));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when & then
        assertThatThrownBy(() -> janggi.move(new Position(5, 0), new Position(1, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포가_경로에_포를_뛰어넘으면_예외가_발생한다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 0), new Cannon(Team.CHO));
        pieces.put(new Position(3, 0), new Cannon(Team.HAN));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when & then
        assertThatThrownBy(() -> janggi.move(new Position(5, 0), new Position(1, 0)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포가_적군_기물을_잡을_수_있다() {
        // given
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(new Position(5, 0), new Cannon(Team.CHO));
        pieces.put(new Position(3, 0), new Soldier(Team.HAN));
        pieces.put(new Position(1, 0), new Chariot(Team.HAN));
        Board board = new Board(pieces);
        Janggi janggi = new Janggi(board);

        // when
        janggi.move(new Position(5, 0), new Position(1, 0));

        // then
        assertThat(board.pickPiece(new Position(1, 0)).isCannon()).isTrue();
    }
}