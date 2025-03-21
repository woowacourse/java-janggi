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
        this.choPieces = new Pieces(choPieceAssignType.makeAssign(CampType.CHO));
        this.hanPieces = new Pieces(hanPieceAssignType.makeAssign(CampType.HAN));
    }

    public void movePiece(CampType campType, Position targetPiecePosition, Position destination) {
        if (campType == CampType.CHO) {
            choPieces.movePiece(hanPieces.getPieces(), targetPiecePosition, destination);
            return;
        }
        hanPieces.movePiece(choPieces.getPieces(), targetPiecePosition, destination);
    }

    public List<Piece> getChoPieces() {
        return choPieces.getPieces();
    }

    public List<Piece> getHanPieces() {
        return hanPieces.getPieces();
    }
}
