package janggi.domain.board;

import janggi.domain.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> piecesByPosition;

    public Board(Map<Position, Piece> base) {
        this.piecesByPosition = base;
    }

    public Map<Position, Piece> move(Position from, Position to, Team currentTeam) {
        validate(from, to, currentTeam);
        Piece fromPiece = piecesByPosition.get(from);
        List<Position> path = fromPiece.getPath(from, to);
        List<Piece> pieceOnPath = getPiecesOnPath(path);
        if (fromPiece.canMove(pieceOnPath, piecesByPosition.get(to))) {
            piecesByPosition.put(from, new EmptyPiece());
            piecesByPosition.put(to, fromPiece);
        }
        return showBoard();
    }

    private List<Piece> getPiecesOnPath(List<Position> path) {
        List<Piece> pieceOnPath = new ArrayList<>();
        for (Position position : path) {
            pieceOnPath.add(piecesByPosition.get(position));
        }
        return pieceOnPath;
    }

    private void validate(Position from, Position to, Team currentTeam) {
        validateSamePosition(from, to);
        Piece fromPiece = piecesByPosition.get(from);
        validateEmptyPiece(fromPiece);
        validateCurrentTeamPiece(fromPiece, currentTeam);
    }

    private void validateSamePosition(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 출발 좌표와 도착 좌표는 같을 수 없습니다.");
        }
    }

    private void validateEmptyPiece(Piece fromPiece) {
        if (fromPiece.isEmptyPiece()) {
            throw new IllegalArgumentException("[ERROR] 선택된 기물이 없습니다.");
        }
    }

    private void validateCurrentTeamPiece(Piece fromPiece, Team currentTeam) {
        if (!fromPiece.isSame(currentTeam)) {
            throw new IllegalArgumentException("[ERROR] 자신의 기물만 이동시킬 수 있습니다.");
        }
    }

    public Map<Position, Piece> showBoard() {
        return Map.copyOf(piecesByPosition);
    }
}
