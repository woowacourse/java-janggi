package janggi.domain.piece;

import static janggi.domain.piece.PieceType.*;

import janggi.domain.Side;
import janggi.domain.rule.ChaMovement;
import janggi.domain.rule.GungMovement;
import janggi.domain.rule.JolbyeongMovement;
import janggi.domain.rule.MaMovement;
import janggi.domain.rule.Movement;
import janggi.domain.rule.PoMovement;
import janggi.domain.rule.SaMovement;
import janggi.domain.rule.SangMovement;
import java.util.EnumMap;
import java.util.Map;

@SuppressWarnings("java:S6548")
public class PieceFactory {

    private static final PieceFactory INSTANCE = new PieceFactory();

    private final Map<PieceType, Movement> matchInfo;

    private PieceFactory() {
        this.matchInfo = new EnumMap<>(PieceType.class);
        matchInfo.put(CHA, ChaMovement.getInstance());
        matchInfo.put(MA, MaMovement.getInstance());
        matchInfo.put(SANG, SangMovement.getInstance());
        matchInfo.put(SA, SaMovement.getInstance());
        matchInfo.put(GUNG, GungMovement.getInstance());
        matchInfo.put(PO, PoMovement.getInstance());
        matchInfo.put(JOL, JolbyeongMovement.getInstanceBySide(Side.CHO));
        matchInfo.put(BYEONG, JolbyeongMovement.getInstanceBySide(Side.HAN));
    }

    public static PieceFactory getInstance() {
        return INSTANCE;
    }

    public Piece createActivePiece(PieceType type, Side side) {
        Movement movement = mapMovement(type);
        return new ActivePiece(type, side, movement);
    }

    private Movement mapMovement(PieceType type) {
        if (matchInfo.containsKey(type)) {
            return matchInfo.get(type);
        }
        throw new IllegalArgumentException("이동 규칙이 정의되지 않은 기물입니다 : " + type);
    }
}
