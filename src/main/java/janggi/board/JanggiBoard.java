package janggi.board;

import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.setting.AssignType;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;

public class JanggiBoard {

    private final Pieces choPieces;
    private final Pieces hanPieces;

    public JanggiBoard(AssignType choAssignType, AssignType hanAssignType) {
        this.choPieces = new Pieces(choAssignType.makeAssign(CampType.CHO));
        this.hanPieces = new Pieces(hanAssignType.makeAssign(CampType.HAN));
    }

    public void startChoTurn(final JanggiPosition targetJanggiPosition, final JanggiPosition destination) {
        choPieces.movePiece(hanPieces, targetJanggiPosition, destination);
    }

    public void startHanTurn(final JanggiPosition targetJanggiPosition, final JanggiPosition destination) {
        hanPieces.movePiece(choPieces, targetJanggiPosition, destination);
    }

    public List<Piece> getChoPieces() {
        return choPieces.getPieces();
    }

    public List<Piece> getHanPieces() {
        return hanPieces.getPieces();
    }
}
