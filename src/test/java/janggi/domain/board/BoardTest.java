package janggi.domain.board;

import janggi.domain.Camp;
import janggi.domain.board.strategy.ElephantHorseElephantHorse;
import janggi.domain.piece.Advisor;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Position;
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
        Board board = BoardFactory.create(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());
        Map<Position, Piece> janggiBoard = board.janggiBoard();
        assertThat(janggiBoard.get(Position.of(1, 4))).isInstanceOf(General.class);
        assertThat(janggiBoard.get(Position.of(0, 0))).isInstanceOf(Chariot.class);
        assertThat(janggiBoard.get(Position.of(0, 8))).isInstanceOf(Chariot.class);
        assertThat(janggiBoard.get(Position.of(0, 3))).isInstanceOf(Advisor.class);
        assertThat(janggiBoard.get(Position.of(0, 5))).isInstanceOf(Advisor.class);
        assertThat(janggiBoard.get(Position.of(2, 1))).isInstanceOf(Cannon.class);
        assertThat(janggiBoard.get(Position.of(2, 7))).isInstanceOf(Cannon.class);
        for (int i = 0; i <= 8; i += 2) {
            assertThat(janggiBoard.get(Position.of(3, i))).isInstanceOf(Soldier.class);
        }
        assertThat(janggiBoard.get(Position.of(0, 1))).isInstanceOf(Elephant.class);
        assertThat(janggiBoard.get(Position.of(0, 2))).isInstanceOf(Horse.class);
        assertThat(janggiBoard.get(Position.of(0, 6))).isInstanceOf(Elephant.class);
        assertThat(janggiBoard.get(Position.of(0, 7))).isInstanceOf(Horse.class);

        assertThat(janggiBoard.get(Position.of(8, 4))).isInstanceOf(General.class);
        assertThat(janggiBoard.get(Position.of(9, 0))).isInstanceOf(Chariot.class);
        assertThat(janggiBoard.get(Position.of(9, 8))).isInstanceOf(Chariot.class);
        assertThat(janggiBoard.get(Position.of(9, 3))).isInstanceOf(Advisor.class);
        assertThat(janggiBoard.get(Position.of(9, 5))).isInstanceOf(Advisor.class);
        assertThat(janggiBoard.get(Position.of(7, 1))).isInstanceOf(Cannon.class);
        assertThat(janggiBoard.get(Position.of(7, 7))).isInstanceOf(Cannon.class);
        for (int i = 0; i <= 8; i += 2) {
            assertThat(janggiBoard.get(Position.of(6, i))).isInstanceOf(Soldier.class);
        }
        assertThat(janggiBoard.get(Position.of(9, 1))).isInstanceOf(Elephant.class);
        assertThat(janggiBoard.get(Position.of(9, 2))).isInstanceOf(Horse.class);
        assertThat(janggiBoard.get(Position.of(9, 6))).isInstanceOf(Elephant.class);
        assertThat(janggiBoard.get(Position.of(9, 7))).isInstanceOf(Horse.class);
    }

    @Test
    void 기물이_보드에_존재하지_않는다면_예외처리한다() {
        Board board = BoardFactory.create(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());
        int row = 1;
        int col = 0;

        assertThatThrownBy(() -> board.selectPiece(Position.of(row, col)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드에 기물이 존재하지 않습니다.");
    }

    @Test
    void 기물이_보드에_존재한다면_기물을_반환한다() {
        Board board = BoardFactory.create(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());
        int row = 0;
        int col = 0;

        Piece selectPiece = board.selectPiece(Position.of(row, col));

        Assertions.assertThat(selectPiece).isInstanceOf(Chariot.class);
    }

    @Test
    void 기물이_없는_위치를_선택하면_예외가_발생한다() {
        Board board = BoardFactory.create(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThatThrownBy(() -> board.movePiece(Position.of(5, 4), Position.of(4, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드에 기물이 존재하지 않습니다.");
    }

    @Test
    void 이동_규칙에_맞지_않는_위치로_이동하면_예외가_발생한다() {
        Board board = BoardFactory.create(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThatThrownBy(() -> board.movePiece(Position.of(0, 0), Position.of(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없는 좌표입니다.");
    }

    @Test
    void 경로에_기물이_있으면_예외가_발생한다() {
        Board board = BoardFactory.create(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThatThrownBy(() -> board.movePiece(Position.of(0, 0), Position.of(0, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("경로가 막혀있습니다.");
    }

    @Test
    void 도착지에_아군_기물이_있으면_예외가_발생한다() {
        Board board = BoardFactory.create(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThatThrownBy(() -> board.movePiece(Position.of(0, 0), Position.of(0, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("목적지의 기물을 잡을 수 없습니다.");
    }

    @Test
    void 빈_위치로_이동하면_기물이_옮겨진다() {
        Board board = BoardFactory.create(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        board.movePiece(Position.of(3, 0), Position.of(4, 0));

        assertThat(board.janggiBoard().get(Position.of(4, 0))).isNotNull();
        assertThat(board.janggiBoard().get(Position.of(3, 0))).isNull();
    }

    @Test
    void 도착지에_적군_기물이_있으면_잡고_이동한다() {
        Board board = BoardFactory.create(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());


        board.movePiece(Position.of(3, 4), Position.of(4, 4));
        board.movePiece(Position.of(4, 4), Position.of(5, 4));
        board.movePiece(Position.of(5, 4), Position.of(6, 4));

        Piece movedPiece = board.janggiBoard().get(Position.of(6, 4));
        assertThat(movedPiece.isSameCamp(Camp.CHO)).isTrue();
        assertThat(board.janggiBoard()).hasSize(31);
    }

    @ParameterizedTest
    @EnumSource(Camp.class)
    void 선택한_나라의_장이_있으면_true를_반환한다(Camp camp) {
        Board board = BoardFactory.create(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        boolean essentialPiece = board.isAliveEssentialPiece(camp);

        assertThat(essentialPiece).isTrue();
    }

    @Test
    void 한나라보다_초나라의_기물_점수가_더_크면_초나라가_이긴다() {
        Board board = BoardFactory.create(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());
        board.movePiece(Position.of(3, 0), Position.of(3, 1));
        board.movePiece(Position.of(0, 0), Position.of(6, 0));
        board.movePiece(Position.of(6, 0), Position.of(9, 0));
        board.movePiece(Position.of(9, 0), Position.of(9, 1));
        board.movePiece(Position.of(9, 1), Position.of(9, 2));
        board.movePiece(Position.of(9, 2), Position.of(9, 3));
        board.movePiece(Position.of(9, 3), Position.of(9, 5));

        assertThat(board.calculateScoreResult()).isEqualTo(Camp.CHO);
    }

    @Test
    void 초나라보다_한나라의_기물_점수가_더_크면_한나라가_이긴다() {
        Board board = BoardFactory.create(new ElephantHorseElephantHorse(), new ElephantHorseElephantHorse());

        assertThat(board.calculateScoreResult()).isEqualTo(Camp.HAN);
    }
}
