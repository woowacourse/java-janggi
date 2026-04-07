package janggi.domain;

import janggi.domain.piece.King;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;

public class JanggiGame {
    private Long gameId;
    private Team currentTurn = Team.CHO;
    private boolean isFinished = false;
    private Team winner = null;

    // 기본 생성자 - 새게임
    public JanggiGame() {
    }

    // DB 복원용
    public JanggiGame(Long gameId, Team currentTurn, boolean isFinished, Team winner) {
        this.gameId = gameId;
        this.currentTurn = currentTurn;
        this.isFinished = isFinished;
        this.winner = winner;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Team findCurrentTeam() {
        return currentTurn;
    }

    public void changeTurn() {
        if (currentTurn == Team.CHO) {
            currentTurn = Team.HAN;
            return;
        }
        currentTurn = Team.CHO;
    }

    public Long findGameId() {
        return gameId;
    }

    public Team findWinner() {
        return winner;
    }

    public void processCaptured(Piece capturedPiece) {
        if (!(capturedPiece instanceof King)) {
            return;
        }
        isFinished = true;
        winner = findOpponent(capturedPiece.findTeam());
    }

    private Team findOpponent(Team team) {
        if (team == Team.CHO) {
            return Team.HAN;
        }
        return Team.CHO;
    }

}
