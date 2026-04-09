package janggi.domain.board;

import janggi.domain.Team;
import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;

import java.util.Map;

public class Board {

    private final Map<Position, Piece> piecesByPosition;

    public Board(Map<Position, Piece> base) {
        this.piecesByPosition = base;
    }

    public void move(Movement movement, Team currentTeam) {
        Piece fromPiece = getPiece(movement.getFrom());
        validate(fromPiece, currentTeam);
        Path path = fromPiece.getPath(movement);
        PieceOnPath pieceOnPath = getPiecesOnPath(path);
        fromPiece.validateCanMove(pieceOnPath, getPiece(movement.getTo()));
        executeMove(movement, fromPiece);
    }

    public boolean isGeneralCaptured(Team currentTeam) {
        return piecesByPosition.values().stream()
                .noneMatch(piece -> piece.isSameType(PieceType.GENERAL) && piece.isSameTeam(currentTeam));
    }

    public Map<Position, Piece> showBoard() {
        return Map.copyOf(piecesByPosition);
    }

    private PieceOnPath getPiecesOnPath(Path path) {
        PieceOnPath pieceOnPath = new PieceOnPath();
        for (Position position : path) {
            pieceOnPath.add(piecesByPosition.get(position));
        }
        return pieceOnPath;
    }

    private void validate(Piece fromPiece, Team currentTeam) {
        validateEmptyPiece(fromPiece);
        validateCurrentTeamPiece(fromPiece, currentTeam);
    }

    private Piece getPiece(Position position) {
        return piecesByPosition.get(position);
    }

    private void validateEmptyPiece(Piece fromPiece) {
        if (fromPiece.isEmptyPiece()) {
            throw new IllegalArgumentException("[ERROR] 선택된 기물이 없습니다.");
        }
    }

    private void validateCurrentTeamPiece(Piece fromPiece, Team currentTeam) {
        if (!(fromPiece.isSameTeam(currentTeam))) {
            throw new IllegalArgumentException("[ERROR] 자신의 기물만 이동시킬 수 있습니다.");
        }
    }

    private void executeMove(Movement movement, Piece fromPiece) {
        piecesByPosition.put(movement.getFrom(), new EmptyPiece());
        piecesByPosition.put(movement.getTo(), fromPiece);
    }
}
