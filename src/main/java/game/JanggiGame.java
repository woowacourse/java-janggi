package game;

import java.util.Map;

import board.Board;
import board.Position;
import piece.Piece;
import piece.Team;

public class JanggiGame {

    private final Board board;
    private final Turn turn;

    public JanggiGame(final Board board, final Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public void move(final Position startPosition, final Position destinationPosition) {
        board.isValidTurn(startPosition, turn);
        board.move(startPosition, destinationPosition);
        turn.increaseRound();
    }

    public Map<Team, Double> calculateTotalScore() {
        Map<Team, Double> scoreBoard = Team.initializeScoreBoard();
        Team teamRed = Team.RED;
        Team teamBlue = Team.BLUE;
        scoreBoard.put(teamRed, board.calculateTeamScore(teamRed, scoreBoard.get(teamRed)));
        scoreBoard.put(teamBlue, board.calculateTeamScore(teamBlue, scoreBoard.get(teamBlue)));
        return scoreBoard;
    }

    public boolean isFinish() {
        return board.isAllKingAlive();
    }

    public Team findWinnerTeam() {
        return board.findWinnerTeam();
    }

    public Turn getTurn() {
        return turn;
    }

    public Map<Position, Piece> getPieces() {
        return board.getPieces();
    }

}
