package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Game {

    private static final Camp SECOND_PLAYER = Camp.HAN;
    private static final double SECOND_PLAYER_BONUS_SCORE = 1.5;

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
            double finalScore = applyBonusScore(camp, totalPieceScore);
            resultScore.put(camp, finalScore);
        });

        return resultScore;
    }

    private double applyBonusScore(Camp camp, double score) {
        if (camp == SECOND_PLAYER) {
            return score + SECOND_PLAYER_BONUS_SCORE;
        }
        return score;
    }

    public void validateSourceForCurrentTurn(Position source) {
        board.validateCampTurn(source, currentTurn());
    }

    public boolean play(Position source, Position destination) {
        Optional<Piece> caughtPiece = board.movePiece(source, destination, currentTurn());
        boolean gameEnded = caughtPiece
                .filter(Piece::isGeneral)
                .isPresent();

        if (!gameEnded) {
            turn = turn.next();
        }

        return gameEnded;
    }
}
