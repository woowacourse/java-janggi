package domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Board;
import domain.board.Position;
import domain.board.SetUp;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameTest {

    @Test
    @DisplayName("게임 시작 시 초나라 턴으로 시작한다.")
    void startWithChoTurn() {
        Game game = Game.start(SetUp.LEFT_ELEPHANT, SetUp.RIGHT_ELEPHANT);

        assertThat(game.currentTurn()).isEqualTo(Camp.CHO);
    }

    @Test
    @DisplayName("현재 턴의 기물을 이동하면 보드 상태가 변경되고 턴이 넘어간다.")
    void movePieceAndChangeTurn() {
        Game game = Game.start(SetUp.LEFT_ELEPHANT, SetUp.RIGHT_ELEPHANT);
        Position from = new Position(1, 7);
        Position to = new Position(1, 6);

        game.playMove(from, to);

        assertThat(game.currentTurn()).isEqualTo(Camp.HAN);
        assertThat(game.board().findPiece(from)).isEmpty();

        Piece movedPiece = game.board().findBy(to);
        assertThat(movedPiece.camp()).isEqualTo(Camp.CHO);
        assertThat(movedPiece.type()).isEqualTo(PieceType.SOLDIER);
    }

    @Test
    @DisplayName("현재 턴이 아닌 기물을 이동하면 예외가 발생하고 턴은 유지된다.")
    void throwExceptionWhenMoveOpponentPiece() {
        Game game = Game.start(SetUp.LEFT_ELEPHANT, SetUp.RIGHT_ELEPHANT);
        Position from = new Position(1, 4);
        Position to = new Position(1, 5);

        assertThatThrownBy(() -> game.playMove(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 현재 턴의 기물만 움직일 수 있습니다.");

        assertThat(game.currentTurn()).isEqualTo(Camp.CHO);
        assertThat(game.board().findBy(from).camp()).isEqualTo(Camp.HAN);
    }

    @Test
    @DisplayName("한 턴을 넘기면 상대 진영의 기물을 이동할 수 있다.")
    void moveOpponentPieceAfterPassTurn() {
        Game game = Game.start(SetUp.LEFT_ELEPHANT, SetUp.RIGHT_ELEPHANT);
        Position from = new Position(1, 4);
        Position to = new Position(1, 5);

        game.passTurn();
        game.playMove(from, to);

        assertThat(game.currentTurn()).isEqualTo(Camp.CHO);
        assertThat(game.board().findPiece(from)).isEmpty();

        Piece movedPiece = game.board().findBy(to);
        assertThat(movedPiece.camp()).isEqualTo(Camp.HAN);
        assertThat(movedPiece.type()).isEqualTo(PieceType.SOLDIER);
    }


    @Test
    @DisplayName("장군(궁)이 잡히면 게임이 종료된다.")
    void finishGame_When_GeneralCaptured() {
        Board board = new Board(Map.of(
                new Position(5, 5), new Piece(Camp.CHO, PieceType.CHARIOT),
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL)
        ));
        Game game = Game.restore(board, Camp.CHO, false);

        game.playMove(new Position(5, 5), new Position(5, 2));

        assertThat(game.isFinished()).isTrue();
    }

    @Test
    @DisplayName("기물을 잡은 뒤 상대 진영의 남아 있는 점수가 줄어든다.")
    void decreaseOpponentScore_When_CapturePiece() {
        Board board = new Board(Map.of(
                new Position(5, 5), new Piece(Camp.CHO, PieceType.CHARIOT),
                new Position(9, 1), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(5, 2), new Piece(Camp.HAN, PieceType.SOLDIER),
                new Position(4, 2), new Piece(Camp.HAN, PieceType.GUARD)
        ));
        Game game = Game.restore(board, Camp.CHO, false);

        game.playMove(new Position(5, 5), new Position(5, 2));

        assertThat(game.scoreOf(Camp.HAN)).isEqualTo(3);
    }

    @Test
    @DisplayName("상대 장군을 위협하는 수를 두면 장군 상태를 반환한다.")
    void returnCheckedCamp_When_MoveCausesCheck() {
        Board board = new Board(Map.of(
                new Position(5, 9), new Piece(Camp.CHO, PieceType.GENERAL),
                new Position(5, 2), new Piece(Camp.HAN, PieceType.GENERAL),
                new Position(1, 5), new Piece(Camp.CHO, PieceType.CHARIOT)
        ));
        Game game = Game.restore(board, Camp.CHO, false);

        TurnResult turnResult = game.playMove(new Position(1, 5), new Position(5, 5));

        assertThat(turnResult.checkedCamp()).contains(Camp.HAN);
        assertThat(turnResult.winner()).isEmpty();
        assertThat(game.currentTurn()).isEqualTo(Camp.HAN);
    }

    @Test
    @DisplayName("턴을 넘기면 빈 결과를 반환하고 턴이 변경된다.")
    void returnEmptyResult_When_PassTurn() {
        Game game = Game.start(SetUp.LEFT_ELEPHANT, SetUp.RIGHT_ELEPHANT);

        TurnResult turnResult = game.passTurn();

        assertThat(turnResult.capturedPieceType()).isEmpty();
        assertThat(turnResult.checkedCamp()).isEmpty();
        assertThat(turnResult.winner()).isEmpty();
        assertThat(game.currentTurn()).isEqualTo(Camp.HAN);
    }
}
