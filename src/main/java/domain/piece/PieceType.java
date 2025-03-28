package domain.piece;

import domain.janggi.Score;
import domain.janggi.Team;
import java.util.Arrays;
import java.util.function.Function;

public enum PieceType {
    CANNON("포", new Score(7), Cannon::new),
    CHARIOT("차", new Score(13), Chariot::new),
    ELEPHANT("상", new Score(3), Elephant::new),
    GENERAL("왕", new Score(0), General::new),
    GUARD("사", new Score(3), Guard::new),
    HORSE("마", new Score(5), Horse::new),
    ZZU("쭈", new Score(2), Zzu::new);

    private final String title;
    private final Score score;
    private final Function<Team, Piece> generatePiece;

    PieceType(
            final String title,
            final Score score,
            final Function<Team, Piece> generatePiece
    ) {
        this.title = title;
        this.score = score;
        this.generatePiece = generatePiece;
    }

    public static PieceType from(final String name) {
        return Arrays.stream(values())
                .filter(pieceType -> pieceType.name().equals(name.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 PieceType이 없습니다."));
    }

    public Piece generate(final Team team) {
        return generatePiece.apply(team);
    }

    public String getTitle() {
        return title;
    }

    public Score getScore() {
        return score;
    }
}
