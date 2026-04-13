package application;

import domain.player.Name;
import domain.player.Players;

record InitializedPlayers(
        Name choName,
        Name hanName,
        Players players
) {
}
