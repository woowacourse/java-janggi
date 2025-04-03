package janggi.game;

import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.rule.CampType;
import janggi.rule.PieceAssignType;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiBoard {

    private final Map<CampType, Pieces> piecesInCamp;

    public JanggiBoard(PieceAssignType choPieceAssignType, PieceAssignType hanPieceAssignType) {
        PieceAssigner assigner = new PieceAssigner();
        List<Piece> chaInitialPieces = assigner.assignPieces(CampType.CHO, choPieceAssignType);
        List<Piece> hanInitialPieces = assigner.assignPieces(CampType.HAN, hanPieceAssignType);
        piecesInCamp = new EnumMap<>(CampType.class);
        piecesInCamp.put(CampType.CHO, new Pieces(chaInitialPieces));
        piecesInCamp.put(CampType.HAN, new Pieces(hanInitialPieces));
    }

    public void movePiece(MovePieceCommand command) {
        Pieces alliesPieces = findPieces(command.campType());
        Pieces enemyPieces = findPieces(command.campType().getEnemyCampType());
        alliesPieces.movePiece(enemyPieces.getPieces(), command.targetPiecePosition(), command.destination());
        alliesPieces.killEnemyPiece(enemyPieces.getPieces(), command.destination());
        enemyPieces.removeDyingPiece(command.destination());
    }

    public boolean canContinueGame() {
        Optional<CampType> winnerByKillingGung = checkWiningByKillingGung();
        return winnerByKillingGung.isEmpty();
    }

    public CampType whoWin() {
        Optional<CampType> winnerByKillingGung = checkWiningByKillingGung();
        return winnerByKillingGung.orElseGet(this::checkWiningByScore);
    }

    public List<Piece> getPieces(CampType campType) {
        Pieces alliesPieces = findPieces(campType);
        return alliesPieces.getPieces();
    }

    public List<Piece> getKilledPieces(CampType campType) {
        Pieces alliesPieces = findPieces(campType);
        return alliesPieces.getDyingEnemy();
    }

    public double getScore(CampType campType) {
        double score = findPieces(campType).getScore();
        return campType.addCampDefaultScore(score);
    }

    private Pieces findPieces(CampType campType) {
        if (!piecesInCamp.containsKey(campType)) {
            throw new IllegalArgumentException("진영에 해당하는 장기물 세트가 존재하지 않습니다.");
        }
        return piecesInCamp.get(campType);
    }

    private Optional<CampType> checkWiningByKillingGung() {
        boolean choKillingGung = findPieces(CampType.CHO).checkEnemyGungKilling();
        boolean hanKillingGung = findPieces(CampType.HAN).checkEnemyGungKilling();
        if (!choKillingGung && !hanKillingGung) {
            return Optional.empty();
        }
        if (choKillingGung) {
            return Optional.of(CampType.CHO);
        }
        return Optional.of(CampType.HAN);
    }

    private CampType checkWiningByScore() {
        double choScore = getScore(CampType.CHO);
        double hanScore = getScore(CampType.HAN);
        if (choScore > hanScore) {
            return CampType.CHO;
        }
        return CampType.HAN;
    }
}
