package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.ScoreBoard;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.camp.CampType;
import janggi.exception.ExceptionMessage;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 목적지가_비어있으면_기물을_이동시킨다() {
        // given
        Position source = new Position(7, 1);
        Position destination = new Position(8, 1);
        Board board = new Board(Map.of(
                source, new Piece(PieceRule.SOLDIER, CampType.CHO)
        ));
        // when
        board.movePiece(source, destination, CampType.CHO);
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(board.hasPieceAt(destination)).isTrue();
            softly.assertThat(board.hasPieceAt(source)).isFalse();
        });
    }


    @Test
    void 목적지에_반대_진영_기물이_있으면_해당_기물을_제거한다() {
        // given
        Position source = new Position(7, 1);
        Position destination = new Position(0, 1);

        Board board = new Board(Map.of(
                new Position(4, 1), new Piece(PieceRule.SOLDIER, CampType.HAN),
                destination, new Piece(PieceRule.HORSE, CampType.CHO),
                source, new Piece(PieceRule.CANNON, CampType.HAN)
        ));
        // when
        board.movePiece(source, destination, CampType.HAN);
        // then
        boolean destinationExists = board.getBoard().get(destination).isSamePieceRule(PieceRule.CANNON);
        boolean sourceExists = board.hasPieceAt(source);

        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(destinationExists).isTrue();
            assertSoftly.assertThat(sourceExists).isFalse();
        });
    }

    @Test
    void 상대_진영의_기물을_이동_시키면_예외가_발생한다() {
        // given
        Position source = new Position(7, 1);
        Position destination = new Position(0, 1);

        // when
        Board board = new Board(Map.of(
                destination, new Piece(PieceRule.HORSE, CampType.CHO),
                source, new Piece(PieceRule.CANNON, CampType.CHO)
        ));
        // then
        assertThatThrownBy(() -> board.movePiece(source, destination, CampType.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.INVALID_CAMP_PIECE.getMessage());
    }

    @Test
    void 출발지에_기물이_존재하지_않으면_예외가_발생한다() {
        // given
        Position source = new Position(7, 1);
        Position destination = new Position(0, 1);
        // when
        Board board = new Board(Map.of());
        // then
        assertThatThrownBy(() -> board.movePiece(source, destination, CampType.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.SOURCE_NOT_EXISTS.getMessage());
    }

    @Test
    void 특정_위치에_기물이_있는지_확인한다() {
        // given
        Position position = new Position(0, 0);
        Board board = new Board(Map.of(position, new Piece(PieceRule.SOLDIER, CampType.CHO)));

        // when & then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(board.hasPieceAt(position)).isTrue();
            softly.assertThat(board.hasPieceAt(new Position(0, 1))).isFalse();
        });
    }

    @Test
    void 출발지와_목적지가_같으면_예외가_발생한다() {
        // given
        Position source = new Position(7, 1);
        Board board = new Board(Map.of(source, new Piece(PieceRule.SOLDIER, CampType.CHO)));
        // when & then
        assertThatThrownBy(() -> board.movePiece(source, source, CampType.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.PIECE_MUST_MOVE.getMessage());
    }

    @Test
    void 목적지에_같은_진영_기물이_있으면_예외가_발생한다() {
        // given
        Position source = new Position(7, 1);
        Position destination = new Position(0, 1);
        Board board = new Board(Map.of(
                source, new Piece(PieceRule.SOLDIER, CampType.CHO),
                destination, new Piece(PieceRule.HORSE, CampType.CHO)
        ));
        // when & then
        assertThatThrownBy(() -> board.movePiece(source, destination, CampType.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.SAME_CAMP_PIECE_AT_DESTINATION.getMessage());
    }

    @Test
    void 현재_차례인_진영의_왕이_존재하는지_확인한다() {
        // given
        CampType currnetTurnCampType = CampType.CHO;
        Board board = new Board(Map.of(new Position(0, 4), new Piece(PieceRule.GENERAL, CampType.HAN)));
        // when
        boolean result = board.isGeneralKilled(currnetTurnCampType);
        // then
        assertThat(result).isTrue();
    }

    @Test
    void 상대_진영의_기물을_잡으면_보드에서_사라지며_상대_진영은_점수를_잃는다() {
        // given
        Position source = new Position(3, 0);
        Position destination = new Position(4, 0);
        CampType rivalCampType = CampType.HAN;
        Board board = new Board(Map.of(
                source, new Piece(PieceRule.CHARIOT, CampType.CHO),
                destination, new Piece(PieceRule.SOLDIER, rivalCampType)
        ));

        ScoreBoard scoreBoard = ScoreBoard.create();
        Double scoreBeforeMinus = scoreBoard.getScoreBoard().get(rivalCampType);
        double expectedScore = scoreBeforeMinus - PieceRule.SOLDIER.getScore();
        // when
        board.movePiece(source, destination, CampType.CHO);
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(board.hasPieceAt(source)).isFalse();
            softly.assertThat(board.getBoard().get(destination).isSamePieceRule(PieceRule.CHARIOT)).isTrue();
            softly.assertThat(board.getScoreBoard().get(CampType.HAN)).isEqualTo(expectedScore);
        });
    }
}
