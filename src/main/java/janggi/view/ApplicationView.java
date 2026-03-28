package janggi.view;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
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

    public int requestArrangementStrategyDecision(Side side, List<ArrangementStrategy> strategies) {
        outputWriter.printPromptMessage(side.getName() + "팀의 초기화 전략 번호를 입력해주세요.");

        for (ArrangementStrategy strategy : strategies) {
            String strategyDecisionOption = String.format("%d. %s", strategy.decisionNumber(), strategy.name());
            outputWriter.printPromptMessage(strategyDecisionOption);
        }

        return retry(inputReader::readInt);
    }

    public void responseBoardArray(List<List<Piece>> board2DArray) {
        List<List<String>> stringMatrix = board2DArray.stream()
                .map(row -> row.stream()
                        .map(Piece::getPieceType)
                        .map(PieceType::getNameFormat)
                        .toList()
                ).toList();

        outputWriter.printStringMatrix(stringMatrix);
    }

    public void responseCurrentSide(Side currentSide) {
        outputWriter.printPromptMessage(currentSide.getName() + "팀의 차례입니다.");
    }

    public List<Integer> requestLocationOfPiece() {
        outputWriter.printPromptMessage("이동시킬 기물의 좌표를 입력해주세요. (,로 구분)");

        return retry(inputReader::readIntegers);
    }

    public List<Integer> requestLocationToMove() {
        outputWriter.printPromptMessage("해당 기물이 이동할 좌표를 입력해주세요. (,로 구분)");

        return retry(inputReader::readIntegers);
    }

    public void responseErrorMessage(RuntimeException e) {
        outputWriter.printErrorMessage(e);
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
