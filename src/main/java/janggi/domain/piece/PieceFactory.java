package janggi.domain.piece;

import static janggi.domain.piece.PieceType.BYEONG;
import static janggi.domain.piece.PieceType.CHA;
import static janggi.domain.piece.PieceType.GUNG;
import static janggi.domain.piece.PieceType.JOL;
import static janggi.domain.piece.PieceType.MA;
import static janggi.domain.piece.PieceType.PO;
import static janggi.domain.piece.PieceType.SA;
import static janggi.domain.piece.PieceType.SANG;

import janggi.domain.Side;
import janggi.domain.board.GungSeong;
import janggi.domain.rule.ByeongMovement;
import janggi.domain.rule.ChaMovement;
import janggi.domain.rule.GungSaMovement;
import janggi.domain.rule.JolMovement;
import janggi.domain.rule.MaMovement;
import janggi.domain.rule.Movement;
import janggi.domain.rule.PoMovement;
import janggi.domain.rule.SangMovement;
import java.util.EnumMap;
import java.util.Map;

public class PieceFactory {

    private final Map<PieceType, Movement> matchInfo;

    private PieceFactory(GungSeong gungSeong) {
        this.matchInfo = new EnumMap<>(PieceType.class);
        matchInfo.put(CHA, ChaMovement.create(gungSeong));
        matchInfo.put(MA, MaMovement.getInstance());
        matchInfo.put(SANG, SangMovement.getInstance());
        matchInfo.put(SA, GungSaMovement.create(gungSeong));
        matchInfo.put(GUNG, GungSaMovement.create(gungSeong));
        matchInfo.put(PO, PoMovement.create(gungSeong));
        matchInfo.put(JOL, JolMovement.create(gungSeong));
        matchInfo.put(BYEONG, ByeongMovement.create(gungSeong));
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
