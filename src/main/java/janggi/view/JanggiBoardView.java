package janggi.view;

import janggi.domain.Dynasty;
import janggi.domain.Player;
import janggi.domain.board.point.DefaultPoint;
import janggi.domain.board.point.Point;
import janggi.domain.piece.BoardPiece;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.Guard;
import janggi.domain.piece.King;
import janggi.domain.piece.Knight;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Rook;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class JanggiBoardView {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final Map<Piece, String> PIECE_LABELS = Map.of(
            new King(), "궁",
            new Guard(), "사",
            new Rook(), "차",
            new Cannon(), "포",
            new Knight(), "마",
            new Elephant(), "상",
            new Pawn(), "졸"
    );
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

    private static Map<Point, BoardPiece> createBoardPiecesMap(Set<BoardPiece> boardPieces) {
        Map<Point, BoardPiece> pieceMap = new HashMap<>();
        for (BoardPiece boardPiece : boardPieces) {
            Point currentPoint = boardPiece.getCurrentPoint();
            pieceMap.put(new DefaultPoint(currentPoint.getX(), currentPoint.getY()), boardPiece);
        }
        return pieceMap;
    }

    public void printGameStartMessage() {
        System.out.println("""
                장기게임을 시작합니다.
                게임 이동: move source위치 target위치 예) move ㄱ2 ㄴ3
                게임 종료를 원하시면 end를 입력해주세요.
                """);
    }

    public Movement readPlayerMove(Player player) {
        System.out.println(player.getNickname() + "의 차례입니다. 이동할 위치를 입력해주세요. 예) move ㄱ2 ㄴ3");

        String command = readLine().trim();
        if (command.equals("end")) {
            return new Movement(command);
        }
        String[] splitCommands = command.split(" ");
        if (splitCommands[0].equals("move")) {
            int startY = VERTICAL_INPUT_MAP.get(Character.toString(splitCommands[1].charAt(0)));
            int startX = Integer.parseInt(splitCommands[1].substring(1));

            int endY = VERTICAL_INPUT_MAP.get(Character.toString(splitCommands[2].charAt(0)));
            int endX = Integer.parseInt(splitCommands[2].substring(1));
            return new Movement("move", startX, startY, endX, endY);
        }
        throw new IllegalArgumentException("입력 형식이 틀렸습니다.");
    }

    public void printBoard(Set<BoardPiece> boardPieces) {
        Map<Point, BoardPiece> boardPieceMap = createBoardPiecesMap(boardPieces);

        for (int x = 1; x <= 10; x++) {
            for (int y = 1; y <= 9; y++) {
                DefaultPoint point = new DefaultPoint(x, y);
                if (boardPieceMap.containsKey(point)) {
                    BoardPiece boardPiece = boardPieceMap.get(point);
                    printPointPiece(boardPiece);
                    continue;
                }
                System.out.print("ㅁ");
            }
            System.out.print("  " + x);
            System.out.println();
        }
        System.out.println("ㄱㄴㄷㄹㅁㅂㅅㅇㅈ");
    }

    private void printPointPiece(BoardPiece boardPiece) {
        String pieceLabel = PIECE_LABELS.get(boardPiece.getPiece());
        if (boardPiece.getDynasty() == Dynasty.HAN) {
            System.out.print(RED + pieceLabel + RESET);
            return;
        }
        System.out.print(BLUE + pieceLabel + RESET);
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
    }
}
