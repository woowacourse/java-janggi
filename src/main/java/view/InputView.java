package view;

import domain.board.formation.InitialFormationType;
import domain.coordinate.Position;
import domain.board.Side;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import dto.PossibleMovesDto;
import view.message.InitialFormationFormatter;
import view.message.SideView;

public class InputView {

    private static final int INDEX_OFFSET = 1;
    private static final int INIT_INDEX_COUNT = 1;
    private static final int MAX_INDEX = 4;
    private static final String COMMA_DELIMITER = ",";

    private static final String REQUEST_INIT_FORMAT_STRATEGY = "\n%s는 초기 (상·마) 포진을 선택해주세요.\n";
    private static final String REQUEST_MOVING_START_PIECE_POSITION = "\n%s 턴! 이동할 기물의 좌표를 입력해주세요. (e.g. 2,3)\n";
    private static final String REQUEST_PIECE_DESTINATION = "움직일 좌표의 번호를 선택해주세요.";

    private final Scanner sc;

    public InputView(Scanner sc) {
        this.sc = sc;
    }

    public InitialFormationType requestInitialType(Side side) {
        try {
            int index = INIT_INDEX_COUNT;
            System.out.printf(REQUEST_INIT_FORMAT_STRATEGY, SideView.from(side));
            for (int i = 0; i < MAX_INDEX; i++) {
                System.out.printf("%d. %s\n", index, InitialFormationFormatter.format(index));
                index++;
            }

            System.out.println("번호를 입력해주세요.");
            int userInput = Integer.parseInt(userInput());
            validateIndex(userInput, MAX_INDEX);
            return InitialFormationFormatter.from(userInput);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("\n잘못된 입력입니다. 다시 입력 해주세요.");
        }
    }

    public Position requestStartPiecePosition(Side side) {
        try {
            System.out.printf(REQUEST_MOVING_START_PIECE_POSITION, SideView.from(side));
            List<String> parts = splitCoordinate(userInput());
            int col = Integer.parseInt(parts.get(0).trim());
            int row = Integer.parseInt(parts.get(1).trim());

            return new Position(col, row);
        } catch (PatternSyntaxException | NumberFormatException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("\n잘못된 입력입니다. 다시 입력 해주세요.");
        }
    }

    public int requestPieceDestination(PossibleMovesDto possibleMovesDto) {
        try {
            int index = INIT_INDEX_COUNT;

            System.out.println();
            for (Position possibleMove : possibleMovesDto.possibleMoves()) {
                System.out.printf("%d. (%d, %d)\n", index++, possibleMove.col(), possibleMove.row());
            }

            System.out.println(REQUEST_PIECE_DESTINATION);
            int userInput = Integer.parseInt(userInput());
            validateIndex(userInput, possibleMovesDto.possibleMoveCount());
            return userInput - INDEX_OFFSET;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("\n잘못된 입력입니다. 다시 입력 해주세요.");
        }
    }

    private void validateIndex(int userInput, int maxIndex) {
        if (userInput < INIT_INDEX_COUNT || userInput > maxIndex) {
            throw new IllegalArgumentException(String.format("\n%d부터 %d 사이의 숫자만 입력 가능합니다. 다시 입력 해주세요.", INIT_INDEX_COUNT, maxIndex));
        }
    }

    private List<String> splitCoordinate(String userInput) {
        return Arrays.stream(userInput.split(COMMA_DELIMITER)).toList();
    }

    private String userInput() {
        return sc.nextLine();
    }
}
