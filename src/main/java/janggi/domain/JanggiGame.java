package janggi.domain;

import janggi.domain.board.BoardView;
import janggi.domain.piece.Team;

import java.util.HashMap;
import java.util.Map;

public class JanggiGame {
    private final BoardView board;
    private Map<Team, Boolean> skip = new HashMap<>(){
        {
            put(Team.HAN, false);
            put(Team.CHO, false);
        }
    };
    private Team currentTurn = Team.CHO;
    private Team winner = Team.NONE;

    public JanggiGame(BoardView board) {
        this.board = board;
    }

    public boolean isFinished() {
        return isKingCaught() || winner != Team.NONE || allTeamSkip();
    }

    public Team getCurrentTeam() {
        return currentTurn;
    }

    public void changeTurn() {
        currentTurn = currentTurn.anotherTeam();
    }

    public void skipTurn() {
        skip.put(currentTurn, true);
    }

    public void playTurn() {
        skip.put(currentTurn, false);
    }


    public Team getWinner() {
        return winner;
    }

    public void resign() {
        winner = currentTurn.anotherTeam();
    }

    public void decideWinner() {
        ScoreBoard scoreBoard = new ScoreBoard();

        if (winner != Team.NONE) {
            return;
        }

        if (isKingCaught()) {
            winner = board.kingsOnBoard().get(0).findTeam();
            return;
        }

        scoreBoard.saveHanScore(board.piecesOf(Team.HAN));
        scoreBoard.saveChoScore(board.piecesOf(Team.CHO));

        winner = scoreBoard.winner();
    }

    private boolean allTeamSkip() {
        return skip.values().stream().filter(status -> status == true).count() == 2;
    }

    private boolean isKingCaught() {
        return board.kingsOnBoard().size() < 2;
    }
}
