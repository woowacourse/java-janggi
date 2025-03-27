package domain;

import domain.piece.Empty;
import domain.piece.Piece;
import domain.piece.Side;
import java.util.List;
import java.util.Map;

public class JanggiBoard {

    private final Map<JanggiPosition, Piece> janggiBoard;
    private int choScore = 0;
    private int hanScore = 0;

    public JanggiBoard(Map<JanggiPosition, Piece> janggiBoard) {
        this.janggiBoard = janggiBoard;
    }

    public Map<JanggiPosition, Piece> getJanggiBoard() {
        return janggiBoard;
    }

    public void move(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        afterPosition.validateBound();

        Piece piece = getPieceFrom(beforePosition);
        Piece targetPiece = getPieceFrom(afterPosition);

        validateDestinationPiece(piece, targetPiece);
        validateMovePattern(piece, beforePosition, afterPosition);

        changeState(piece, targetPiece);
        changePosition(beforePosition, afterPosition);
    }

    private void validateDestinationPiece(Piece piece, Piece targetPiece) {
        if (piece.isSameSide(targetPiece.getSide())) {
            throw new IllegalArgumentException("목적지에 같은 팀의 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    private void validateMovePattern(Piece piece, JanggiPosition beforePosition, JanggiPosition afterPosition) {
        List<JanggiPosition> positions = piece.getPositionsFromPatterns(beforePosition, afterPosition);
        List<Piece> hurdlePieces = getPiecesFrom(positions);
        piece.validateMove(hurdlePieces);
    }

    private List<Piece> getPiecesFrom(List<JanggiPosition> positions) {
        return positions.stream()
                .map(this::getPieceFrom)
                .filter(p -> !p.isEmpty())
                .toList();
    }

    public Piece getPieceFrom(JanggiPosition position) {
        return janggiBoard.get(position);
    }

    private void changeState(Piece piece, Piece targetPiece) {
        piece.updateState();
        piece.capture(targetPiece);
        updateScore(piece, targetPiece);
    }

    private void changePosition(JanggiPosition beforePosition, JanggiPosition afterPosition) {
        Piece piece = getPieceFrom(beforePosition);

        janggiBoard.put(beforePosition, new Empty());
        janggiBoard.put(afterPosition, piece);
    }

    public boolean isGeneralDead(Piece targetPiece) {
        return targetPiece.isGeneral();
    }

    private void updateScore(Piece piece, Piece targetPiece) {
        if (piece.getSide().equals(Side.CHO)) {
            choScore = choScore + targetPiece.getScore();
        }
        if (piece.getSide().equals(Side.HAN)) {
            hanScore = hanScore + targetPiece.getScore();
        }
    }

    public int getChoScore() {
        return choScore;
    }

    public int getHanScore() {
        return hanScore;
    }
}
