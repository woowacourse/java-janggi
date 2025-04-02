package janggi.piece;

import janggi.rule.BoardPositionRange;
import janggi.value.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Pieces {

    private final List<Piece> pieces;
    private final List<Piece> dyingEnemy;

    public Pieces(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
        this.dyingEnemy = new ArrayList<>();
    }

    public void movePiece(List<Piece> enemyPieces, Position targetPiecePosition, Position destination) {
        BoardPositionRange.validateInRange(targetPiecePosition);
        BoardPositionRange.validateInRange(destination);
        Piece target = getPiece(targetPiecePosition);
        pieces.remove(target);
        Piece movedTarget = target.move(destination, enemyPieces, pieces);
        pieces.add(movedTarget);
    }

    public void removeDyingPiece(Position dyingPiecePosition) {
        Optional<Piece> searchedPiece = searchPiece(dyingPiecePosition);
        searchedPiece.ifPresent(pieces::remove);
    }

    public void killEnemyPiece(List<Piece> enemyPieces, Position destination) {
        Optional<Piece> dyingEnemy = enemyPieces.stream().filter(enemy -> enemy.getPosition().equals(destination))
                .findFirst();
        dyingEnemy.ifPresent(this.dyingEnemy::add);
    }

    public boolean checkEnemyGungKilling() {
        return dyingEnemy.stream().anyMatch(piece -> piece.checkPieceType(PieceType.GUNG));
    }

    public Optional<Piece> searchPiece(Position targetPiecePosition) {
        return pieces.stream().filter(piece -> piece.getPosition().equals(targetPiecePosition))
                .findFirst();
    }

    public Piece getPiece(Position targetPiecePosition) {
        return pieces.stream().filter(piece -> piece.getPosition().equals(targetPiecePosition))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 이동할 말이 존재하지 않습니다."));
    }

    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }

    public List<Piece> getDyingEnemy() {
        return Collections.unmodifiableList(dyingEnemy);
    }

    public double getScore() {
        return dyingEnemy.stream().mapToInt(Piece::getScore).sum();
    }
}
