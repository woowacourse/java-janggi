package domain;

import domain.piece.Cannon;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Rook;
import java.util.Arrays;
import java.util.function.Function;

public enum PieceType {
    KING("궁", 0, King::new),
    ROOK("차", 13, Rook::new),
    CANNON("포", 7, Cannon::new),
    HORSE("마", 5, Horse::new),
    ELEPHANT("상", 3, Elephant::new),
    GUARD("사", 3, Guard::new),
    PAWN("졸", 2, Pawn::new);

    private final String koreanName;
    private final int score;
    private final Function<Team, Piece> factory;

    PieceType(String koreanName, int score, Function<Team, Piece> factory) {
        this.koreanName = koreanName;
        this.score = score;
        this.factory = factory;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public int getScore(){
        return score;
    }

    public Piece createPiece(Team team) {
        return factory.apply(team);
    }

    public static PieceType getPieceType(String koreanName) {
        return Arrays.stream(PieceType.values())
                .filter(type -> type.koreanName.equals(koreanName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기물 타입입니다: " + koreanName));
    }
}
