package janggi.view;

import janggi.domain.Country;
import janggi.domain.StartingPosition;
import janggi.dto.CommandType;
import janggi.dto.GameStartType;
import janggi.dto.MoveDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringJoiner;

public class InputView {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private final ViewUtil viewUtil;

    public InputView(final ViewUtil viewUtil) {
        this.viewUtil = viewUtil;
    }

    private static String readLine() {
        try {
            return READER.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GameStartType inputStartType() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add("장기 게임을 시작합니다.");
        sj.add("새로운 게임 : 1 / 이어하기 : 2");
        System.out.println(sj);

        final int input = Integer.parseInt(readLine());
        if (input == 1) {
            return GameStartType.NEW_GAME;
        }
        if (input == 2) {
            return GameStartType.CONTINUE;
        }
        throw new IllegalArgumentException("1 또는 2를 입력해야 합니다.");
    }

    public int inputStartFileNumber() {
        System.out.println("몇번째 게임을 불러오시겠습니까?");
        return Integer.parseInt(readLine());
    }

    public StartingPosition inputStartPositionOf(final Country country) {
        StringJoiner sj = new StringJoiner("\n");
        sj.add(viewUtil.convertCountry(country) + "의 시작 형태를 골라주세요.");
        sj.add("마상마상 : 1 / 상마상마 : 2 / 마상상마 : 3 / 상마마상 : 4");
        System.out.println(sj);

        final int input = Integer.parseInt(readLine());
        if (input == 1) {
            return StartingPosition.MA_SANG_MA_SANG;
        }
        if (input == 2) {
            return StartingPosition.SAMG_MA_SANG_MA;
        }
        if (input == 3) {
            return StartingPosition.MA_SANG_SANG_MA;
        }
        if (input == 4) {
            return StartingPosition.SANG_MA_MA_SANG;
        }
        throw new IllegalArgumentException("1~4를 입력해야 합니다.");
    }

    public CommandType inputCommand(final Country currentCountry) {
        StringJoiner sj = new StringJoiner("\n");
        sj.add(viewUtil.convertCountry(currentCountry) + "의 차례입니다.");
        sj.add("움직이기 : 1 / 저장하고 그만두기 : 2");
        System.out.println(sj);

        final int input = Integer.parseInt(readLine());
        if (input == 1) {
            return CommandType.MOVE;
        }
        if (input == 2) {
            return CommandType.SAVE;
        }
        throw new IllegalArgumentException("1 또는 2를 입력해야 합니다.");
    }

    public int inputSaveNumber() {
        System.out.println("몇번 파일에 저장하시겠습니까?");
        return Integer.parseInt(readLine());
    }

    public MoveDto inputMove() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add("움직일 기물의 위치와 움직일 위치를 입력해주세요.");
        sj.add("입력 방식 : [파일],[랭크]-[파일],[랭크] (예시: 1,1-3,2)");
        System.out.println(sj);

        final String[] inputs = readLine().split("-");
        return new MoveDto(viewUtil.parseToPosition(inputs[0]), viewUtil.parseToPosition(inputs[1]));
    }
}
