package janggi.domain.board;

import static janggi.domain.board.Board.*;
import static janggi.domain.board.HorseElephantPosition.HEHE;
import static janggi.domain.dynasty.Dynasty.CHO;
import static janggi.domain.dynasty.Dynasty.HAN;
import static janggi.domain.piece.PieceType.CHARIOT;
import static org.assertj.core.api.Assertions.*;

import janggi.domain.exception.DomainException;
import janggi.domain.dynasty.Dynasty;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Position;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

class BoardTest {

    @Test
    @DisplayName("특정 위치에 있는 기물이 움직일 수 있는 위치들을 올바르게 반환한다")
    public void canMovePosition_success() {
        // given
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = Board.policyOf(policy);
        
        // when & then
        assertThatCode(() -> board.canMovePosition(Position.from(4, 5), CHO))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("상대 팀의 기물을 움직이려하는 경우에는 오류를 일으킨다")
    public void canMovePosition_error1() {
        // given
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = Board.policyOf(policy);
        Position from = Position.from(4, 5);

        // when & then
        assertThatThrownBy(() -> board.canMovePosition(from, HAN))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining(String.format(INVALID_PIECE_OWNER_MESSAGE, from.row().row(), from.column().column()));
    }

    @Test
    @DisplayName("해당 위치에 기물이 없는 경우에는 오류를 일으킨다")
    public void canMovePosition_error2() {
        // given
        DefaultBoardDesignPolicy policy = new DefaultBoardDesignPolicy(Map.of(CHO, HEHE, HAN, HEHE));
        Board board = Board.policyOf(policy);
        Position from = Position.from(5, 5);
        // when & then
        assertThatThrownBy(() -> board.canMovePosition(from, CHO))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining(String.format(PIECE_NOT_FOUND_MESSAGE, from.row().row(), from.column().column()));
    }


    @Test
    @DisplayName("특정 위치에 있는 기물을 다른 위치로 옮긴다")
    public void movePiece_success() {
        // given
        Position from = Position.from(5, 5);
        Piece fromPiece = new Piece(HAN, CHARIOT);
        Position toCanEat = Position.from(3,5);
        Position toCannotEat = Position.from(3,3);

        BoardDesignPolicy policy = () -> new HashMap<>(Map.of(
                from, fromPiece,
                toCanEat,  new Piece(CHO,  CHARIOT)
        ));
        Board board = Board.policyOf(policy);

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
        Piece fromPiece = new Piece(HAN, CHARIOT);

        BoardDesignPolicy policy = () -> new HashMap<>(Map.of(
                from, fromPiece
        ));
        Board board = Board.policyOf(policy);
        Position to = Position.from(4, 4);

        // when & then
        assertThatThrownBy(() -> board.movePiece(from, to, HAN))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining(String.format(INVALID_PIECE_MOVE_MESSAGE,
                        from.row().row(), from.column().column(),
                        to.row().row(), to.column().column()));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "CHO, 72",
            "HAN, 73.5"
    })
    @DisplayName("초 나라는 72점, 한나라는 73.5점으로 시작한다.")
    public void calculateScoreByDynasty_success(Dynasty dynasty, double expected) throws Exception {
        // given
        BoardDesignPolicy boardDesignPolicy =
                new DefaultBoardDesignPolicy(Map.of(
                        dynasty, HEHE,
                        dynasty.next(), HEHE
                ));
        Board board = Board.policyOf(boardDesignPolicy);

        // when
        double score = board.calculateScoreByDynasty(dynasty);

        // then
        assertThat(score).isEqualTo(expected);
    }
    
    @ParameterizedTest
    @CsvSource(value = {
            "2, 5, CHO",
            "7, 5, HAN"
    })
    @DisplayName("장기판에 특정나라의 궁 기물이 잡힌 경우")
    public void isGeneralCaught_ByDynasty_success1(int row, int column, Dynasty dynasty) throws Exception {
        // given
        BoardDesignPolicy boardDesignPolicy = () -> Map.of(
                Position.from(row, column), new Piece(dynasty, PieceType.GENERAL)
        );
        Board board = Board.policyOf(boardDesignPolicy);

        // when
        boolean generalCaught = board.isGeneralCaughtByDynasty(dynasty.next());

        // then
        assertThat(generalCaught).isEqualTo(true);
    }

    @ParameterizedTest
    @EnumSource(Dynasty.class)
    @DisplayName("장기판에 특정 나라의 궁 기물이 있는 경우")
    public void isGeneralCaught_ByDynasty_success2(Dynasty dynasty) throws Exception {
        // given
        BoardDesignPolicy boardDesignPolicy = () -> Map.of(
                Position.from(2, 5), new Piece(CHO, PieceType.GENERAL),
                Position.from(7, 5), new Piece(HAN, PieceType.GENERAL)
        );
        Board board = Board.policyOf(boardDesignPolicy);

        // when
        boolean generalCaught = board.isGeneralCaughtByDynasty(dynasty);

        // then
        assertThat(generalCaught).isEqualTo(false);
    }
}
