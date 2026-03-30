package domain;

import java.util.List;
import java.util.Map;

public class Game {
    private final Board board;
    private final Players players;

    public Game(Board board, Players players) {
        this.board = board;
        this.players = players;
    }

    public Side getCurrentSide() {
        return players.getCurrentSide();
    }

    public List<Position> selectSource(Position position) {
        Piece piece = board.getPiece(position);
        players.getCurrentPlayer().validateAlly(piece);
        return getPossibleDestinations(position);
    }

    public void move(Position from, Position to) {
        validateDestinations(selectSource(from), to);
        movePiece(from, to);
        players.switchPlayer();
    }

    private void validateDestinations(List<Position> positions, Position target) {
        if (!positions.contains(target)) {
            throw new IllegalArgumentException("선택할 수 없는 위치입니다.");
        }
    }

    private List<Position> getPossibleDestinations(Position position) {
        return board.getPossibleDestinations(position);
    }

    private void movePiece(Position from, Position to) {
        board.movePiece(from, to);
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }

    public boolean isOver() {
        return board.isGameOver();
    }

    public String getWinner() {
        players.switchPlayer();
        return players.getCurrentPlayer().getName();
    }
}
