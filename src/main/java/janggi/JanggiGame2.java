package janggi;

import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.domain.score.Score;
import janggi.domain.team.Team;

import java.util.Map;

public class JanggiGame2 {

    private final Board board;
    private Team currentTeam;

    public JanggiGame2(Board board, Team currentTeam) {
        this.board = board;
        this.currentTeam = currentTeam;
    }

    public void play(Movement movement) {
        board.move(movement, currentTeam);
        currentTeam = currentTeam.convert();
    }

    public boolean isFinished() {
        return board.isGeneralCaptured(currentTeam);
    }

    public Map<Position, Piece> getBoard() {
        return board.showBoard();
    }

    public Score getScore() {
        return Score.from(board.showBoard());
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public Team getWinner() {
        if (isFinished()) {
            return currentTeam.convert();
        }
        return Team.NONE;
    }
}
