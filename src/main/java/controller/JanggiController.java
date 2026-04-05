package controller;

import domain.Position;
import domain.Team;
import domain.board.JanggiBoard;
import domain.dto.BoardDto;
import domain.dto.BoardMapper;
import domain.dto.ResultDto;
import domain.dto.ScoreDto;
import game.JanggiGame;
import view.InputView;
import view.OutputView;

import java.util.Arrays;
import java.util.HashMap;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        JanggiBoard janggiBoard = new JanggiBoard(new HashMap<>());
        JanggiGame janggiGame = new JanggiGame(janggiBoard);

        while(!janggiGame.isFinished()) {
            try {
                playTurn(janggiBoard, janggiGame);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        outputView.printJanggiBoard(BoardMapper.from(janggiBoard));
        outputView.printWinnerTeam(new ResultDto(janggiBoard.calculateScore(Team.CHO),
                janggiBoard.calculateScore(Team.HAN), janggiGame.getWinner().getName()));
    }

    private void playTurn(JanggiBoard janggiBoard, JanggiGame janggiGame) {
        outputView.printJanggiBoard(BoardMapper.from(janggiBoard));
        outputView.printCurrentScore(new ScoreDto(janggiBoard.calculateScore(Team.CHO), janggiBoard.calculateScore(Team.HAN)));

        Team currentTeam = janggiGame.getCurrentTeam();
        String currentTeamName = currentTeam.getName();

        String movePiecePosition = inputView.readMovePiecePosition(currentTeamName);
        Position currentPosition = parsePosition(movePiecePosition);

        String targetPiecePosition = inputView.readTargetPiecePosition();
        Position targetPosition = parsePosition(targetPiecePosition);

        janggiGame.progress(currentPosition, targetPosition);
    }

    private static Position parsePosition(String input) {
        String[] split = input.split(",");
        return new Position(Integer.parseInt(split[0].trim()), Integer.parseInt(split[1].trim()));
    }
}
