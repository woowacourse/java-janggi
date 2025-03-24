package janggi.view;

import janggi.domain.Dynasty;
import janggi.domain.Player;
import janggi.domain.board.Point;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.ChuSoldier;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.HanSoldier;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class JanggiBoardView {

    private static final Pattern MOVE_PATTERN = Pattern.compile("move [ㄱㄴㄷㄹㅁㅂㅅㅇㅈ](10|[1-9]) [ㄱㄴㄷㄹㅁㅂㅅㅇㅈ](10|[1-9])");
    private static final Scanner scanner = new Scanner(System.in);
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final Map<Piece, String> PIECE_LABELS = new HashMap<>() {{
        put(new General(Dynasty.CHU), "궁");
        put(new Guard(Dynasty.CHU), "사");
        put(new Chariot(Dynasty.CHU), "차");
        put(new Cannon(Dynasty.CHU), "포");
        put(new Horse(Dynasty.CHU), "마");
        put(new Elephant(Dynasty.CHU), "상");
        put(new ChuSoldier(), "졸");

        put(new General(Dynasty.HAN), "궁");
        put(new Guard(Dynasty.HAN), "사");
        put(new Chariot(Dynasty.HAN), "차");
        put(new Cannon(Dynasty.HAN), "포");
        put(new Horse(Dynasty.HAN), "마");
        put(new Elephant(Dynasty.HAN), "상");
        put(new HanSoldier(), "졸");
    }};

    private static final String BLUE = "\u001B[34m";
    private static final Map<String, Integer> VERTICAL_INPUT_MAP = Map.of(
            "ㄱ", 1,
            "ㄴ", 2,
            "ㄷ", 3,
            "ㄹ", 4,
            "ㅁ", 5,
            "ㅂ", 6,
            "ㅅ", 7,
            "ㅇ", 8,
            "ㅈ", 9
    );

    public void printGameStartMessage() {
        System.out.println("""
                장기게임을 시작합니다.
                게임 이동: move source위치 target위치 예) move ㄱ2 ㄴ3
                게임 종료를 원하시면 end를 입력해주세요.
                """);
    }

    public Movement readPlayerMove(Player player) {
        System.out.println();
        printPlayerMoveGuide(player);

        String command = readLine().trim();
        if (command.equals("end")) {
            return new Movement(command);
        }
        String[] splitCommands = command.split(" ");
        if (MOVE_PATTERN.matcher(command).matches()) {
            int startY = VERTICAL_INPUT_MAP.get(Character.toString(splitCommands[1].charAt(0)));
            int startX = Integer.parseInt(splitCommands[1].substring(1));

            int endY = VERTICAL_INPUT_MAP.get(Character.toString(splitCommands[2].charAt(0)));
            int endX = Integer.parseInt(splitCommands[2].substring(1));
            return new Movement("move", startX, startY, endX, endY);
        }
        throw new IllegalArgumentException("입력 형식이 틀렸습니다.");
    }

    private void printPlayerMoveGuide(Player player) {
        if (player.getDynasty() == Dynasty.HAN) {
            System.out.println(convertHanColor(player.getNickname()) + "의 차례입니다. 이동할 위치를 입력해주세요. 예) move ㄱ2 ㄴ3");
            return;
        }
        System.out.println(convertChuColor(player.getNickname()) + "의 차례입니다. 이동할 위치를 입력해주세요. 예) move ㄱ2 ㄴ3");
    }

    public void printBoard(Map<Point, Piece> boardPieces) {

        for (int x = 1; x <= 10; x++) {
            for (int y = 1; y <= 9; y++) {
                Point point = new Point(x, y);
                if (boardPieces.containsKey(point)) {
                    printPointPiece(boardPieces.get(point));
                    continue;
                }
                System.out.print("ㅁ ");
            }
            System.out.print("  " + x);
            System.out.println();
        }
        System.out.println("ㄱ ㄴ ㄷ ㄹ ㅁ ㅂ ㅅ ㅇ ㅈ");
    }

    private void printPointPiece(Piece boardPiece) {
        String pieceLabel = PIECE_LABELS.get(boardPiece);
        if (boardPiece.isDynasty(Dynasty.HAN)) {
            System.out.print(convertHanColor(pieceLabel) + " ");
            return;
        }
        System.out.print(convertChuColor(pieceLabel + " "));
    }

    private String convertChuColor(String pieceLabel) {
        return BLUE + pieceLabel + RESET;
    }

    private String convertHanColor(String pieceLabel) {
        return RED + pieceLabel + RESET;
    }

    private String readLine() {
        return scanner.nextLine().trim();
    }

    public record Movement(
            String command, int startX, int startY, int endX, int endY
    ) {
        public Movement(String command) {
            this(command, -1, -1, -1, -1);
        }

        public boolean isEnd() {
            return this.command.equals("end");
        }

        public boolean isMove() {
            return this.command.equals("move");
        }
    }
}
