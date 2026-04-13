package janggi.domain.janggiGame;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import janggi.domain.vo.FinishStatus;
import janggi.domain.vo.position.Position;

import java.util.HashMap;
import java.util.Map;

public class JanggiGame {
    private final Board board;
    private final Map<Team, Boolean> skip = new HashMap<>() {
        {
            put(Team.HAN, false);
            put(Team.CHO, false);
        }
    };
    private Team currentTurn;
    private Team winner = Team.NONE;

    public JanggiGame(Board board, Team currentTurn) {
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public JanggiGame(Board board) {
        this(board, Team.CHO);
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }

    public FinishStatus getFinishStatus() {
        return new FinishStatus(isKingCaught() || winner != Team.NONE || allTeamSkip());
    }

    public Team getCurrentTeam() {
        return currentTurn;
    }

    public void move(Position from, Position to) {
        skip.put(currentTurn, false);
        board.move(from, to, currentTurn);
        currentTurn = currentTurn.anotherTeam();
    }

    public void skipTurn() {
        skip.put(currentTurn, true);
        currentTurn = currentTurn.anotherTeam();
    }

    public void resign() {
        winner = currentTurn.anotherTeam();
    }

    public Team decideWinner() {
        if (winner != Team.NONE) {
            return winner;
        }

        if (isKingCaught()) {
            winner = board.kingsOnBoard().get(0).findTeam();
            return winner;
        }

        ScoreBoard scoreBoard = new ScoreBoard(board.piecesOf(Team.HAN), board.piecesOf(Team.CHO));

        return scoreBoard.winner();
    }

    private boolean allTeamSkip() {
        return skip.values().stream().filter(status -> status).count() == 2;
    }

    private boolean isKingCaught() {
        return board.kingsOnBoard().size() < 2;
    }
}
