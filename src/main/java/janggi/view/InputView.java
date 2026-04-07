package janggi.view;

import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.board.setup.BoardSetUpFormat;
import janggi.domain.side.Side;

public class InputView {

    private static final Pattern pattern = Pattern.compile("^([가나다라마바사아자차])([0-8])$");

    private final Scanner sc;

    public InputView(InputStream inputStream) {
        sc = new Scanner(inputStream);
    }

    public boolean readContinueGame() {
        System.out.println("진행 중인 게임이 있습니다. 이어서 플레이하시겠습니까? (Y / N)");

        String input = sc.nextLine().trim().toUpperCase();
        if (input.equals("Y")) {
            return true;
        }
        if (input.equals("N")) {
            return false;
        }

        throw new IllegalArgumentException("Y 또는 N을 입력해주세요.");

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
        return Point.of(x, y);
    }

    public Optional<Point> readDestination() {
        System.out.println("기물 이동[초록색] - {한글}{숫자} (e.g. 가0) (취소 - Q 입력)");
        String input = sc.nextLine();
        Matcher matcher = pattern.matcher(input);

        if (input.equals("Q")) {
            return Optional.empty();
        }

        if (!matcher.matches()) {
            throw new IllegalArgumentException("입력은 {한글}{숫자} (e.g. 가0) 형식으로 입력해 주세요.");
        }

        int x = XPointFormat.convertToInt(matcher.group(1));
        int y = parseToInt(matcher.group(2));
        return Optional.of(Point.of(x, y));
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
