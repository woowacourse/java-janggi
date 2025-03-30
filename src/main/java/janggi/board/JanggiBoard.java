package janggi.board;

import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.setting.CampType;
import janggi.setting.PieceAssignType;
import janggi.value.Position;
import java.util.ArrayList;
import java.util.List;

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

    public void movePiece(CampType campType, Position targetPiecePosition, Position destination) {
        Pieces alliesPieces = findPieces(campType);
        Pieces enemyPieces = findPieces(campType.getEnemyCampType());
        alliesPieces.movePiece(enemyPieces.getPieces(), targetPiecePosition, destination);
        alliesPieces.killEnemyPiece(enemyPieces.getPieces(), destination);
        enemyPieces.removeDyingPiece(destination);
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
}
