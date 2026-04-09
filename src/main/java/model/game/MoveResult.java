package model.game;

import model.coordinate.Position;
import model.piece.Piece;

import java.util.Map;
import java.util.Optional;

public class MoveResult {
    private final Map<Position, Piece> board;
    private final Team winner;

    public MoveResult(Map<Position, Piece> board, Team winner) {
        this.board = board;
        this.winner = winner;
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public Optional<Team> getWinner() {
        return Optional.ofNullable(winner);
    }
}
