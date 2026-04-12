package view;

import controller.CommandType;
import domain.board.formation.InitialFormationType;
import domain.state.Side;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import dto.PositionDto;
import dto.PossibleMovesDto;
import view.message.GameCommandFormatter;
import view.message.InitialFormationFormatter;
import view.message.SideView;

public class InputView {

    private static final int INDEX_OFFSET = 1;
    private static final int INIT_INDEX_COUNT = 1;
    private static final int MAX_COMMAND_INDEX = 3;
    private static final int MAX_FORMATION_INDEX = 4;
    private static final String COMMA_DELIMITER = ",";
    private static final String BINARY_INPUT_REGEX = "^([YN])$";

    private static final String REQUEST_INIT_FORMAT_STRATEGY = "\n%s는 초기 (상·마) 포진을 선택해주세요.\n";
    private static final String REQUEST_MOVING_START_PIECE_POSITION = "\n%s 턴! 이동할 기물의 좌표를 입력해주세요. (e.g. 2,3)\n";
    private static final String REQUEST_PIECE_DESTINATION = "움직일 좌표의 번호를 선택해주세요.";
    private static final String REQUEST_GAME_COMMAND = "\n%s 턴입니다.\n";
    private static final String REQUEST_INPUT =  "번호를 입력해주세요.";
    private static final String YES = "Y";

    private final Scanner sc;

    public InputView(Scanner sc) {
        this.sc = sc;
    }

    public int selectRoom() {
        System.out.println("선택할 게임 번호를 입력하세요 (0: 새 게임): ");
        String input = userInput();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("\n잘못된 입력입니다. 다시 입력 해주세요.");
        }
    }

    public CommandType requestGameCommand(Side side) {
        try {
            int index = INIT_INDEX_COUNT;
            System.out.printf(REQUEST_GAME_COMMAND, SideView.from(side));

            for (int i = 0; i < MAX_COMMAND_INDEX; i++) {
                System.out.printf("%d. %s\n", index, GameCommandFormatter.format(index));
                index++;
            }

            System.out.println(REQUEST_INPUT);
            int userInput = Integer.parseInt(userInput());
            return GameCommandFormatter.from(userInput);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("\n잘못된 입력입니다. 다시 입력 해주세요.");
        }
    }

    public InitialFormationType requestInitialType(Side side) {
        try {
            int index = INIT_INDEX_COUNT;
            System.out.printf(REQUEST_INIT_FORMAT_STRATEGY, SideView.from(side));

            for (int i = 0; i < MAX_FORMATION_INDEX; i++) {
                System.out.printf("%d. %s\n", index, InitialFormationFormatter.format(index));
                index++;
            }

            System.out.println(REQUEST_INPUT);
            int userInput = Integer.parseInt(userInput());
            validateIndex(userInput, MAX_FORMATION_INDEX);

            return InitialFormationFormatter.from(userInput);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("\n잘못된 입력입니다. 다시 입력 해주세요.");
        }
    }

    public List<Integer> requestStartPiecePosition(Side side) {
        try {
            System.out.printf(REQUEST_MOVING_START_PIECE_POSITION, SideView.from(side));
            List<String> inputs = splitCoordinate(userInput());
            int col = Integer.parseInt(inputs.get(0).trim());
            int row = Integer.parseInt(inputs.get(1).trim());

            return List.of(col, row);
        } catch (PatternSyntaxException | NumberFormatException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("\n잘못된 입력입니다. 다시 입력 해주세요.");
        }
    }

    public int requestPieceDestination(PossibleMovesDto possibleMovesDto) {
        try {
            int index = INIT_INDEX_COUNT;

            System.out.println();
            for (PositionDto position : possibleMovesDto.possibleMoves()) {
                System.out.printf("%d. (%d, %d)\n", index++, position.col(), position.row());
            }

            System.out.println(REQUEST_PIECE_DESTINATION);
            int userInput = Integer.parseInt(userInput());
            validateIndex(userInput, possibleMovesDto.possibleMoveCount());

            return userInput - INDEX_OFFSET;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("\n잘못된 입력입니다. 다시 입력 해주세요.");
        }
    }

    public boolean requestRetry() {
        System.out.println("\n게임을 다시 시작하겠습니까? (Y/N)");
        String input = userInput();

        try {
            return validateBinaryInput(input);
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

    private boolean validateBinaryInput(String userInput) {
        if (!Pattern.matches(BINARY_INPUT_REGEX, userInput)) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해주세요.");
        }

        return userInput.equals(YES);
    }

    private String userInput() {
        return sc.nextLine();
    }
}
