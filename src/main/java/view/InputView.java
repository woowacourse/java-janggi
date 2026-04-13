package view;

import java.util.Scanner;

public class InputView {

    private static final int BASE_INDEX = 1;

    private static final Scanner SCANNER = new Scanner(System.in);

    public int readNewGameOrPreviousGame() {
        return Integer.parseInt(SCANNER.nextLine());
    }

    public Long readGameId() {
        return Long.parseLong(SCANNER.nextLine());
    }

    public int readElephantSetupIndex() {
        return readZeroBasedIndex();
    }

    public int readPieceIndex() {
        return readZeroBasedIndex();
    }

    public int readPositionIndex() {
        return readZeroBasedIndex();
    }

    private int readZeroBasedIndex() {
        int oneBasedIndex = Integer.parseInt(SCANNER.nextLine().trim());
        return toZeroBasedIndex(oneBasedIndex);
    }

    private int toZeroBasedIndex(final int oneBasedIndex) {
        return oneBasedIndex - BASE_INDEX;
    }
}
