package domain;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.BoardPosition;
import domain.piece.Piece;
import domain.piece.Team;
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
        final Board board = BoardFactory.createInitialBoard();
        final Team currentTeam = Team.GREEN;

        return new Janggi(board, currentTeam);
    }

    public void validateSelectedPiece(final BoardPosition selectBoardPosition) {
        board.findSelectedPiece(selectBoardPosition, currentTeam)
            .orElseThrow(() -> new IllegalArgumentException("해당 위치에 말이 없거나 상대팀의 말입니다."));
    }

    public void processTurn(
        final BoardPosition selectBoardPosition,
        final BoardPosition destinationBoardPosition
    ) {
        board.movePiece(selectBoardPosition, destinationBoardPosition, currentTeam);
        currentTeam = currentTeam.nextTeam();
    }

    public boolean isGameOver() {
        return board.isOnlyOneKingLeft();
    }

    public Team calculateWinner() {
        if (!isGameOver()) {
            throw new IllegalStateException("게임이 종료되지 않았습니다.");
        }
        
        return currentTeam.nextTeam();
    }

    public Map<BoardPosition, Piece> getPieces() {
        return board.getPieces();
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }
}
