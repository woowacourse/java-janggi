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
    private final Board board;
    private Camp turn;

    private Game(Board board, Camp turn) {
        this.board = board;
        this.turn = turn;
    }

    public static Game newGame(Board board) {
        return new Game(board, Camp.CHO);
    }

    public static Game restore(Board board, Camp currentTurn) {
        return new Game(board, currentTurn);
    }

    public Camp currentTurn() {
        return turn;
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
                .filter(PieceType::isGeneral)
                .isPresent();

        if (!gameEnded) {
            turn = turn.next();
        }

        return gameEnded;
    }
}
