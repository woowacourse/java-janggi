package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.common.ErrorMessage;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.behavior.Soldier;
import janggi.domain.piece.behavior.palace.General;
import janggi.factory.PieceInitFactory;
import janggi.factory.horse_elephant.HorseElephantFactory;
import janggi.util.BoardFixture;
import janggi.view.HorseElephantPosition;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Board board;

    @BeforeEach
    void init() {
        Map<Position, Piece> initialize = PieceInitFactory.initialize();
        initialize.putAll(HorseElephantFactory.create(HorseElephantPosition.HORSE_ELEPHANT_HORSE_ELEPHANT, Team.CHO));
        initialize.putAll(HorseElephantFactory.create(HorseElephantPosition.HORSE_ELEPHANT_HORSE_ELEPHANT, Team.HAN));

        board = new Board(initialize);
    }

    @DisplayName("움직일 수 있는 기물인지 확인한다.")
    @Test
    void test1() {
        // given
        Position position = Position.of(7, 1);

        // when & then
        assertThatCode(() -> board.checkMoveablePiece(Team.CHO, position))
                .doesNotThrowAnyException();
    }

    @DisplayName("해당 포지션에 기물이 존재하지 않으면 예외를 반환한다.")
    @Test
    void test2() {
        // given
        Position position = Position.of(2, 1);

        // when & then
        assertThatThrownBy(() -> board.checkMoveablePiece(Team.CHO, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.POSITION_DOES_NOT_EXIST.getMessage());
    }

    @DisplayName("상대의 기물이라면 예외를 반환한다.")
    @Test
    void test3() {
        // given
        Position position = Position.of(1, 1);

        // when & then
        assertThatThrownBy(() -> board.checkMoveablePiece(Team.CHO, position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.IS_NOT_SAME_SIDE.getMessage());
    }

    @DisplayName("기물을 움직인다.")
    @Test
    void test4() {
        // given
        Position position = Position.of(5, 1);
        Piece soldier = new Piece(Team.CHO, new Soldier());

        Board board = new Board(Map.of(position, soldier));

        Position newPosition = Position.of(4, 1);

        // when & then
        assertThatCode(() -> board.movePiece(position, newPosition))
                .doesNotThrowAnyException();
    }

    @DisplayName("움직일 포지션에 우리 팀의 기물이 존재하면 예외를 발생한다.")
    @Test
    void test5() {
        // given
        Position position = Position.of(5, 1);
        Piece soldier1 = new Piece(Team.CHO, new Soldier());

        Position newPosition = Position.of(4, 1);
        Piece soldier2 = new Piece(Team.CHO, new Soldier());

        Board board = new Board(Map.of(position, soldier1, newPosition, soldier2));

        // when & then
        assertThatThrownBy(() -> board.movePiece(position, newPosition))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.CANNOT_MOVE_TO_POSITION.getMessage());
    }

    @DisplayName("상대의 기물이 있는 곳으로 움직일 수 있다.")
    @Test
    void test6() {
        // given
        Position position = Position.of(5, 1);
        Piece soldier1 = new Piece(Team.CHO, new Soldier());

        Position newPosition = Position.of(4, 1);
        Piece soldier2 = new Piece(Team.HAN, new Soldier());

        Board board = new Board(Map.of(position, soldier1, newPosition, soldier2));

        // when & then
        assertThatCode(() -> board.movePiece(position, newPosition))
                .doesNotThrowAnyException();
    }

    @DisplayName("보드의 General이 있다면 true를 반환한다.")
    @Test
    void test7() {
        // given
        Board board = new Board(PieceInitFactory.initialize());

        // when & then
        assertThat(board.hasGeneral(Team.HAN)).isTrue();
    }

    @DisplayName("보드의 General이 없다면 false를 반환한다.")
    @Test
    void test8() {
        // given
        Position position = Position.of(5, 1);
        Piece general = new Piece(Team.CHO, new General());

        Position newPosition = Position.of(4, 1);
        Piece soldier2 = new Piece(Team.HAN, new Soldier());

        Board board = new Board(Map.of(position, general, newPosition, soldier2));

        assertThat(board.hasGeneral(Team.HAN)).isFalse();
    }

    @DisplayName("초나라의 점수를 반환한다")
    @Test
    void test9() {
        // given
        Board board = BoardFixture.sangMaSangMa();

        double result = board.getScore(Team.CHO);

        assertThat(result).isEqualTo(72);
    }

    @DisplayName("한나라의 점수를 반환한다")
    @Test
    void test10() {
        // given
        Board board = BoardFixture.sangMaSangMa();

        double result = board.getScore(Team.HAN);

        assertThat(result).isEqualTo(73.5);
    }
}
