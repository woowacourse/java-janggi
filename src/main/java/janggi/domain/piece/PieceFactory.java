package janggi.domain.piece;

import static janggi.domain.piece.PieceType.*;

import janggi.domain.Side;
import janggi.domain.board.GungSeong;
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

public class PieceFactory {

    private final Map<PieceType, Movement> matchInfo;

    private PieceFactory(GungSeong gungSeong) {
        this.matchInfo = new EnumMap<>(PieceType.class);
        matchInfo.put(CHA, ChaMovement.getInstance());
        matchInfo.put(MA, MaMovement.getInstance());
        matchInfo.put(SANG, SangMovement.getInstance());
        matchInfo.put(SA, SaMovement.create(gungSeong));
        matchInfo.put(GUNG, GungMovement.create(gungSeong));
        matchInfo.put(PO, PoMovement.getInstance());
        matchInfo.put(JOL, JolbyeongMovement.getInstanceBySide(Side.CHO));
        matchInfo.put(BYEONG, JolbyeongMovement.getInstanceBySide(Side.HAN));
    }

    public static PieceFactory of(GungSeong gungSeong) {
        return new PieceFactory(gungSeong);
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
