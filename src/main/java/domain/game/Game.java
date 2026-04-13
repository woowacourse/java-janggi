package domain.game;

import domain.board.Board;
import domain.common.Position;
import domain.common.Side;
import domain.movement.Destinations;
import domain.piece.Piece;
import domain.player.Players;
import domain.score.ScorePolicy;
import java.util.Map;

public class Game {
    private Board board;
    private final Players players;
    private final ScorePolicy scorePolicy;

    public Game(Board board, Players players, ScorePolicy scorePolicy) {
        this.board = board;
        this.players = players;
        this.scorePolicy = scorePolicy;
    }

    public Side getCurrentSide() {
        return players.getCurrentSide();
    }

    public Destinations selectSource(Position position) {
        Piece piece = board.getPiece(position);
        players.getActiveTurnPlayer().validateAlly(piece);
        return findDestinations(position);
    }

    public void move(Position source, Position target) {
        Destinations destinations = selectSource(source);
        destinations.validateDestinations(target);
        movePiece(source, target);
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

    public double getScore(Side side) {
        return scorePolicy.calculate(board, side);
    }

    public String getWinner() {
        return players.getInActiveTurnPlayer().getName();
    }
}
