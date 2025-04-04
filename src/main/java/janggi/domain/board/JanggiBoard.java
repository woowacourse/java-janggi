package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.Pieces;
import janggi.domain.setting.AssignType;
import janggi.domain.setting.CampType;
import janggi.domain.value.JanggiPosition;
import java.util.List;

public class JanggiBoard {
    private static final double HAN_PLUS_SCORE = 1.5;

    private final Pieces choPieces;
    private final Pieces hanPieces;

    public JanggiBoard(final AssignType choAssignType, final AssignType hanAssignType) {
        this.choPieces = new Pieces(choAssignType.makeAssign(CampType.CHO));
        this.hanPieces = new Pieces(hanAssignType.makeAssign(CampType.HAN));
    }

    public JanggiBoard(final List<Piece> choPieces, final List<Piece> hanPieces) {
        this.choPieces = new Pieces(choPieces);
        this.hanPieces = new Pieces(hanPieces);
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

    public int requestChoTotalScore() {
        return choPieces.calculateTotalScore();
    }

    public double requestHanTotalScore() {
        return hanPieces.calculateTotalScore() + HAN_PLUS_SCORE;
    }

    public List<Piece> getChoPieces() {
        return choPieces.getPieces();
    }

    public List<Piece> getHanPieces() {
        return hanPieces.getPieces();
    }
}
