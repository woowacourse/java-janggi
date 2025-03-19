package domain;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Piece> alivePieces;
    private final List<Piece> deadPieces;

    public Board(List<Piece> pieces) {
        this.alivePieces = new ArrayList<>(pieces);
        this.deadPieces = new ArrayList<>();
    }

    public void movePiece(Position startPosition, Position endPosition, TeamType team) {
        Piece findPiece = alivePieces.stream()
                .filter(piece -> piece.hasSamePosition(startPosition))
                .findAny().orElseThrow(() -> new IllegalArgumentException("해당 좌표에는 말이 존재하지 않습니다."));

        if (!findPiece.isSameTeam(team)) {
            throw new IllegalArgumentException("본인 말만 움직일 수 있습니다.");
        }

        boolean canMove = findPiece.canMove(endPosition, alivePieces);
        if (!canMove) {
            throw new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다.");
        }
        Piece endPositionPiece = alivePieces.stream()
                .filter(piece -> piece.hasSamePosition(endPosition))
                .findFirst().orElse(null);
        if (endPositionPiece != null) {
            alivePieces.remove(endPositionPiece);
            deadPieces.add(endPositionPiece);
        }
        findPiece.moveTo(endPosition);
    }

    public List<Piece> getAlivePieces() {
        return alivePieces.stream().map(Piece::newInstance).toList();
    }

    public boolean isFinished() {
        return deadPieces.stream()
                .anyMatch(piece -> piece.getType().equals(PieceType.KING));
    }

    public TeamType findWinTeam() {
        Piece deadKing = deadPieces.stream()
                .filter(piece -> piece.getType().equals(PieceType.KING))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("아직 게임이 끝나지 않았습니다."));

        if (deadKing.isSameTeam(TeamType.CHO)) {
            return TeamType.HAN;
        }
        return TeamType.CHO;
    }
}
