package janggi.view;

import janggi.dto.FinalResultDto;
import janggi.dto.PieceDto;
import janggi.dto.ScoreResultDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ApplicationView {

    private final Output outputWriter;
    private final Input inputReader;

    public ApplicationView(Output outputWriter, Input inputReader) {
        this.outputWriter = outputWriter;
        this.inputReader = inputReader;
    }

    public int promptForArrangementStrategyDecision(String side, Map<Integer, String> strategies) {
        outputWriter.printPromptMessage(side + "팀의 초기화 전략 번호를 입력해주세요.");

        for (Entry<Integer, String> strategy : strategies.entrySet()) {
            String strategyDecisionOption = String.format("%d. %s", strategy.getKey(), strategy.getValue());
            outputWriter.printPromptMessage(strategyDecisionOption);
        }

        return inputReader.readInt();
    }

    public void showBoardArray(List<List<PieceDto>> pieceMatrix) {
        outputWriter.printPieceMatrix(pieceMatrix);
    }

    public void showScoreResults(ScoreResultDto scoreResultDto) {
        outputWriter.printPromptMessage(consistScoreResult(scoreResultDto));
    }

    private String consistScoreResult(ScoreResultDto scoreResultDto) {
        List<String> results = new ArrayList<>();
        for (Entry<String, Double> scoreResult : scoreResultDto.scoreResults().entrySet()) {
            String team = scoreResult.getKey();
            double score = scoreResult.getValue();
            String result = String.format("%s: %.1f점", team, score);
            results.add(result);
        }
        return String.join(" | ", results);
    }

    public void showCurrentSide(String currentSide) {
        outputWriter.printPromptMessage("\n" + currentSide + "팀의 차례입니다.");
    }

    public List<Integer> promptForLocationOfPiece() {
        outputWriter.printPromptMessage("이동시킬 기물의 좌표(행 번호, 열 번호)를 입력해주세요. (,로 구분)");

        return inputReader.readIntegers();
    }

    public List<Integer> promptForLocationToMove() {
        outputWriter.printPromptMessage("해당 기물이 이동할 좌표(행 번호, 열 번호)를 입력해주세요. (,로 구분)");

        return inputReader.readIntegers();
    }

    public void showErrorMessage(String errorMessage) {
        outputWriter.printErrorMessage(errorMessage);
    }

    public void showFinalResult(FinalResultDto finalResultDto) {
        ScoreResultDto scoreResultDto = finalResultDto.scoreResultDto();
        outputWriter.printPromptMessage("[게임 결과]\n승리 팀: " + finalResultDto.winner()
                + "\n\n[양 진영의 최종 점수] \n" + consistScoreResult(scoreResultDto));
    }
}
