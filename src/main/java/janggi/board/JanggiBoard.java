package janggi.board;

import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.setting.AssignType;
import janggi.setting.CampType;
import janggi.value.Position;
import java.util.List;

public class JanggiBoard {

    private final Pieces choPieces;
    private final Pieces hanPieces;

    public JanggiBoard(AssignType choAssignType, AssignType hanAssignType) {
        this.choPieces = new Pieces(choAssignType.makeAssign(CampType.CHO));
        this.hanPieces = new Pieces(hanAssignType.makeAssign(CampType.HAN));
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
