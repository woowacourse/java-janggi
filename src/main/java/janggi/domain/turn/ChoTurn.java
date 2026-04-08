package janggi.domain.turn;

import janggi.domain.board.Board;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Team;
import java.util.Map;
import java.util.Optional;

public class ChoTurn implements GameState {
    private final Team team;
    private final Board board;

    public ChoTurn(Board board) {
        team = Team.CHO;
        this.board = board;
    }

    @Override
    public GameState move(Position from, Position to) {
        board.move(from, to, team);

        if (board.isGameOver()) {
            return new GameOver(board);
        }
        return new HanTurn(board);
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
        return board.calculatePieceScore(team);
    }
}
