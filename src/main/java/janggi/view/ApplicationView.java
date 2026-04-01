package janggi.view;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.strategy.StrategyLabel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ApplicationView {

    private final Output outputWriter;
    private final Input inputReader;
    private List<List<Piece>> lastBoard;
    private Side lastSide;

    public ApplicationView(Output outputWriter, Input inputReader) {
        this.outputWriter = outputWriter;
        this.inputReader = inputReader;
    }

    public int requestArrangementStrategyDecision(Side side, List<StrategyLabel> strategies) {
        outputWriter.printPromptMessage(SideViewResolver.toDisplayName(side) + "팀의 초기화 전략 번호를 입력해주세요.");

        for (StrategyLabel strategy : strategies) {
            String strategyDecisionOption = String.format("%d. %s", strategy.getDecisionNumber(), strategy.getName());
            outputWriter.printPromptMessage(strategyDecisionOption);
        }

        return retry(inputReader::readInt);
    }

    public void respondBoardArray(List<List<Piece>> board2DArray) {
        this.lastBoard = board2DArray;
        outputWriter.clearScreen();
        List<List<String>> stringMatrix = new ArrayList<>();

        List<String> header = new ArrayList<>();
        header.add(" "); // 행 번호 자리 (2칸)
        for (int i = 1; i <= 9; i++) {
            header.add(" " + i); // 각 열마다 " i" (2칸) + Writer가 붙이는 공백(1칸) = 3칸 정렬
        }
        stringMatrix.add(header);

        for (int i = 0; i < board2DArray.size(); i++) {
            List<Piece> row = board2DArray.get(i);
            List<String> stringRow = new ArrayList<>();

            int rowLabel = (i + 1) % 10;
            stringRow.add(String.format("%2d", rowLabel)); // 행 레이블 (2칸)

            for (Piece piece : row) {
                stringRow.add(PieceViewResolver.toDisplayName(piece));
            }
            stringMatrix.add(stringRow);
        }

        outputWriter.printStringMatrix(stringMatrix);
    }

    public void respondCurrentSide(Side currentSide) {
        this.lastSide = currentSide;
        outputWriter.printPromptMessage(SideViewResolver.toDisplayName(currentSide) + "팀의 차례입니다.");
    }

    public List<Integer> requestLocationOfPiece() {
        outputWriter.printPromptMessage("이동 시킬 기물의 좌표를 입력해주세요. (row,col)");
        return retry(() -> translateInput(inputReader.readIntegers()));
    }

    public List<Integer> requestLocationToMove() {
        outputWriter.printPromptMessage("해당 기물이 이동할 좌표를 입력해주세요. (row,col)");
        return retry(() -> translateInput(inputReader.readIntegers()));
    }

    private List<Integer> translateInput(List<Integer> inputs) {
        if (inputs.size() != 2) {
            return inputs;
        }
        int rowInput = inputs.get(0);
        int colInput = inputs.get(1);

        int row = (rowInput == 0) ? 9 : rowInput - 1;
        int col = colInput - 1;
        System.out.println("row: " + row + "  col: " + col);
        return List.of(row, col);
    }

    public void respondErrorMessage(RuntimeException e) {
        if (lastBoard != null) {
            respondBoardArray(lastBoard);
        }
        if (lastSide != null) {
            respondCurrentSide(lastSide);
        }
        outputWriter.printErrorMessage(e);
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                respondErrorMessage(e);
            }
        }
    }
}
