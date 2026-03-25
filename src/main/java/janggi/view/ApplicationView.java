package janggi.view;

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

    public int requestArrangementStrategyDecision(List<ArrangementStrategy> strategies) {
        outputWriter.printPromptMessage("초기화 전략 번호를 입력해주세요.");

        for (ArrangementStrategy strategy : strategies) {
            String strategyDecisionOption = String.format("%d. %s", strategy.decisionNumber(), strategy.name());
            outputWriter.printPromptMessage(strategyDecisionOption);
        }

        return retry(inputReader::readInteger);
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
