package domain;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.board.SelectedPositions;
import domain.piece.Piece;
import java.util.Map;

public class Janggi {

    private final Board board;
    private final Turn turn;

    public Janggi(
            final Board board,
            final Turn turn
    ) {
        validateNotNull(board, turn);
        this.board = board;
        this.turn = turn;
    }

    private void validateNotNull(
            final Board board,
            final Turn turn
    ) {
        if (board == null || turn == null) {
            throw new IllegalArgumentException("보드는 보드와 현재 턴을 가져야합니다.");
        }
    }

    public static Janggi initialize() {
        final Board board = Board.initialize();
        final Turn turn = new Turn(Team.GREEN);
        return new Janggi(board, turn);
    }

    public void processTurn(final SelectedPositions selectedPositions) {
        board.movePiece(
                selectedPositions.selectPosition(),
                selectedPositions.destinationPosition(),
                turn.currentTeam()
        );

        turn.change();
    }

    public Map<BoardPosition, Piece> getPieces() {
        return board.getPieces();
    }

    public Team getCurrentTeam() {
        return turn.currentTeam();
    }
}
