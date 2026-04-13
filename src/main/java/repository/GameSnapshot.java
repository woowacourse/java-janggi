package repository;

import model.board.Board;
import model.board.PlacedPiece;
import model.game.JanggiGame;

import java.util.ArrayList;
import java.util.List;

public class GameSnapshot {
    private GameSnapshot() {
    }

    public static SavedGame from(JanggiGame game, Board board) {
        return new SavedGame(
                null,
                game.turn(),
                game.isFinished(),
                game.winner(),
                savedPieces(board)
        );
    }

    private static List<SavedPiece> savedPieces(Board board) {
        List<SavedPiece> savedPieces = new ArrayList<>();
        for (PlacedPiece placedPiece : board.placedPieces()) {
            savedPieces.add(new SavedPiece(
                    placedPiece.position().row().value(),
                    placedPiece.position().column().value(),
                    placedPiece.piece().country(),
                    placedPiece.piece().pieceType()));
        }
        return savedPieces;
    }
}
