package janggi.board;

import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.setting.AssignType;
import janggi.setting.CampType;
import janggi.value.JanggiPosition;
import java.util.List;

public class JanggiBoard {
    private static final double CHO_PLUS_SCORE = 1.5;

    private final Pieces choPieces;
    private final Pieces hanPieces;

    public JanggiBoard(AssignType choAssignType, AssignType hanAssignType) {
        this.choPieces = new Pieces(choAssignType.makeAssign(CampType.CHO));
        this.hanPieces = new Pieces(hanAssignType.makeAssign(CampType.HAN));
    }

    public void startTurn(final JanggiPosition targetJanggiPosition, final JanggiPosition destination, final CampType campType) {
        if (campType == CampType.CHO) {
            choPieces.movePiece(hanPieces, targetJanggiPosition, destination);
            return;
        }
        hanPieces.movePiece(choPieces, targetJanggiPosition, destination);
    }

    public boolean isChoCampCollapse() {
        return choPieces.isPieceAlive(CampType.CHO.getGungName());
    }

    public boolean isHanCampCollapse() {
        return hanPieces.isPieceAlive(CampType.HAN.getGungName());
    }

    public double requestChoTotalScore() {
        return choPieces.calculateTotalScore() + CHO_PLUS_SCORE;
    }

    public int requestHanTotalScore() {
        return hanPieces.calculateTotalScore();
    }

    public List<Piece> getChoPieces() {
        return choPieces.getPieces();
    }

    public List<Piece> getHanPieces() {
        return hanPieces.getPieces();
    }
}
