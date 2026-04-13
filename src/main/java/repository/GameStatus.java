package repository;

import domain.Game;
import domain.Position;
import domain.Turn;
import domain.piece.Piece;

import java.util.Map;

public class GameStatus {

    private final Long gameId;
    private final Map<Position, Piece> board;
    private final Turn turn;

    private GameStatus(Long gameId, Map<Position, Piece> board, Turn turn) {
        this.gameId = gameId;
        this.board = board;
        this.turn = turn;
    }

    public static GameStatus of(Long gameId, Map<Position, Piece> board, Turn turn) {
        return new GameStatus(gameId, board, turn);
    }

    public Game toGame() {
        return new Game(gameId, turn, board);
    }
}
