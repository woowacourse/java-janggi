package janggi.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.fixture.TestBoardGenerator;
import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import janggi.piece.Team;
import janggi.piece.Type;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.score.ScoreBoard;
import janggi.view.SetupOption;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardGeneratorTest {

    @DisplayName("안상을 생성하면 상이 안쪽에 있어야한다.")
    @Test
    void testInnerSetup() {
        // given
        final Board board = BoardGenerator.generateOriginalSetup(SetupOption.INNER_SETUP);
        // when
        final Piece piece1 = board.getPiece(new Position(Row.ZERO, Column.TWO));
        final Piece piece2 = board.getPiece(new Position(Row.ZERO, Column.SIX));
        // then
        assertAll(
                () -> assertThat(piece1.getType()).isEqualTo(Type.ELEPHANT),
                () -> assertThat(piece2.getType()).isEqualTo(Type.ELEPHANT)
        );
    }

    @DisplayName("바깥상을 생성하면 상이 바깥쪽에 있어야한다.")
    @Test
    void testOuterSetup() {
        // given
        final Board board = BoardGenerator.generateOriginalSetup(SetupOption.OUTER_SETUP);
        // when
        final Piece piece1 = board.getPiece(new Position(Row.ZERO, Column.ONE));
        final Piece piece2 = board.getPiece(new Position(Row.ZERO, Column.SEVEN));
        // then
        assertAll(
                () -> assertThat(piece1.getType()).isEqualTo(Type.ELEPHANT),
                () -> assertThat(piece2.getType()).isEqualTo(Type.ELEPHANT)
        );
    }

    @DisplayName("왼상을 생성하면 상이 왼쪽에 있어야한다.")
    @Test
    void testLeftSetup() {
        // given
        final Board board = BoardGenerator.generateOriginalSetup(SetupOption.LEFT_SETUP);
        // when
        final Piece piece1 = board.getPiece(new Position(Row.ZERO, Column.TWO));
        final Piece piece2 = board.getPiece(new Position(Row.ZERO, Column.SEVEN));
        // then
        assertAll(
                () -> assertThat(piece1.getType()).isEqualTo(Type.ELEPHANT),
                () -> assertThat(piece2.getType()).isEqualTo(Type.ELEPHANT)
        );
    }

    @DisplayName("오른상을 생성하면 상이 오른쪽에 있어야한다.")
    @Test
    void testRightSetup() {
        // given
        final Board board = BoardGenerator.generateOriginalSetup(SetupOption.RIGHT_SETUP);
        // when
        final Piece piece1 = board.getPiece(new Position(Row.ZERO, Column.ONE));
        final Piece piece2 = board.getPiece(new Position(Row.ZERO, Column.SIX));
        // then
        assertAll(
                () -> assertThat(piece1.getType()).isEqualTo(Type.ELEPHANT),
                () -> assertThat(piece2.getType()).isEqualTo(Type.ELEPHANT)
        );
    }

    @DisplayName("점수판을 계산한다.")
    @Test
    void testCalculateScoreBoard() {
        // given
        final Board board = new Board(Map.of(
                new Position(Row.ZERO, Column.ONE), Soldier.of(Team.CHO),
                new Position(Row.ZERO, Column.TWO), Elephant.of(Team.CHO),
                new Position(Row.ZERO, Column.THREE), Chariot.of(Team.CHO),
                new Position(Row.ZERO, Column.FOUR), Cannon.of(Team.CHO),
                new Position(Row.ZERO, Column.FIVE), Soldier.of(Team.HAN),
                new Position(Row.ZERO, Column.SIX), Horse.of(Team.HAN),
                new Position(Row.ZERO, Column.SEVEN), Chariot.of(Team.HAN)
        ), 0);
        // when
        final ScoreBoard scoreBoard = board.calculateScoreBoard();
        // then
        assertAll(
                () -> assertThat(scoreBoard.getScore(Team.CHO)).isEqualTo(25),
                () -> assertThat(scoreBoard.getScore(Team.HAN)).isEqualTo(21.5)
        );
    }

    @DisplayName("장이 1개인지 확인한다.")
    @Test
    void testIsGeneralDead() {
        // given
        final Board board = TestBoardGenerator.generateBoardWithOnePiece(new Position(Row.ONE, Column.FOUR),
                General.of(Team.CHO));
        // when
        // then
        assertThat(board.isGeneralDead()).isTrue();
    }

    @DisplayName("두 장이 살아있을 때 승리팀을 계산한다.")
    @Test
    void testFindWinnerWhenTwoGeneralIsAlive() {
        // given
        final Board board = new Board(Map.of(
                new Position(Row.ZERO, Column.TWO), Elephant.of(Team.CHO),
                new Position(Row.ZERO, Column.FOUR), General.of(Team.CHO),
                new Position(Row.ZERO, Column.FIVE), General.of(Team.HAN)
        ), 0);
        // when
        // then
        assertThat(board.findWinner()).isEqualTo(Team.CHO);
    }

    @DisplayName("한 팀의 장군이 죽었을 때 살아남은 장군의 팀이 승리한다.")
    @Test
    void testFindWinnerWhenOneGeneralIsDead() {
        // given
        final Board board = new Board(Map.of(
                new Position(Row.ZERO, Column.TWO), Elephant.of(Team.CHO),
                new Position(Row.ZERO, Column.FOUR), General.of(Team.CHO),
                new Position(Row.ZERO, Column.FIVE), Chariot.of(Team.HAN),
                new Position(Row.ZERO, Column.SEVEN), Chariot.of(Team.HAN)
        ), 0);
        // when
        // then
        assertThat(board.findWinner()).isEqualTo(Team.CHO);
    }
}
