package controller;

import domain.board.JanggiBoard;
import domain.dto.BoardDto;
import domain.dto.BoardMapper;
import game.JanggiGame;
import view.OutputView;

import java.util.HashMap;

public class JanggiController {

    private final OutputView outputView;

    public JanggiController(OutputView outputView) {
        this.outputView = outputView;
    }

    public void start() {
        JanggiBoard janggiBoard = new JanggiBoard(new HashMap<>());
        JanggiGame janggiGame = new JanggiGame(janggiBoard);

        BoardDto boardDto = BoardMapper.from(janggiBoard);
        outputView.printJanggiBoard(boardDto);
    }
}
