package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.Team;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;
    private Turn turn = new Turn(Team.HAN);

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public void move(Position from, Position to) {
        validatePieceExistsAt(from);
        Piece piece = board.get(from);
        validateCurrentTurn(piece);

        if (!piece.canMove(from, to, this)) {
            throw new IllegalArgumentException("해당 기물은 이동할 수 없습니다.");
        }

        movePiece(from, to);
        changeTurn();
    }

    public boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    public Map<Position, Piece> findPiecesOn(List<Position> positions) {
        Map<Position, Piece> positionPieces = new LinkedHashMap<>();

        for (Position position : positions) {
            if (board.containsKey(position)) {
                positionPieces.put(position, board.get(position));
            }
        }
        return positionPieces;
    }

    private boolean isCurrentTeamPiece(Piece piece) {
        return turn.isCurrentTeam(piece.getTeam());
    }

    private void movePiece(Position from, Position to) {
        board.put(to, board.get(from));
        board.remove(from);
    }

    private void changeTurn() {
        this.turn = turn.changeTurn();
    }

    private void validatePieceExistsAt(Position from) {
        if (!hasPieceAt(from)) {
            throw new IllegalArgumentException("해당 출발 위치에는 기물이 존재하지 않습니다");
        }
    }

    private void validateCurrentTurn(Piece piece) {
        if (!isCurrentTeamPiece(piece)) {
            throw new IllegalArgumentException("해당 기물은 현재 턴의 진영 기물이 아닙니다.");
        }
    }
}
