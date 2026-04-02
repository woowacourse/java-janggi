package janggi.model.gimul.linearMove;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import java.util.List;

public class Pho extends AbstractLinearMoveGimul {
    private static final int SCORE_VALUE = 7;
    private static final int REQUIRED_PIECE_COUNT_ON_PATH = 1;

    public Pho(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath) {
        return gimulsOnPath.size() == REQUIRED_PIECE_COUNT_ON_PATH
                && !(gimulsOnPath.getFirst() instanceof Pho);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath, AbstractGimul abstractGimulAtTo) {
        return gimulsOnPath.size() == REQUIRED_PIECE_COUNT_ON_PATH
                && !(gimulsOnPath.getFirst() instanceof Pho)
                && !this.isSameTeam(abstractGimulAtTo)
                && !(abstractGimulAtTo instanceof Pho);
    }

    @Override
    public String getSymbol() {
        return "포";
    }

    @Override
    public Score getScore() {
        return new Score(SCORE_VALUE);
    }
}
