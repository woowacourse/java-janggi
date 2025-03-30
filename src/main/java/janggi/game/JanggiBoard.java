package janggi.game;

import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.setting.CampType;
import janggi.setting.PieceAssignType;
import janggi.setting.PieceAssigner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class JanggiBoard {

    private final List<Pieces> piecesInCamp;

    public JanggiBoard(PieceAssignType choPieceAssignType, PieceAssignType hanPieceAssignType) {
        PieceAssigner assigner = new PieceAssigner();
        piecesInCamp = new ArrayList<>();
        List<Piece> chaInitialPieces = assigner.assignPieces(CampType.CHO, choPieceAssignType);
        piecesInCamp.add(new Pieces(CampType.CHO, chaInitialPieces));
        List<Piece> hanInitialPieces = assigner.assignPieces(CampType.HAN, hanPieceAssignType);
        piecesInCamp.add(new Pieces(CampType.HAN, hanInitialPieces));
    }

    public void movePiece(MovePieceCommand command) {
        Pieces alliesPieces = findPieces(command.getCampType());
        Pieces enemyPieces = findPieces(command.getCampType().getEnemyCampType());
        alliesPieces.movePiece(enemyPieces.getPieces(), command.getTargetPiecePosition(), command.getDestination());
        alliesPieces.killEnemyPiece(enemyPieces.getPieces(), command.getDestination());
        enemyPieces.removeDyingPiece(command.getDestination());
    }

    public boolean isGameEnd() {
        return piecesInCamp.stream().anyMatch(pieces -> !pieces.existGung());
    }

    public CampType whoWin() {
        Optional<CampType> winningCampByGung = checkWiningByGung();
        return winningCampByGung.orElseGet(this::checkWiningByScore);
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
        Pieces alliesPieces = findPieces(campType);
        return alliesPieces.getScore();
    }

    private Pieces findPieces(CampType campType) {
        return piecesInCamp.stream()
                .filter(pieces -> pieces.checkCamp(campType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("진영에 해당하는 장기물 세트가 존재하지 않습니다."));
    }

    private Optional<CampType> checkWiningByGung() {
        Optional<Pieces> optionalPieces = piecesInCamp.stream()
                .filter(pieces -> !pieces.existGung())
                .findFirst();
        return optionalPieces.map(pieces -> pieces.getCampType().getEnemyCampType());
    }

    private CampType checkWiningByScore() {
        Pieces piecesInWinningCamp = piecesInCamp.stream()
                .sorted(Comparator.comparing(Pieces::getScore).reversed())
                .toList()
                .getFirst();
        return piecesInWinningCamp.getCampType();
    }
}
