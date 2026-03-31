package domain.game;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.Formation;
import domain.player.Name;
import domain.player.Player;
import domain.player.Players;
import domain.player.Team;
import java.util.List;

public class GameInitializer {

    public Game initialize(String choName, String hanName, int choPositionInput, int hanPositionInput) {
        Players players = createPlayers(choName, hanName);
        Board board = createBoard(choPositionInput, hanPositionInput);
        return new Game(players, board);
    }

    private Players createPlayers(String choName, String hanName) {
        Player choPlayer = createPlayer(choName, CHO);
        Player hanPlayer = createPlayer(hanName, HAN);
        return new Players(List.of(choPlayer, hanPlayer));
    }

    private Board createBoard(int choPositionInput, int hanPositionInput) {
        Formation choFormation = createFormation(choPositionInput);
        Formation hanFormation = createFormation(hanPositionInput);

        return BoardFactory.createWithFormation(choFormation, hanFormation);
    }

    private Player createPlayer(String name, Team team) {
        return new Player(new Name(name), team);
    }

    private Formation createFormation(int positionInput) {
        return Formation.from(positionInput);
    }
}
