package domain.piece;

import domain.piece.policy.PolicyProvider;
import domain.piece.strategy.StrategyProvider;

public class PieceFactory {

    public static final String CAN_NOT_FIND_PIECE = "해당 기물을 찾을 수 없습니다.";

    public static Piece create(PieceType pieceType, Team team) {
        if (pieceType == PieceType.CHA) {
            return new Cha(StrategyProvider.getStrategy(pieceType), PolicyProvider.getPolicies(pieceType), team);
        }
        if (pieceType == PieceType.MA) {
            return new Ma(StrategyProvider.getStrategy(pieceType), PolicyProvider.getPolicies(pieceType), team);
        }
        if (pieceType == PieceType.SANG) {
            return new Sang(StrategyProvider.getStrategy(pieceType), PolicyProvider.getPolicies(pieceType), team);
        }
        if (pieceType == PieceType.SA) {
            return new Sa(StrategyProvider.getStrategy(pieceType), PolicyProvider.getPolicies(pieceType), team);
        }
        if (pieceType == PieceType.JANG) {
            return new Jang(StrategyProvider.getStrategy(pieceType), PolicyProvider.getPolicies(pieceType), team);
        }
        if (pieceType == PieceType.PO) {
            return new Po(StrategyProvider.getStrategy(pieceType), PolicyProvider.getPolicies(pieceType), team);
        }
        if (pieceType == PieceType.JOL) {
            return new Jol(StrategyProvider.getStrategy(pieceType), PolicyProvider.getPolicies(pieceType), team);
        }
        if (pieceType == PieceType.BYEONG) {
            return new Byeong(StrategyProvider.getStrategy(pieceType), PolicyProvider.getPolicies(pieceType), team);
        }
        throw new IllegalArgumentException(CAN_NOT_FIND_PIECE);
    }
}
