package janggi.domain.piece;

import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public Piece findPieceByPosition(Position position) {
        return pieces.stream()
            .filter(piece -> piece.isSamePosition(position))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("해당 위치엔 기물이 존재하지 않습니다."));
    }

    public List<Piece> getAllPiecesExcept(Piece sourcePiece) {
        return pieces.stream()
            .filter(piece -> !piece.equals(sourcePiece))
            .toList();
    }

    public void killEnemyPieceIfPresent(Position destination, Side turn) {
        Optional<Piece> enemyPiece = pieces.stream()
            .filter(piece -> piece.isSamePosition(destination))
            .filter(piece -> piece.isEnemy(turn))
            .findFirst();

        enemyPiece.ifPresent(pieces::remove);
    }

    public boolean isEnd() {
        return pieces.stream()
            .filter(Piece::isKing)
            .count() != 2;
    }

    public Side getWinner() {
        return pieces.stream()
            .filter(Piece::isKing)
            .map(Piece::getSide)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("게임이 종료되지 않았습니다."));
    }

    public Map<Side,Double> calculateGameScore() {
        double choScore = calculateSideScore(Side.CHO);
        double hanScore = calculateSideScore(Side.HAN);

        return Map.of(Side.CHO, choScore, Side.HAN, hanScore);
    }

    private double calculateSideScore(Side side) {
        double sideScore = pieces.stream()
            .filter(piece -> piece.getSide() == side)
            .mapToDouble(Piece::getScore)
            .sum();

        if (side.isSecondTurn()) {
            return side.plusSecondTurnBonus(sideScore);
        }
        return sideScore;
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
