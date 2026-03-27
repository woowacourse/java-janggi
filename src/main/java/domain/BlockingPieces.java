package domain;

import java.util.List;

public class BlockingPieces {

    private int MAX_PIECES_ON_ROUTE = 2;
    private final List<Piece> pieces;

    public BlockingPieces(List<Piece> pieces) {
        if (pieces.size() > MAX_PIECES_ON_ROUTE) {
            throw new IllegalArgumentException("경로 상의 기물은 최대 "+ MAX_PIECES_ON_ROUTE+ "개만 존재할 수 있습니다.");
        }
        this.pieces = List.copyOf(pieces);
    }

    public boolean isFirstPieceCannon() {
        if (pieces.isEmpty()) {
            return false;
        }
        return pieces.getFirst().getPieceType() == PieceType.CANNON; // Java 21+ 문법
    }


    public boolean isLastPieceCannon() {
        if (pieces.size() < MAX_PIECES_ON_ROUTE) {
            return false;
        }
        return pieces.getLast().getPieceType() == PieceType.CANNON;
    }

    public boolean isLastPieceSameTeam(TeamColor myTeam) {
        if (pieces.size() < MAX_PIECES_ON_ROUTE) {
            return false;
        }
        return pieces.getLast().getTeamColor() == myTeam;
    }

    public int size() {
        return pieces.size();
    }
    
    public TeamColor getFirstBlockingTeamColor() {
        return pieces.getFirst().getTeamColor();
    }

}