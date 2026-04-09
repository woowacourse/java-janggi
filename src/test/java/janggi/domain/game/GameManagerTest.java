package janggi.domain.game;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameManagerTest {

    @DisplayName("턴을 전환하면 반대 진영의 플레이어 차례가 된다")
    @Test
    void switchTurn_ChangesCurrentPlayer() {
        GameManager gameManager = createGameManagerWithInitialBoard();
        Player initialPlayer = gameManager.currentPlayer();

        gameManager.switchTurn();
        Player nextPlayer = gameManager.currentPlayer();

        assertThat(initialPlayer).isNotEqualTo(nextPlayer);
    }

    @DisplayName("보드에 두 궁이 모두 존재하면 게임은 종료되지 않은 상태다")
    @Test
    void isFinished_BothPalacesExist_ReturnsFalse() {
        GameManager gameManager = createGameManagerWithInitialBoard();

        assertThat(gameManager.isFinished()).isFalse();
    }

    @DisplayName("선택한 위치에 현재 턴의 기물이 존재하면 참을 반환한다")
    @Test
    void isThereMoveablePiece_ValidCurrentTurnPiece_ReturnsTrue() {
        GameManager gameManager = createGameManagerWithInitialBoard();
        Position choPalacePosition = new Position(8, 4);

        assertThat(gameManager.isThereMoveablePiece(choPalacePosition)).isTrue();
    }

    @DisplayName("선택한 위치에 기물이 없거나 상대방 턴의 기물이면 거짓을 반환한다")
    @Test
    void isThereMoveablePiece_EmptyOrOpponentPiece_ReturnsFalse() {
        GameManager gameManager = createGameManagerWithInitialBoard();
        Position emptyPosition = new Position(5, 4);
        Position hanPalacePosition = new Position(0, 4);

        assertThat(gameManager.isThereMoveablePiece(emptyPosition)).isFalse();
        assertThat(gameManager.isThereMoveablePiece(hanPalacePosition)).isFalse();
    }

    private GameManager createGameManagerWithInitialBoard() {
        Players players = Players.from("초", "한");
        Board board = Board.initialize();
        return new GameManager(players, board);
    }
}
