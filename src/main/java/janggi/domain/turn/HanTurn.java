package janggi.domain.turn;

import janggi.domain.board.Board;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Team;
import java.util.Map;
import java.util.Optional;

public class HanTurn implements GameState {
    private static final double BONUS_SCORE = 1.5;

    private final Team team;
    private final Board board;

    public HanTurn(Board board) {
        this.team = Team.HAN;
        this.board = board;
    }

    @Override
    public GameState move(Position from, Position to) {
        board.move(from, to, team);

        if (board.isGameOver()) {
            return new GameOver(board);
        }

        return new ChoTurn(board);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public Map<Position, Space> captureBoard() {
        return board.getPiecesInfo();
    }

    @Override
    public Optional<Team> getTeam() {
        return Optional.of(team);
    }

    @Override
    public double calculatePieceScore() {
        return BONUS_SCORE + board.calculatePieceScore(team);
    }


}
