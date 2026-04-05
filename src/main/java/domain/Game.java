package domain;

import domain.board.Board;
import domain.piece.Piece;
import domain.player.Players;
import java.util.Map;

public class Game {
    private Board board;
    private final Players players;

    public Game(Board board, Players players) {
        this.board = board;
        this.players = players;
    }

    public Side getCurrentSide() {
        return players.getCurrentSide();
    }

    public MoveCandidate selectSource(Position source) {
        players.validateAlly(board.getPiece(source));
        Destinations destinations = board.findDestinations(source);
        return new MoveCandidate(source, destinations);
    }

    public void move(Position source, Position target) {
        players.validateAlly(board.getPiece(source));
        this.board = board.movePiece(source, target);
        players.switchPlayer();
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }

    public boolean isPlaying() {
        return board.isPlaying();
    }

    public String getWinner() {
        Side winnerSide = board.getWinnerSide();
        return players.getPlayerNameBySide(winnerSide);
    }
}
