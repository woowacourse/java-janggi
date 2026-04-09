package controller;

import domain.Position;
import domain.Team;
import domain.board.JanggiBoard;
import domain.dto.BoardMapper;
import domain.dto.ResultDto;
import domain.dto.ScoreDto;
import game.JanggiGame;
import repository.BoardRepository;
import repository.GameRepository;
import view.InputView;
import view.OutputView;

import java.util.HashMap;

public class JanggiController {

    private final BoardRepository boardRepository;
    private final GameRepository gameRepository;

    public JanggiController(BoardRepository boardRepository, GameRepository gameRepository) {
        this.boardRepository = boardRepository;
        this.gameRepository = gameRepository;
    }

    public void start() {
        JanggiBoard janggiBoard = initializeBoard();
        Team currentTurn = gameRepository.findCurrentTurn();
        JanggiGame janggiGame = new JanggiGame(janggiBoard, currentTurn);

        while (!janggiGame.isFinished()) {
            try {
                playTurn(janggiBoard, janggiGame);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        OutputView.printJanggiBoard(BoardMapper.from(janggiBoard));
        OutputView.printWinnerTeam(new ResultDto(janggiBoard.calculateScore(Team.CHO),
                janggiBoard.calculateScore(Team.HAN), janggiGame.getWinner().getName()));
    }

    private JanggiBoard initializeBoard() {
        if (gameRepository.isNotFinished()) {
            String userAnswer = InputView.askResumeGame();
            if (userAnswer.equalsIgnoreCase("y")) {
                return new JanggiBoard(boardRepository.findAll());
            }
            clearAllRepository();
        }
        return new JanggiBoard(new HashMap<>());
    }

    private void clearAllRepository() {
        boardRepository.deleteAll();
        gameRepository.deleteAll();
    }

    private void playTurn(JanggiBoard janggiBoard, JanggiGame janggiGame) {
        OutputView.printJanggiBoard(BoardMapper.from(janggiBoard));
        OutputView.printCurrentScore(new ScoreDto(janggiBoard.calculateScore(Team.CHO), janggiBoard.calculateScore(Team.HAN)));

        Team currentTeam = janggiGame.getCurrentTeam();
        String currentTeamName = currentTeam.getName();

        String movePiecePosition = InputView.readMovePiecePosition(currentTeamName);
        Position currentPosition = parsePosition(movePiecePosition);

        String targetPiecePosition = InputView.readTargetPiecePosition();
        Position targetPosition = parsePosition(targetPiecePosition);

        janggiGame.progress(currentPosition, targetPosition);
        saveCurrentStatus(janggiBoard, janggiGame);
    }

    private void saveCurrentStatus(JanggiBoard janggiBoard, JanggiGame janggiGame) {
        boardRepository.save(janggiBoard.getJanggiBoard());
        gameRepository.save(
                janggiGame.getCurrentTeam(),
                janggiGame.isFinished(),
                janggiBoard.calculateScore(Team.CHO),
                janggiBoard.calculateScore(Team.HAN));
    }

    private static Position parsePosition(String input) {
        String[] split = input.split(",");
        return new Position(Integer.parseInt(split[0].trim()), Integer.parseInt(split[1].trim()));
    }
}
