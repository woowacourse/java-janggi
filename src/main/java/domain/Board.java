package domain;

import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {
    private final List<Piece> alivePieces;
    private final List<Piece> deadPieces;

    public Board(List<Piece> pieces) {
        this.alivePieces = new ArrayList<>(pieces);
        this.deadPieces = new ArrayList<>();
    }

    public void movePiece(Position startPosition, Position endPosition, TeamType team) {
        Piece findPiece = findPieceByPosition(startPosition)
                .orElseThrow(() -> new IllegalArgumentException("해당 좌표에는 말이 존재하지 않습니다."));

        validateOwnPiece(team, findPiece);
        validateCanMove(findPiece, endPosition);

        changePieceState(endPosition);
        findPiece.moveTo(endPosition);
    }

    public boolean isFinished() {
        return deadPieces.stream()
                .anyMatch(piece -> piece.getType().equals(PieceType.KING));
    }

    public TeamType findWinTeam() {
        Piece deadKing = findDeadKing();
        if (deadKing.isSameTeam(TeamType.CHO)) {
            return TeamType.HAN;
        }
        return TeamType.CHO;
    }

    private void changePieceState(Position endPosition) {
        findPieceByPosition(endPosition)
                .ifPresent(piece -> {
                    alivePieces.remove(piece);
                    deadPieces.add(piece);
                });
    }

    private boolean isNotSameTeam(TeamType team, Piece findPiece) {
        return !findPiece.isSameTeam(team);
    }

    private Optional<Piece> findPieceByPosition(Position startPosition) {
        return alivePieces.stream()
                .filter(piece -> piece.hasSamePosition(startPosition))
                .findAny();
    }

    private Piece findDeadKing() {
        return deadPieces.stream()
                .filter(piece -> piece.isSameType(PieceType.KING))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("아직 게임이 끝나지 않았습니다."));
    }

    private void validateCanMove(Piece piece, Position endPosition) {
        boolean canMove = piece.canMove(endPosition, alivePieces);
        if (!canMove) {
            throw new IllegalArgumentException("해당 좌표로 이동시킬 수 없습니다.");
        }
    }

    private void validateOwnPiece(TeamType team, Piece findPiece) {
        if (isNotSameTeam(team, findPiece)) {
            throw new IllegalArgumentException("본인 말만 움직일 수 있습니다.");
        }
    }

    public List<Piece> getAlivePieces() {
        return alivePieces.stream().map(Piece::newInstance).toList();
    }
}
