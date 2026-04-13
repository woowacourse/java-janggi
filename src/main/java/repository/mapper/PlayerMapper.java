package repository.mapper;

import domain.player.Player;
import domain.player.Team;
import repository.entity.PlayerEntity;

import java.util.List;

public class PlayerMapper {

    public List<PlayerEntity> toEntities(final long gameId, final List<Player> players) {
        return players.stream()
                .map(player -> toEntity(gameId, player))
                .toList();
    }

    public List<Player> toPlayers(final List<PlayerEntity> playerEntities) {
        return playerEntities.stream()
                .map(this::toPlayer)
                .toList();
    }

    private PlayerEntity toEntity(final long gameId, final Player player) {
        return new PlayerEntity(
                player.getPlayerId(),
                gameId,
                player.getName().name(),
                player.getTeam().name(),
                player.getScore()
        );
    }

    private Player toPlayer(final PlayerEntity playerEntity) {
        return Player.loadPlayer(
                playerEntity.getPlayerId(),
                playerEntity.getName(),
                Team.valueOf(playerEntity.getTeam()),
                playerEntity.getScore()
        );
    }
}
