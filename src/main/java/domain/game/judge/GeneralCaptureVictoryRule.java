package domain.game.judge;

import domain.board.Board;
import domain.game.Team;
import java.util.Optional;
import java.util.stream.Stream;

public final class GeneralCaptureVictoryRule implements CaptureVictoryRule {

    @Override
    public Optional<Team> findWinner(Board board) {
        return Stream.of(Team.CHO, Team.HAN)
                .filter(team -> !board.hasEssentialPieceOf(team))
                .findFirst()
                .map(Team::opposite);
    }
}
