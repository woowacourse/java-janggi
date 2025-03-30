package janggi.dto;

import janggi.movement.target.AttackedPiece;
import janggi.piece.Piece;
import java.util.List;
import java.util.Map;

public record PieceDtos(List<RunningPieceDto> runningPieces, List<AttackedPieceDto> attackedPieces){

    public static PieceDtos from(Map<Integer, Piece> runningPieces, Map<Integer, AttackedPiece> attackedPieces) {
        List<RunningPieceDto> runnings = runningPieces.entrySet().stream()
                .map(entry -> new RunningPieceDto(entry.getKey(), entry.getValue()))
                .toList();
        List<AttackedPieceDto> attackeds = attackedPieces.entrySet().stream()
                .map(entry -> new AttackedPieceDto(entry.getKey(), entry.getValue()))
                .toList();
        return new PieceDtos(runnings, attackeds);
    }

    public List<Piece> getRunningPieces() {
        return runningPieces.stream().map(dto -> dto.piece).toList();
    }

    public List<AttackedPiece> getAttackedPieces() {
        return attackedPieces.stream().map(dto -> dto.piece).toList();
    }

    public record RunningPieceDto(int id, Piece piece) {
    }

    public record AttackedPieceDto(int id, AttackedPiece piece) {
        public Piece getPieceValue() {
            return piece.getPiece();
        }
    }
}
