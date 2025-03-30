package janggi.view;

import janggi.domain.board.JanggiBoard;
import janggi.domain.gamestatus.GameEnded;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.ChuSoldier;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.HanSoldier;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Point;
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
                """);
    }

    public Movement readPlayerMove(Dynasty dynasty) {
        System.out.println();
        printPlayerMoveGuide(dynasty);

        String command = readLine().trim();
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

    private void printPlayerMoveGuide(Dynasty dynasty) {
        if (dynasty == Dynasty.HAN) {
            System.out.println(convertHanColor(toDynastyName(dynasty)) + "의 차례입니다. 이동할 위치를 입력해주세요. 예) move ㄱ2 ㄴ3");
            return;
        }
        System.out.println(convertChuColor(toDynastyName(dynasty)) + "의 차례입니다. 이동할 위치를 입력해주세요. 예) move ㄱ2 ㄴ3");
    }

    public void printBoard(JanggiBoard janggiBoard) {
        Map<Point, Piece> boardPieces = janggiBoard.getPieces();
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

    public void printScore(JanggiBoard janggiBoard) {
        System.out.println("한나라 점수: " + janggiBoard.dynastyScore(Dynasty.HAN));
        System.out.println("초나라 점수: " + janggiBoard.dynastyScore(Dynasty.CHU));
    }

    public void printResult(GameEnded janggiEnded) {
        System.out.println(toDynastyName(janggiEnded.winner()) + "님이 이겼습니다!");
        System.out.println("==최종 점수==");
        printScore(janggiEnded.janggiBoard());
    }

    public record Movement(
            String command, int startX, int startY, int endX, int endY
    ) {
    }

    private String toDynastyName(Dynasty dynasty) {
        if (dynasty == Dynasty.HAN) {
            return "한나라";
        }
        return "초나라";
    }
}
