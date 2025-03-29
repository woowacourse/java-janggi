package janggi.temp;

import janggi.temp.piece.Piece;
import janggi.temp.position.Position;

public final class Game {

    private Team turn;
    private final Board board;

    public Game(final Board board) {
        this.turn = Team.CHO;
        this.board = board;
    }

    public void move(final Position source, final Position destination) {
        final Piece piece = board.get(source);
        validateTurn(piece);
        validatePositions(source, destination, piece);
        piece.validateMove(source, destination, board);
    }

    private void validateTurn(final Piece piece) {
        if (piece.team() != turn) {
            throw new IllegalArgumentException(String.format("[ERROR] %s팀의 순서가 아닙니다.", piece.team().getName()));
        }
    }

    private void validatePositions(final Position source, final Position destination, final Piece piece) {
        if (board.hasSameTeam(destination, piece.team())) {
            throw new IllegalArgumentException("[ERROR] 같은 팀의 기물은 잡을 수 없습니다.");
        }
        if (source.equals(destination)) {
            throw new IllegalArgumentException("[ERROR] 본인의 위치로는 이동할 수 없습니다.");
        }
    }
}
