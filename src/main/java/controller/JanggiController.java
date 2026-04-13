package controller;


import domain.Position;
import domain.Team;
import domain.dto.BoardMapper;
import domain.dto.ResultDto;
import domain.dto.ScoreDto;
import service.JanggiService;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final JanggiService  janggiService;

    public JanggiController(JanggiService janggiService) {
        this.janggiService = janggiService;
    }

    public void start() {
        janggiService.initializeGame(decideResume());

        while (!janggiService.isFinished()) {
            try {
                playTurn();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        printFinalResult();
    }

    public boolean decideResume() {
        if (janggiService.canResume()) {
            return InputView.askResumeGame().equalsIgnoreCase("y");
        }
        return false;
    }

    private void playTurn() {
        OutputView.printJanggiBoard(BoardMapper.from(janggiService.getBoard()));
        OutputView.printCurrentScore(new ScoreDto(
                janggiService.calculateScore(Team.CHO),
                janggiService.calculateScore(Team.HAN)
        ));

        Position current = parsePosition(InputView.readMovePiecePosition(janggiService.getCurrentTurnName()));
        Position target = parsePosition(InputView.readTargetPiecePosition());

        janggiService.move(current, target);
    }

    private void printFinalResult() {
        OutputView.printJanggiBoard(BoardMapper.from(janggiService.getBoard()));
        OutputView.printWinnerTeam(new ResultDto(
                janggiService.calculateScore(Team.CHO),
                janggiService.calculateScore(Team.HAN),
                janggiService.getWinnerName()
        ));
    }

    private static Position parsePosition(String input) {
        try {
            String[] split = input.split(",");
            return new Position(Integer.parseInt(split[0].trim()), Integer.parseInt(split[1].trim()));
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 위치 입력 형식이 잘못되었습니다. (예: 0,0)");
        }
    }
}
