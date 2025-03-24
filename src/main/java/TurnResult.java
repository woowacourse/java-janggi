import java.util.Optional;
import piece.Team;

public record TurnResult(int nextTurn, Optional<Team> loseTeam) {
}
