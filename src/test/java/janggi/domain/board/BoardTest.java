package janggi.domain.board;

import static janggi.domain.board.Board.*;
import static janggi.domain.board.HorseElephantPosition.HEHE;
import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static org.assertj.core.api.Assertions.*;

import janggi.domain.piece.ChariotMoveStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("특정 위치에 있는 기물이 움직일 수 있는 위치들을 올바르게 반환한다")
    public void canMovePosition_success() {
        // given
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = new Board(policy);

        // when & then
        assertThatCode(() -> board.canMovePosition(Position.from(4, 5), CHO))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("상대 팀의 기물을 움직이려하는 경우에는 오류를 일으킨다")
    public void canMovePosition_error1() {
        // given
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = new Board(policy);

        // when & then
        assertThatThrownBy(() -> board.canMovePosition(Position.from(4, 5), HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_PIECE_OWNER_MESSAGE);
    }

    @Test
    @DisplayName("해당 위치에 기물이 없는 경우에는 오류를 일으킨다")
    public void canMovePosition_error2() {
        // given
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = new Board(policy);

        // when & then
        assertThatThrownBy(() -> board.canMovePosition(Position.from(5, 5), CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(PIECE_NOT_FOUND_MESSAGE);
    }


    @Test
    @DisplayName("특정 위치에 있는 기물을 다른 위치로 옮긴다")
    public void movePiece_success() {
        // given
        Position from = Position.from(5, 5);
        Piece fromPiece = new Piece(HAN, ChariotMoveStrategy.getInstance());
        Position toCanEat = Position.from(3,5);
        Position toCannotEat = Position.from(3,3);

        BoardDesignPolicy policy = () -> new HashMap<>(Map.of(
                from, fromPiece,
                toCanEat,  new Piece(CHO, ChariotMoveStrategy.getInstance())
        ));
        Board board = new Board(policy);

        // when
        board.movePiece(from, toCanEat, HAN);
        board.movePiece(toCanEat, toCannotEat, HAN);

        // then
        assertThat(board.board())
                .doesNotContainKeys(from, toCanEat)
                .containsEntry(toCannotEat, fromPiece);
    }

    @Test
    @DisplayName("특정 기물을 해당 위치로 움직일 수 없으면 에러가 발생한다")
    public void movePiece_fail() {
        // given
        Position from = Position.from(5, 5);
        Piece fromPiece = new Piece(HAN, ChariotMoveStrategy.getInstance());

        BoardDesignPolicy policy = () -> new HashMap<>(Map.of(
                from, fromPiece
        ));
        Board board = new Board(policy);
        Position to = Position.from(4, 4);

        // when & then
        assertThatThrownBy(() -> board.movePiece(from, to, HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(INVALID_PIECE_MOVE_MESSAGE);
    }
}
