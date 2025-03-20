package domain;

import java.util.Map;

public class Janggi {

    private final Board board;
    private Team currentTeam;

    public Janggi(
            final Board board,
            final Team currentTeam
    ) {
        validateNotNull(board, currentTeam);
        this.board = board;
        this.currentTeam = currentTeam;
    }

    private void validateNotNull(
            final Board board,
            final Team currentTeam
    ) {
        if (board == null || currentTeam == null) {
            throw new IllegalArgumentException("보드는 보드와 현재 팀을 가져야합니다.");
        }
    }

    public static Janggi initialize() {
        final Board board = Board.initialize();
        final Team currentTeam = Team.GREEN;
        return new Janggi(board, currentTeam);
    }

    public void processTurn(
            final BoardPosition selectBoardPosition,
            final BoardPosition destinationBoardPosition
    ) {
        board.movePiece(selectBoardPosition, destinationBoardPosition, currentTeam);
        currentTeam = currentTeam.nextTeam();
    }

    public Map<BoardPosition, Piece> getPieces() {
        return board.getPieces();
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }
}
