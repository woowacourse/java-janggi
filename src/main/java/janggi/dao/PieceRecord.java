package janggi.dao;

import janggi.dto.PiecesOnBoardDto;
import janggi.dto.PiecesOnBoardDto.AttackedPieceDto;
import janggi.dto.PiecesOnBoardDto.RunningPieceDto;
import janggi.movement.target.AttackedPiece;
import janggi.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class PieceRecord {
    private static final List<PieceRecord> pieceDaos = new ArrayList<>();

    private final int id;
    private Piece piece;

    protected PieceRecord(int id, Piece piece) {
        this.id = id;
        this.piece = piece;
    }

    public static PieceRecord addRecord(int id, Piece piece){
        PieceRecord pieceRecord = new PieceRecord(id, piece);
        pieceDaos.add(pieceRecord);
        return pieceRecord;
    }

    public static List<PieceRecord> recreatePieceRecordsFrom(PiecesOnBoardDto piecesOnBoardDto) {
        List<PieceRecord> createdPieceRecords = new ArrayList<>();
        for (RunningPieceDto running : piecesOnBoardDto.runningPieces()) {
            createdPieceRecords.add(new PieceRecord(running.id(), running.piece()));
        }
        for (AttackedPieceDto attacked : piecesOnBoardDto.attackedPieces()) {
            createdPieceRecords.add(new PieceRecord(attacked.id(), attacked.getPieceValue()));
        }
        pieceDaos.addAll(createdPieceRecords);
        return createdPieceRecords;
    }

    public static PieceRecord findByPiece(Piece piece) {
        return pieceDaos.stream()
                .filter(record -> record.piece.equals(piece))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static PieceRecord findByAttackedPiece(AttackedPiece attackedPiece) {
        return pieceDaos.stream()
                .filter(record -> record.piece.equals(attackedPiece.getPiece()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public void updatePiece(Piece piece) {
        this.piece = piece;
    }

    public int getId() {
        return id;
    }

    public Piece getPiece() {
        return piece;
    }
}
