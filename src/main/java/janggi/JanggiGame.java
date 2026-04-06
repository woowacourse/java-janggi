package janggi;

import janggi.domain.board.Board;
import janggi.domain.board.BoardFactory;
import janggi.domain.board.PieceSetup;
import janggi.domain.piece.Piece;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.domain.score.Score;
import janggi.domain.team.Team;

import java.util.List;
import java.util.Map;

public class JanggiGame {
    private static final int FROM_INDEX = 0;
    private static final int TO_INDEX = 1;

    private Board board;

    public void initialize(String hanSetup, String choSetup) {
        board = BoardFactory.create(PieceSetup.from(hanSetup), PieceSetup.from(choSetup));
    }

    public void playTurn(List<String> positions, Team currentTeam) {
        board.move(createMovement(positions), currentTeam);
    }

    public boolean isFinished(Team currentTeam) {
        return board.isGeneralCaptured(currentTeam);
    }

    public Map<Position, Piece> getBoard() {
        return board.showBoard();
    }

    public Score getScore() {
        return new Score(board.showBoard());
    }

    private Movement createMovement(List<String> positions) {
        Position from = Position.from(positions.get(FROM_INDEX));
        Position to = Position.from(positions.get(TO_INDEX));
        return new Movement(from, to);
    }
}
