package janggi.entity;

import janggi.dto.PiecesOnBoardDto;
import janggi.dto.PiecesOnBoardDto.AttackedPieceDto;
import janggi.dto.PiecesOnBoardDto.RunningPieceDto;
import janggi.movement.target.AttackedPiece;
import janggi.piece.Piece;
import java.util.ArrayList;
import java.util.List;

public class PieceEntity {
    private static final List<PieceEntity> pieceDaos = new ArrayList<>();

    private final int id;
    private Piece piece;

    protected PieceEntity(int id, Piece piece) {
        this.id = id;
        this.piece = piece;
    }

    public static PieceEntity addRecord(int id, Piece piece) {
        PieceEntity pieceEntity = new PieceEntity(id, piece);
        pieceDaos.add(pieceEntity);
        return pieceEntity;
    }

    public static List<PieceEntity> recreatePieceRecordsFrom(PiecesOnBoardDto piecesOnBoardDto) {
        List<PieceEntity> createdPieceEntities = new ArrayList<>();
        for (RunningPieceDto running : piecesOnBoardDto.runningPieces()) {
            createdPieceEntities.add(new PieceEntity(running.id(), running.piece()));
        }
        for (AttackedPieceDto attacked : piecesOnBoardDto.attackedPieces()) {
            createdPieceEntities.add(new PieceEntity(attacked.id(), attacked.getPieceValue()));
        }
        pieceDaos.addAll(createdPieceEntities);
        return createdPieceEntities;
    }

    public static PieceEntity findByPiece(Piece piece) {
        return pieceDaos.stream()
                .filter(record -> record.piece.equals(piece))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static PieceEntity findByAttackedPiece(AttackedPiece attackedPiece) {
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
