package janggi.model.piece;

import janggi.model.Team;
import janggi.model.piece.diagonalMove.Ma;
import janggi.model.piece.diagonalMove.Sang;
import janggi.model.piece.palace.Jang;
import janggi.model.piece.palace.Sa;
import janggi.model.piece.straightMove.Cha;
import janggi.model.piece.straightMove.Pho;
import java.util.function.Function;

public enum PieceType {
    MA(5, Ma::new),
    SANG(3, Sang::new),
    JANG(13, Jang::new),
    SA(3, Sa::new),
    PHO(7, Pho::new),
    CHA(13, Cha::new),
    BYEONG(2, Byeong::new);

    private final Score score;
    private final Function<Team, Piece> mapper;

    PieceType(int scoreValue, Function<Team, Piece> mapper) {
        this.score = new Score(scoreValue);
        this.mapper = mapper;
    }

    public int getScore() {
        return score.value();
    }

    public Piece createPieceWith(Team team) {
        return mapper.apply(team);
    }
}
