package janggi.view;

import janggi.board.Turn;
import janggi.game.GameRoom;
import janggi.piece.*;
import janggi.position.Position;
import janggi.team.Team;

import java.util.*;

public class Output {

    private static final String RESET = "\u001B[0m";
    private static final String HAN_RED = "\u001B[31m";
    private static final String CHO_BLUE = "\u001B[34m";

    private static final Map<PieceType, String> PIECE_VIEWS = Map.of(
            PieceType.KING, "K",
            PieceType.HORSE, "H",
            PieceType.ELEPHANT, "E",
            PieceType.GUARD, "G",
            PieceType.SOLDIER, "S",
            PieceType.CHARIOT, "C",
            PieceType.CANNON, "P"
    );

    private static final Map<Team, String> TEAMS = Map.of(
            Team.CHO,"초",
            Team.HAN,"한"
    );

    public void printGameRooms(List<String> roomNames) {
        if (roomNames.isEmpty()) {
            System.out.println("참가 가능한 게임 방이 없습니다.");
            System.out.println("방을 생성해 주세요.");
            return;
        }

        System.out.println("---참가 가능한 방---");
        for (String roomName : roomNames) {
            System.out.println(roomName);
        }
        System.out.println("-----------------");
    }

    public void printBoard(Map<Position, Piece> locatedPieces) {

        String[][] board = new String[10][9];

        for (String[] row : board) {
            Arrays.fill(row,"_");
        }

        for (Map.Entry<Position, Piece> piece : locatedPieces.entrySet()) {
            int row = piece.getKey().row();
            int column = piece.getKey().column();
            String color = piece.getValue().getTeam().equals(Team.CHO) ? CHO_BLUE : HAN_RED;
            Map.Entry<PieceType,String> findEntry = PIECE_VIEWS.entrySet().stream()
                    .filter(entry -> entry.getKey() == piece.getValue().getPieceType())
                    .findFirst()
                    .orElseThrow();
            board[row - 1][column - 1] = color + findEntry.getValue() + RESET;
        }

        for (int i = 0; i <= 9; i++) {
            System.out.println();
            for (int j = 0; j <= 8; j++) {
                System.out.print(board[i][j]);
            }
        }

        System.out.println();
    }

    public void printGameResult(Piece piece) {
        System.out.printf("%s 진영이 승리하였습니다" ,TEAMS.get( piece.getTeam()));
        System.out.println();
    }

    public void printScore(int choScore, int hanScore) {
        System.out.printf("초 진영 : %s점" ,choScore);
        System.out.println();
        System.out.printf("한 진영 : %s점" ,hanScore);
        System.out.println();
    }

    public void printTurn(Team turn) {
        System.out.println();
        System.out.printf("순서 : %s 진영%n", TEAMS.get(turn));
        System.out.println();
    }
}
