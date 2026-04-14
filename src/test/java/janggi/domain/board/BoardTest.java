package janggi.domain.board;

import janggi.domain.Camp;
import janggi.domain.JanggiPosition;
import janggi.domain.board.strategy.ElephantHorseElephantHorse;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Piece;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BoardTest {

    @DisplayName("보드 초기화가 잘 되는지 확인한다")
    @Test
    void initializeToBoard_Always_ReturnCorrectBoard() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());
        Map<JanggiPosition, String> janggiBoard = board.displayBoard();
        assertThat(janggiBoard.get(JanggiPosition.of(1, 4))).isEqualTo("楚");
        assertThat(janggiBoard.get(JanggiPosition.of(0, 0))).isEqualTo("車");
        assertThat(janggiBoard.get(JanggiPosition.of(0, 8))).isEqualTo("車");
        assertThat(janggiBoard.get(JanggiPosition.of(0, 3))).isEqualTo("士");
        assertThat(janggiBoard.get(JanggiPosition.of(0, 5))).isEqualTo("士");
        assertThat(janggiBoard.get(JanggiPosition.of(2, 1))).isEqualTo("包");
        assertThat(janggiBoard.get(JanggiPosition.of(2, 7))).isEqualTo("包");
        for (int i = 0; i <= 8; i += 2) {
            assertThat(janggiBoard.get(JanggiPosition.of(3, i))).isEqualTo("卒");
        }
        assertThat(janggiBoard.get(JanggiPosition.of(Camp.CHO.baselineRow(), 1))).isEqualTo("象");
        assertThat(janggiBoard.get(JanggiPosition.of(Camp.CHO.baselineRow(), 2))).isEqualTo("馬");
        assertThat(janggiBoard.get(JanggiPosition.of(Camp.CHO.baselineRow(), 6))).isEqualTo("象");
        assertThat(janggiBoard.get(JanggiPosition.of(Camp.CHO.baselineRow(), 7))).isEqualTo("馬");

        assertThat(janggiBoard.get(JanggiPosition.of(8, 4))).isEqualTo("漢");
        assertThat(janggiBoard.get(JanggiPosition.of(9, 0))).isEqualTo("車");
        assertThat(janggiBoard.get(JanggiPosition.of(9, 8))).isEqualTo("車");
        assertThat(janggiBoard.get(JanggiPosition.of(9, 3))).isEqualTo("士");
        assertThat(janggiBoard.get(JanggiPosition.of(9, 5))).isEqualTo("士");
        assertThat(janggiBoard.get(JanggiPosition.of(7, 1))).isEqualTo("包");
        assertThat(janggiBoard.get(JanggiPosition.of(7, 7))).isEqualTo("包");
        for (int i = 0; i <= 8; i += 2) {
            assertThat(janggiBoard.get(JanggiPosition.of(6, i))).isEqualTo("兵");
        }
        assertThat(janggiBoard.get(JanggiPosition.of(Camp.HAN.baselineRow(), 1))).isEqualTo("象");
        assertThat(janggiBoard.get(JanggiPosition.of(Camp.HAN.baselineRow(), 2))).isEqualTo("馬");
        assertThat(janggiBoard.get(JanggiPosition.of(Camp.HAN.baselineRow(), 6))).isEqualTo("象");
        assertThat(janggiBoard.get(JanggiPosition.of(Camp.HAN.baselineRow(), 7))).isEqualTo("馬");
    }

    @Test
    void 기물이_보드에_존재하지_않는다면_예외처리한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());
        int row = 1;
        int col = 0;

        assertThatThrownBy(() -> board.selectPiece(JanggiPosition.of(row, col)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드에 기물이 존재하지 않습니다.");
    }

    @Test
    void 기물이_보드에_존재한다면_기물을_반환한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        Piece selectPiece = board.selectPiece(JanggiPosition.of(0, 0));

        Assertions.assertThat(selectPiece).isInstanceOf(Chariot.class);
    }

    @Test
    void 기물이_없는_위치를_선택하면_예외가_발생한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThatThrownBy(() -> board.movePiece(JanggiPosition.of(4, 4), JanggiPosition.of(5, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드에 기물이 존재하지 않습니다.");
    }

    @Test
    void 이동_규칙에_맞지_않는_위치로_이동하면_예외가_발생한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThatThrownBy(() -> board.movePiece(JanggiPosition.of(0, 0), JanggiPosition.of(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 좌표입니다.");
    }

    @Test
    void 경로에_기물이_있으면_예외가_발생한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThatThrownBy(() -> board.movePiece(JanggiPosition.of(0, 0), JanggiPosition.of(0, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("경로가 막혀있습니다.");
    }

    @Test
    void 도착지에_아군_기물이_있으면_예외가_발생한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThatThrownBy(() -> board.movePiece(JanggiPosition.of(0, 0), JanggiPosition.of(0, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("목적지의 기물을 잡을 수 없습니다.");
    }

    @Test
    void 빈_위치로_이동하면_기물이_옮겨진다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        board.movePiece(JanggiPosition.of(3, 0), JanggiPosition.of(4, 0));

        assertThat(board.displayBoard().get(JanggiPosition.of(4, 0))).isNotNull();
        assertThat(board.displayBoard().get(JanggiPosition.of(3, 0))).isNull();
    }

    @Test
    void 도착지에_적군_기물이_있으면_잡고_이동한다() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());


        board.movePiece(JanggiPosition.of(3, 4), JanggiPosition.of(4, 4));
        board.movePiece(JanggiPosition.of(4, 4), JanggiPosition.of(5, 4));
        board.movePiece(JanggiPosition.of(5, 4), JanggiPosition.of(6, 4));

        String movedPiece = board.displayBoard().get(JanggiPosition.of(6, 4));
        assertThat(movedPiece).isEqualTo("卒");
        assertThat(movedPiece).isNotEqualTo("兵");
    }

    @DisplayName("보드에 장의 개수가 1개가 아니라면 false를 반환한다")
    @Test
    void isOnlyGeneralOfCampAlive_IsNumberOfGeneralsNotOne_ReturnFalse() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThat(board.isOnlyGeneralOfCampAlive(Camp.CHO)).isFalse();
    }

    @DisplayName("보드에 살아있는 장이 파라미터와 같은 진영이면 true를 반환한다")
    @Test
    void isOnlyGeneralOfCampAlive_IsGeneralSameCamp_ReturnTrue() {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());
        Camp camp = Camp.CHO;

        board.movePiece(JanggiPosition.of(3, 4), JanggiPosition.of(4, 4));
        board.movePiece(JanggiPosition.of(4, 4), JanggiPosition.of(5, 4));
        board.movePiece(JanggiPosition.of(5, 4), JanggiPosition.of(6, 4));
        board.movePiece(JanggiPosition.of(6, 4), JanggiPosition.of(7, 4));
        board.movePiece(JanggiPosition.of(7, 4), JanggiPosition.of(8, 4));

        assertThat(board.isOnlyGeneralOfCampAlive(camp)).isTrue();
    }

    @DisplayName("해당 진영의 총 점수를 계산해서 반환한다")
    @ParameterizedTest
    @EnumSource(Camp.class)
    void calculateTotalScore_ReturnScore(Camp camp) {
        Board board = Board.initializeToBoard(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThat(board.calculateTotalScore(camp)).isEqualTo(72);
    }
}
