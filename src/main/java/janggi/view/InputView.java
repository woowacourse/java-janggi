package janggi.view;

import janggi.controller.GameSelect;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputView {
    private static final Pattern pattern = Pattern.compile("^([가나다라마바사아자차])([0-8])$");

    private final Scanner sc;

    public InputView(InputStream inputStream) {
        sc = new Scanner(inputStream);
    }

    public String readGameName() {
        System.out.println("생성할 게임의 이름을 입력해 주세요!");
        return sc.nextLine();
    }

    public BoardSetUp readBoardSetup(Side side) {
        System.out.println(side + "의 차림을 선택해주세요.");
        for (BoardSetUpFormat value : BoardSetUpFormat.values()) {
            System.out.println("\t" + value.getNumber() + ". " + value.getFormat());
        }

        String input = sc.nextLine();

        validateBlank(input);
        int number = parseToInt(input);

        return BoardSetUpFormat.getBoardSetUp(number);
    }

    public Point readPoint() {
        System.out.println("기물 선택 - {한글}{숫자} (e.g. 가0)");
        String input = sc.nextLine();
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("입력은 {한글}{숫자} (e.g. 가0) 형식으로 입력해 주세요.");
        }

        int x = XPointFormat.convertToInt(matcher.group(1));
        int y = parseToInt(matcher.group(2));
        return new Point(x, y);
    }

    public Point readDestination() {
        System.out.println("기물 이동[초록색] - {한글}{숫자} (e.g. 가0) (취소 - C 입력, 제자리에 놓으면 턴을 넘깁니다.)");
        String input = sc.nextLine();
        Matcher matcher = pattern.matcher(input);

        if (input.equals("C")) {
            return null;
        }

        if (!matcher.matches()) {
            throw new IllegalArgumentException("입력은 {한글}{숫자} (e.g. 가0) 형식으로 입력해 주세요.");
        }

        int x = XPointFormat.convertToInt(matcher.group(1));
        int y = parseToInt(matcher.group(2));
        return new Point(x, y);
    }

    public GameSelect readGameSelect() {
        return GameSelect.from(sc.nextLine());
    }

    private void validateBlank(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("공백은 입력할 수 없습니다.");
        }
    }

    private int parseToInt(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
    }
}
