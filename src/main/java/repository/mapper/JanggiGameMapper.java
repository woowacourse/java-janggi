package repository.mapper;

import domain.board.Board;
import domain.game.GameStatus;
import domain.game.JanggiGame;
import domain.player.Player;
import domain.player.Team;
import repository.entity.JanggiGameEntity;

import java.util.List;

public class JanggiGameMapper {

    private static final String PLAYER_NOT_FOUND = "플레이어를 찾을 수 없습니다.";
    private static final String CURRENT_PLAYER_NOT_FOUND = "현재 플레이어를 찾을 수 없습니다.";

    public JanggiGame toDomain(
            final JanggiGameEntity janggiGameEntity,
            final Board board,
            final List<Player> players
    ) {
        final Player choPlayer = findByTeam(players, Team.CHO);
        final Player hanPlayer = findByTeam(players, Team.HAN);
        final Player currentPlayer = findById(players, janggiGameEntity.getCurrentPlayerId());
        final GameStatus gameStatus = GameStatus.valueOf(janggiGameEntity.getStatus());

        return JanggiGame.loadGame(
                janggiGameEntity.getGameId(),
                board,
                choPlayer,
                hanPlayer,
                currentPlayer,
                gameStatus
        );
    }

    private Player findByTeam(final List<Player> players, final Team team) {
        return players.stream()
                .filter(player -> player.getTeam() == team)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(PLAYER_NOT_FOUND));
    }

    private Player findById(final List<Player> players, final long playerId) {
        return players.stream()
                .filter(player -> player.getPlayerId() == playerId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(CURRENT_PLAYER_NOT_FOUND));
    }
}
