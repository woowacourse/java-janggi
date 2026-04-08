package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.piece.Team;
import janggi.domain.position.Position;
import janggi.exception.business.InvalidTurnException;

import java.util.Optional;

public class JanggiGame {
    private Integer gameId;
    private final Board board;
    private Team currentTeam;

    public JanggiGame(Board board) {
        this.gameId = null;
        this.board = board;
        this.currentTeam = Team.CHO;
    }

    public JanggiGame(Integer gameId, Board board, Team currentTeam) {
        this.gameId = gameId;
        this.board = board;
        this.currentTeam = currentTeam;
    }

    public void assignId(Integer gameId) {
        this.gameId = gameId;
    }

    public void move(Position from, Position to) {
        if (board.getPieceAt(from).getTeam() != currentTeam) {
            throw new InvalidTurnException(currentTeam);
        }
        board.move(from, to);

        this.currentTeam = currentTeam.switchTeam();
    }

    public int calculateScore(Team team) {
        return board.getPieces().stream()
                .filter(piece -> piece.getTeam() == team)
                .mapToInt(piece -> piece.getPieceType().getScore())
                .sum();
    }

    public Optional<Team> getWinner() {
        boolean isChoKingAlive = board.getPieces().stream()
                .anyMatch(piece -> piece.getTeam() == Team.CHO && piece.isKing());
        boolean isHanKingAlive = board.getPieces().stream()
                .anyMatch(piece -> piece.getTeam() == Team.HAN && piece.isKing());

        if (!isChoKingAlive) {
            return Optional.of(Team.HAN);
        }
        if (!isHanKingAlive) {
            return Optional.of(Team.CHO);
        }
        return Optional.empty();
    }

    public Board getBoard() {
        return board;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public Integer getGameId() {
        return gameId;
    }
}
