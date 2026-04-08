package janggi.view;

import janggi.view.input.Input;
import janggi.view.output.Output;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ApplicationView {

    private final Output outputWriter;
    private final Input inputReader;

    private List<List<String>> lastBoard;
    private String lastSide;

    public ApplicationView(Output outputWriter, Input inputReader) {
        this.outputWriter = outputWriter;
        this.inputReader = inputReader;
    }

    public int requestArrangementStrategyDecision(String side, List<String> strategyOptions) {
        outputWriter.printPromptMessage(side + "팀의 초기화 전략 번호를 입력해주세요.");

        for (String option : strategyOptions) {
            outputWriter.printPromptMessage(option);
        }

        return retry(inputReader::readInt);
    }

    public void respondBoardArray(List<List<String>> board2DArray) {
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
            List<String> row = board2DArray.get(i);
            List<String> stringRow = new ArrayList<>();

            int rowLabel = (i + 1) % 10;
            stringRow.add(String.format("%2d", rowLabel)); // 행 레이블 (2칸)

            stringRow.addAll(row);
            stringMatrix.add(stringRow);
        }

        outputWriter.printStringMatrix(stringMatrix);
    }

    public void respondCurrentSide(String currentSide) {
        this.lastSide = currentSide;
        outputWriter.printPromptMessage(currentSide + "팀의 차례입니다.");
    }

    public List<Integer> requestLocationOfPiece() {
        outputWriter.printPromptMessage("이동 시킬 기물의 좌표를 입력해주세요. (row,col)");
        return retry(() -> translateInput(inputReader.readIntegers()));
    }

    public List<Integer> requestLocationToMove() {
        outputWriter.printPromptMessage("해당 기물이 이동할 좌표를 입력해주세요. (row,col)");
        return retry(() -> translateInput(inputReader.readIntegers()));
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

    private List<Integer> translateInput(List<Integer> inputs) {
        if (inputs.size() != 2) {
            return inputs;
        }
        int rowInput = inputs.get(0);
        int colInput = inputs.get(1);

        int row = (rowInput == 0) ? 9 : rowInput - 1;
        int col = colInput - 1;
        return List.of(row, col);
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

    public Decision requestGameContinueDecision() {
        outputWriter.printPromptMessage("진행중이던 게임이 존재합니다. 이어서 하시겠습니까? (y,n)");
        String input = inputReader.readString();

        return Decision.from(input);
    }
}
