package janggi.model.gimul.linearMove;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.GimulType;
import java.util.List;
import java.util.Optional;

public class Pho extends AbstractLinearMoveGimul {
    private static final int SCORE_VALUE = 7;
    private static final int REQUIRED_PIECE_COUNT_ON_PATH = 1;

    public Pho(Team team) {
        super(team, GimulType.PHO);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath, Optional<AbstractGimul> gimulAtTo) {
        return gimulsOnPath.size() == REQUIRED_PIECE_COUNT_ON_PATH
                && gimulsOnPath.getFirst().canBeJumpedOver()
                && gimulAtTo.map(gimul -> !this.isSameTeam(gimul) && gimul.canBeJumpedOver()).orElse(true);
    }

    @Override
    public String getSymbol() {
        return "포";
    }

    @Override
    public Score getScore() {
        return new Score(SCORE_VALUE);
    }

    @Override
    public boolean canBeJumpedOver() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
