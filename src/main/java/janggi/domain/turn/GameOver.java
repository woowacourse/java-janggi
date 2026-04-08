package janggi.domain.turn;

import janggi.domain.board.Board;
import janggi.domain.position.Position;
import janggi.domain.space.Space;
import janggi.domain.space.piece.Team;
import java.util.Map;
import java.util.Optional;

public class GameOver implements GameState {

    private final Board board;

    public GameOver(Board board) {
        this.board = board;
    }

    @Override
    public GameState move(Position from, Position to) {
        throw new IllegalArgumentException("게임이 종료되었습니다.");
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public Map<Position, Space> captureBoard() {
        return board.getPiecesInfo();
    }

    @Override
    public Optional<Team> getTeam() {
        return Optional.empty();
    }

    @Override
    public double calculatePieceScore() {
        throw new IllegalStateException("종료된 게임입니다.");
    }
}
