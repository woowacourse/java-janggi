package janggi.domain.score;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.team.Team;

import java.util.Map;

public class Score {

    private final double hanScore;
    private final double choScore;

    public Score(Map<Position, Piece> board) {
        hanScore = 1.5 + board.values().stream()
                .filter(piece -> piece.isSameTeam(Team.HAN))
                .mapToDouble(piece -> piece.getType().getScore())
                .sum();


        choScore = 0 + board.values().stream()
                .filter(piece -> piece.isSameTeam(Team.CHO))
                .mapToDouble(piece -> piece.getType().getScore())
                .sum();
    }

    public double getHanScore() {
        return hanScore;
    }

    public double getChoScore() {
        return choScore;
    }
}
