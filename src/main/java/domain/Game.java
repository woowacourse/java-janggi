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
        Piece selectedPiece = board.getPiece(source);
        players.validateAlly(selectedPiece);
        Destinations destinations = findDestinations(source);
        return new MoveCandidate(source, destinations);
    }

    public void move(MoveCandidate moveCandidate, Position target) {
        moveCandidate.validate(target);
        movePiece(moveCandidate.source(), target);
        players.switchPlayer();
    }

    private Destinations findDestinations(Position position) {
        return board.findDestinations(position);
    }

    private void movePiece(Position source, Position target) {
        this.board = board.movePiece(source, target);
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }

    public boolean isOver() {
        return board.isGameOver();
    }

    public String getWinner() {
        Side winnerSide = board.getWinnerSide();
        return players.getPlayerNameBySide(winnerSide);
    }
}
