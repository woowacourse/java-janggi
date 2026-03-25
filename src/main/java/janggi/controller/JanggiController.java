package janggi.controller;

import janggi.domain.Board;
import janggi.domain.JanggiGame;
import janggi.domain.dto.MoveCommand;
import janggi.domain.piece.Team;
import janggi.domain.vo.Position;
import janggi.view.InputView;
import janggi.view.OutputView;

public class JanggiController {

    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    public void run() {
        Board board = new Board();

        JanggiGame janggiGame = new JanggiGame();

        //장기 게임 시작 출력 안내

        while (!janggiGame.isFinished()) {
            Team currentTeam = janggiGame.findCurrentTeam();
            //입력 받기
            MoveCommand moveCommand = inputView.readMovePositions();
            Position from = moveCommand.getFrom();
            Position to = moveCommand.getTo();

            board.move(from, to, currentTeam);


        }


    }

}


