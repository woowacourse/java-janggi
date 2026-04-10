package janggi.view;

import janggi.application.dto.GameRoomDto;
import janggi.domain.position.Column;
import janggi.view.dto.BoardDto;
import janggi.view.dto.PieceDto;
import janggi.view.dto.PositionDto;
import janggi.domain.dynasty.Dynasty;
import janggi.view.mapper.DynastyMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class OutputView {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ERROR_PREFIX = "[ERROR] ";

    private static final List<String> FULL_WIDTH_NUMBERS = List.of("-1", "１","２", "３", "４", "５", "６", "７", "８", "９", "０");

    public void printBoard(BoardDto boardDto) {
        printRow();
        printColumn(boardDto);
        printColorInfoByDynasty();
    }

    private static void printRow() {
        System.out.print("   ");
        for (int col = Column.MIN_COLUMN; col <= Column.MAX_COLUMN; col++) {
            System.out.print(FULL_WIDTH_NUMBERS.get(col) + " ");
        }
        System.out.println();
    }

    private static void printColumn(BoardDto boardDto) {
        int rowIndex = 1;
        for (List<PieceDto> piecesByRow : boardDto.board()) {
            System.out.printf("%s ", FULL_WIDTH_NUMBERS.get(rowIndex++));

            for (PieceDto piece : piecesByRow) {
                System.out.print(piece.name() + " " + ANSI_RESET);
            }
            System.out.println();
        }
    }

    private static void printColorInfoByDynasty() {
        StringBuilder sb = new StringBuilder("나라별 색상: ");
        Arrays.stream(Dynasty.values()).forEach(dynasty ->
                sb.append(DynastyMapper.toKoreanWithColor(dynasty)));
        System.out.println(sb);
    }

    public void printCanMovePositions(List<PositionDto> positions) {

        System.out.print("현재 이동 가능한 위치는");

        StringJoiner stringJoiner = new StringJoiner(",");
        for (PositionDto position : positions) {
            stringJoiner.add(" (" + convertRow(position.row()) + "," + position.column() + ")");
        }
        System.out.println(stringJoiner + "입니다.");
        System.out.println();
    }

    private static int convertRow(int row) { // 출력은 0으로, 내부적으로는 10으로 처리되므로
        if(row == 10) {
            return 0;
        }
        return row;
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(ERROR_PREFIX + errorMessage);
        System.out.println();
    }

    public void printWinner(Dynasty winner) {
        System.out.println("게임 종료. 승리: " + DynastyMapper.toKoreanWithColor(winner));
    }

    public void printScore(Map<Dynasty, Double> scoreMap) {
        System.out.println("현재 점수: ");
        for (Dynasty dynasty : scoreMap.keySet()) {
            System.out.println(DynastyMapper.toKoreanWithColor(dynasty) + ": " + scoreMap.get(dynasty));
        }
    }

    public void printGameList(List<GameRoomDto> gameRoomDtos) {
        if(gameRoomDtos.isEmpty()) {
            System.out.println("현재 저장되어 있는 게임이 없습니다. ");
            return;
        }
        System.out.println("플레이 하고 싶은 게임을 선택해주세요: ");
        int idx = 1;
        for (GameRoomDto gameRoomDto : gameRoomDtos) {
            System.out.printf("%d. %s\n", idx++, gameRoomDto.roomName());
        }
    }
}
