package janggi.dto;

import janggi.movement.target.AttackedPiece;
import janggi.piece.Piece;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record PiecesOnBoardDto(List<RunningPieceDto> runningPieces, List<AttackedPieceDto> attackedPieces) {

    public static PiecesOnBoardDto from(Map<Integer, Piece> runningPieces, Map<Integer, AttackedPiece> attackedPieces) {
        List<RunningPieceDto> runnings = runningPieces.entrySet().stream()
                .map(entry -> new RunningPieceDto(entry.getKey(), entry.getValue()))
                .toList();
        List<AttackedPieceDto> attackeds = attackedPieces.entrySet().stream()
                .map(entry -> new AttackedPieceDto(entry.getKey(), entry.getValue()))
                .toList();
        return new PiecesOnBoardDto(runnings, attackeds);
    }

    public List<Piece> getRunningPieces() {
        return runningPieces.stream().map(dto -> dto.piece).collect(Collectors.toList()); // 불변 안됨
    }

    public List<AttackedPiece> getAttackedPieces() {
        return attackedPieces.stream().map(dto -> dto.piece).collect(Collectors.toList());
    }

    public record RunningPieceDto(int id, Piece piece) {
    }

    public record AttackedPieceDto(int id, AttackedPiece piece) {
        public Piece getPieceValue() {
            return piece.getPiece();
        }
    }
}
