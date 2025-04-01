package view;

import domain.Country;
import domain.JanggiBoard;
import domain.JanggiCoordinate;
import domain.dto.GameRoomDto;
import domain.piece.Piece;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static domain.JanggiBoard.COL_SIZE;
import static domain.JanggiBoard.ROW_SIZE;

public class OutputView {
    public static final String RESET = "\u001B[0m";  // 색상 초기화
    public static final String RED = "\u001B[31m";   // 빨간색
    public static final String GREEN = "\u001B[32m"; // 초록색

    private static final int PRINT_START_ROW = 0;
    private static final int PRINT_START_COL = 0;

    public void printCurrTurn(Country currTurn) {
        System.out.println("현재 " + currTurn.getName() + "의 차례입니다.");
    }

    public void printCurrBoard(JanggiBoard board) {

        for (int row = PRINT_START_ROW; row <= ROW_SIZE; row++) {
            if (row == PRINT_START_ROW) {
                System.out.println("  １ ２ ３ ４ ５ ６ ７ ８ ９");
                continue;
            }

            for (int col = PRINT_START_COL; col <= COL_SIZE; col++) {
                if (col == PRINT_START_COL) {
                    if (row == 10) {
                        System.out.print(0 + " ");
                        continue;
                    }
                    System.out.print(row + " ");
                    continue;
                }

                JanggiCoordinate coordinate = new JanggiCoordinate(row, col);
                if (board.isOccupied(coordinate)) {
                    Piece piece = board.findPieceByCoordinate(coordinate);
                    System.out.print(getColorBy(piece) + " " + RESET);
                    continue;
                }
                System.out.print("＿ ");
            }
            System.out.println();
        }
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printWinner(Country winner) {
        System.out.println("승자는 " + winner.getName() + " 입니다.");
    }

    public void printScore(Country country, double countryScore) {
        String stringScore = scoreFormater(countryScore);
        System.out.println(country.getName() + "의 점수는 : " + stringScore + " 입니다.");
    }

    private String getColorBy(Piece piece) {
        if (piece.getCountry() == Country.CHO) {
            return GREEN + piece.getPieceType().getName();
        }
        return RED + piece.getPieceType().getName();
    }

    private String scoreFormater(double score) {
        if (score % 1 == 0) {
            return String.valueOf((int) score);
        }
        return String.valueOf(score);
    }

    public void printGameNames(List<GameRoomDto> allGames) {
        System.out.println("현재 세이브 되어있는 게임 목록입니다.");
        System.out.println();
        for (GameRoomDto dto : allGames) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 m분");
            String creationTime = dto.creationDate().format(dateTimeFormatter);
            System.out.println("게임 이름 : " + dto.gameRoomName());
            System.out.println("현재 턴 : " + dto.currTurn());
            System.out.println("저장된 시간 " + creationTime);
            System.out.println();
        }
    }
}
