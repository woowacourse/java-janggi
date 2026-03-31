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

    public MovablePositions selectSource(Position position) {
        Piece piece = board.getPiece(position);
        players.getCurrentPlayer().validateAlly(piece);
        return findMovablePositions(position);
    }

    public void move(Position from, Position to) {
        selectSource(from).validateDestinations(to);
        movePiece(from, to);
        players.switchPlayer();
    }

    private MovablePositions findMovablePositions(Position position) {
        return board.findMovablePositions(position);
    }

    private void movePiece(Position from, Position to) {
        this.board = board.movePiece(from, to);
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }

    public boolean isOver() {
        return board.isGameOver();
    }

    public String getWinner() {
        players.switchPlayer();
        return players.getCurrentPlayer().getName();
    }
}
