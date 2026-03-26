package janggi.view;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.strategy.ArrangementStrategy;
import java.util.List;
import java.util.function.Supplier;

public class ApplicationView {

    private final Output outputWriter;
    private final Input inputReader;

    public ApplicationView(Output outputWriter, Input inputReader) {
        this.outputWriter = outputWriter;
        this.inputReader = inputReader;
    }

    public int
    requestArrangementStrategyDecision(Side side, List<ArrangementStrategy> strategies) {
        outputWriter.printPromptMessage(side.getName() + "팀의 초기화 전략 번호를 입력해주세요.");

        for (ArrangementStrategy strategy : strategies) {
            String strategyDecisionOption = String.format("%d. %s", strategy.decisionNumber(), strategy.name());
            outputWriter.printPromptMessage(strategyDecisionOption);
        }

        return retry(inputReader::readInteger);
    }

    public void responseBoardArray(List<List<Piece>> board2DArray) {
        List<List<String>> stringMatrix = board2DArray.stream()
                .map(row -> row.stream()
                        .map(Piece::getName)
                        .toList()
                ).toList();

        outputWriter.printStringMatrix(stringMatrix);
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputWriter.printErrorMessage(e);
            }
        }
    }
}
