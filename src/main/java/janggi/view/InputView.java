package janggi.view;

import janggi.domain.Country;
import janggi.domain.StartingPosition;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;
import janggi.dto.CommandType;
import janggi.dto.MoveDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class InputView {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private static final Map<Country, String> COUNTRY_NAMES = Map.of(
            Country.CHO, "초나라",
            Country.HAN, "한나라"
    );

    private static String readLine() {
        try {
            return READER.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CommandType getStartType() {
        StringBuilder sb = new StringBuilder();
        sb.append("장기 게임을 시작합니다.").append("\n");
        sb.append("새로운 게임 : 1 / 이어하기 : 2");
        System.out.println(sb);
        final int input = Integer.parseInt(readLine());
        if (input == 1) return CommandType.NEW_GAME;
        if (input == 2) return CommandType.CONTINUE;
        throw new IllegalArgumentException();
    }

    public int getStartFileNumber() {
        System.out.println("몇번째 게임을 불러오시겠습니까?");
        return Integer.parseInt(readLine());
    }

    public StartingPosition getStartPositionOf(final Country country) {
        StringBuilder sb = new StringBuilder();
        sb.append(COUNTRY_NAMES.get(country)).append("의 시작 형태를 골라주세요.").append("\n");
        sb.append("마상마상 : 1 / 상마상마 : 2 / 마상상마 : 3 / 상마마상 : 4");
        System.out.println(sb);
        final int input = Integer.parseInt(readLine());
        if (input == 1) return StartingPosition.마상마상;
        if (input == 2) return StartingPosition.상마상마;
        if (input == 3) return StartingPosition.마상상마;
        if (input == 4) return StartingPosition.상마마상;
        throw new IllegalArgumentException();
    }

    public CommandType inputCommand(final Country currentCountry) {
        StringBuilder sb = new StringBuilder();
        sb.append(COUNTRY_NAMES.get(currentCountry)).append("의 차례입니다.").append("\n");
        sb.append("움직이기 : 1 / 저장하고 그만두기 : 2");
        System.out.println(sb);
        final int input = Integer.parseInt(readLine());
        if (input == 1) return CommandType.MOVE;
        if (input == 2) return CommandType.SAVE;
        throw new IllegalArgumentException();
    }

    public int getSaveNumber() {
        System.out.println("몇번 파일에 저장하시겠습니까?");
        return Integer.parseInt(readLine());
    }

    public MoveDto inputMove() {
        StringBuilder sb = new StringBuilder();
        sb.append("움직일 기물의 위치와 움직일 위치를 입력해주세요.").append("\n");
        sb.append("입력 방식 : [파일],[랭크]-[파일],[랭크] (예시: 1,1-3,2)");
        System.out.println(sb);
        final String[] inputs = readLine().split("-");
        return new MoveDto(parseToPosition(inputs[0]), parseToPosition(inputs[1]));
    }

    private Position parseToPosition(final String value) {
        final String[] xy = value.split(",");
        final PositionFile file = PositionFile.findByAmount(Integer.parseInt(xy[0]));
        final PositionRank rank = PositionRank.findByAmount(Integer.parseInt(xy[1]));
        return new Position(file, rank);
    }
}
