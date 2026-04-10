package janggi.domain.turn;

import janggi.domain.board.Board;
import janggi.domain.space.piece.Team;
import java.util.Map;
import java.util.function.Function;

public class GameStateFactory {
    private static final Map<Team, Function<Board, GameState>> FACTORY = Map.of(
            Team.HAN, HanTurn::new,
            Team.CHO, ChoTurn::new
    );

    public static GameState createInitialState(Team team, Board board) {
        return FACTORY.get(team).apply(board);
    }
}
