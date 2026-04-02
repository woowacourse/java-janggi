package domain.manager;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.piece.BasicPiece;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.player.Player;
import domain.position.Position;

public class GameManager {

    private final Board board;
    private Player currentPlayer;
    private Player standbyPlayer;
    private boolean isGameRunning;

    public GameManager(Player choPlayer, Player hanPlayer, Formation choFormation, Formation hanFormation) {
        this.currentPlayer = choPlayer;
        this.standbyPlayer = hanPlayer;
        this.board = BoardFactory.createWithFormation(choFormation, hanFormation);
        this.isGameRunning = true;
    }

    public void move(Position source, Position destination) {
        BasicPiece caughtPiece = board.move(source, destination, currentPlayer);
        if (!caughtPiece.isNone()) {
            standbyPlayer.removePiece((Piece) caughtPiece);
        }

        if (caughtPiece.isType(PieceType.JANG)) {
            isGameRunning = false;
            return;
        }

        switchTurn();
    }

    public void validateSource(Position source) {
        board.validateSource(source, currentPlayer);
    }

    public void switchTurn() {
        Player temp = currentPlayer;
        currentPlayer = standbyPlayer;
        standbyPlayer = temp;
    }

    public void endGame() {
        isGameRunning = false;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public Board getBoard() {
        return board;
    }
}
