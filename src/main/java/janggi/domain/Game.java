package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.player.Name;
import janggi.domain.player.Players;
import janggi.domain.space.Destinations;
import janggi.domain.space.Position;
import java.util.Map;

public class Game {
    private Board board;
    private final Players players;

    public Game(Board board, Players players) {
        this.board = board;
        this.players = players;
    }

    public Destinations selectSource(Position source) {
        players.validateAlly(board.getPiece(source));
        return board.findDestinations(source);
    }

    public void move(Position source, Position target) {
        validateGamePlaying();
        players.validateAlly(board.getPiece(source));
        this.board = board.movePiece(source, target);
        players.switchPlayer();
    }

    private void validateGamePlaying() {
        if (!isPlaying()) {
            throw new IllegalArgumentException("게임이 이미 종료되었습니다.");
        }
    }

    public boolean isPlaying() {
        return board.isPlaying();
    }

    public Score calculateScore(Side side) {
        if (side == Side.HAN) {
            return board.calculateScore(side).plus(new Score(1.5));
        }
        return board.calculateScore(side);
    }

    public Name getWinner() {
        Side winnerSide = board.getWinnerSide();
        return players.getPlayerNameBySide(winnerSide);
    }

    public Side getCurrentSide() {
        return players.getCurrentSide();
    }

    public Name getPlayerNameBySide(Side side) {
        return players.getPlayerNameBySide(side);
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }
}
