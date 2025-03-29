package janggi.board;

import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.setting.CampType;
import janggi.setting.PieceAssignType;
import janggi.value.Position;
import java.util.List;

public class JanggiBoard {

    private final Pieces choPieces;
    private final Pieces hanPieces;

    public JanggiBoard(PieceAssignType choPieceAssignType, PieceAssignType hanPieceAssignType) {
        PieceAssigner assigner = new PieceAssigner();
        List<Piece> chaInitialPieces = assigner.assignPieces(CampType.CHO, choPieceAssignType);
        List<Piece> hanInitialPieces = assigner.assignPieces(CampType.HAN, hanPieceAssignType);
        this.choPieces = new Pieces(chaInitialPieces);
        this.hanPieces = new Pieces(hanInitialPieces);
    }

    public void movePiece(CampType campType, Position targetPiecePosition, Position destination) {
        if (campType == CampType.CHO) {
            choPieces.movePiece(hanPieces.getPieces(), targetPiecePosition, destination);
            return;
        }
        hanPieces.movePiece(choPieces.getPieces(), targetPiecePosition, destination);
    }

    public List<Piece> getPieces(CampType campType) {
        if (campType == CampType.CHO) {
            return choPieces.getPieces();
        }
        return hanPieces.getPieces();
    }
}
