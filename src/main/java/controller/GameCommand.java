package controller;

import domain.Game;

public interface GameCommand {

    void execute(Game game);
}
