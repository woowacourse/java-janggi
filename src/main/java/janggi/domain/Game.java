package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Game {
    private final long id;
    private final Board board;
    private Camp currentTurn;

    private Game(long id, Board board, Camp currentTurn) {
        this.id = id;
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public long id() {
        return id;
    }

    public static Game start(long id, Board board) {
        return new Game(id, board, Camp.CHO);
    }

    public static Game restore(long id, Board board, Camp currentTurn) {
        return new Game(id, board, currentTurn);
    }

    public Camp currentTurn() {
        return currentTurn;
    }

    public Map<Position, Piece> boardSnapshot() {
        return board.getBoard();
    }

    public Map<Camp, Double> calculateScore() {
        Map<Camp, Double> resultScore = new HashMap<>();

        Camp.getAllCamp().forEach(camp -> {
            double totalPieceScore = board.calculatePieceScore(camp);
            double finalScore = camp.addBonusTo(totalPieceScore);
            resultScore.put(camp, finalScore);
        });

        return resultScore;
    }

    public void validateSourceForCurrentTurn(Position source) {
        board.validateCampTurn(source, currentTurn());
    }

    public boolean play(Position source, Position destination) {
        Optional<PieceType> caughtPieceType = board.movePiece(source, destination, currentTurn());
        boolean gameEnded = caughtPieceType
                .filter(pieceType -> pieceType == PieceType.GENERAL)
                .isPresent();

        if (!gameEnded) {
            currentTurn = currentTurn.next();
        }

        return gameEnded;
    }
}
