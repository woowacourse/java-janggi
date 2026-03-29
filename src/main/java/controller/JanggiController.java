package controller;

import domain.board.JanggiBoard;
import game.JanggiGame;

import java.util.HashMap;

public class JanggiController {

    public void start() {
        JanggiBoard janggiBoard = new JanggiBoard(new HashMap<>());
        JanggiGame janggiGame = new JanggiGame(janggiBoard);
    }
}
