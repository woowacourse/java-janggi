package view;

import domain.board.ChoWings;
import domain.board.HanWings;
import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import view.label.WingLabel;

public class InputView {

    private static final String WING_DELIMITER = " ";
    private static final String PIECE_DELIMITER = "";
    private static final String INTERSECTION_DELIMITER = " ";

    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public boolean askStartNewGame() {
        String answer = readLine();
        boolean startNewGame = answer.equals("y");

        return startNewGame;
    }

    public int readGameId() {
        return Integer.parseInt(readLine());
    }

    public ChoWings readChoWings() {
        String[] wingInputs = splitWingInput(readLine());
        System.out.println();

        String[] leftWingInputs = wingInputs[0].split(PIECE_DELIMITER);
        String[] rightWingInputs = wingInputs[1].split(PIECE_DELIMITER);

        return new ChoWings(
                parseWingInput(leftWingInputs, Side.CHO),
                parseWingInput(rightWingInputs, Side.CHO)
        );
    }

    public HanWings readHanWings() {
        String[] wingInputs = splitWingInput(readLine());
        System.out.println();

        String[] leftWingInputs = wingInputs[0].split(PIECE_DELIMITER);
        String[] rightWingInputs = wingInputs[1].split(PIECE_DELIMITER);

        return new HanWings(
                parseWingInput(leftWingInputs, Side.HAN),
                parseWingInput(rightWingInputs, Side.HAN)
        );
    }

    public Intersection readIntersection() {
        String[] inputs = splitIntersectionInput(readLine());
        System.out.println();

        int file = Integer.parseInt(inputs[0]);
        int row = parseRow(inputs[1]);

        return new Intersection(row, file);
    }

    private String[] splitWingInput(String rawInput) {
        String[] wingInputs = rawInput.split(WING_DELIMITER);

        if (wingInputs.length != 2) {
            throw new IllegalArgumentException("상차림을 올바르게 입력해주세요.");
        }

        return wingInputs;
    }

    private String[] splitIntersectionInput(String rawInput) {
        String[] intersectionInputs = rawInput.split(INTERSECTION_DELIMITER);

        if (intersectionInputs.length != 2) {
            throw new IllegalArgumentException("좌표를 올바르게 입력해주세요.");
        }

        return intersectionInputs;
    }

    private List<Piece> parseWingInput(String[] wingInputs, Side side) {
        List<Piece> wing = new ArrayList<>();

        for (String wingLabel : wingInputs) {
            Piece wingPiece = WingLabel.from(wingLabel, side);
            wing.add(wingPiece);
        }

        return wing;
    }

    private int parseRow(String rawRow) {
        int row = Integer.parseInt(rawRow);

        if (row == 0) {
            return 10;
        }

        return row;
    }

    private String readLine() {
        try {
            return in.readLine();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
