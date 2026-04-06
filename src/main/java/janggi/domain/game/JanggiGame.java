package janggi.domain.game;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.domain.board.Turn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;

public class JanggiGame {
    private final Board board;
    private Turn turn;

    public JanggiGame(Board board) {
        this.board = board;
        this.turn = new Turn(Team.HAN);
    }

    public void move(Position from, Position to) {
        validatePieceExistsAt(from);
        Piece piece = board.findPiece(from);
        validateCurrentTurn(piece);
        board.move(from, to);
        turn = turn.changeTurn();
    }

    public Board board() {
        return board;
    }

    private void validateCurrentTurn(Piece piece) {
        if (!isCurrentTeamPiece(piece)) {
            throw new IllegalArgumentException("해당 기물은 현재 턴의 진영 기물이 아닙니다.");
        }
    }

    private boolean isCurrentTeamPiece(Piece piece) {
        return turn.isCurrentTeam(piece.getTeam());
    }

    private void validatePieceExistsAt(Position from) {
        if (!board.hasPieceAt(from)) {
            throw new IllegalArgumentException("해당 출발 위치에는 기물이 존재하지 않습니다");
        }
    }
}
