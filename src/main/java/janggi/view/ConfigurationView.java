package janggi.view;

import java.util.Scanner;

public class ConfigurationView {

    private static final Scanner scanner = new Scanner(System.in);

    public boolean readOnlineOrLocal(int maxSeconds) {
        System.out.println("실행 옵션을 선택해 주세요.");
        System.out.println("- 게임 서버에 연결하기 -> \"Y\"");
        System.out.println("- 연결하지 않고 게임하기 -> \"N\"");
        System.out.println();
        System.out.printf("처음 실행의 경우 최대 %s초 가량 소요될 수 있습니다.", maxSeconds);
        System.out.println();

        String input = scanner.nextLine();
        if ("Y".equalsIgnoreCase(input)) {
            return true;
        }
        if ("N".equalsIgnoreCase(input)) {
            return false;
        }
        throw new IllegalArgumentException("잘못 입력하셨습니다.");
    }

    public void printConnectionFailed() {
        System.err.println("-----------------------------");
        System.err.println(" 데이터베이스가 연결되지 않았습니다.");
        System.err.println("게임을 불러오거나 저장하지 않습니다.");
        System.err.println("-----------------------------");
        System.err.println();
    }

    public void printConnectingServer() {
        System.out.println("서버에 연결중입니다..");
        System.out.println(ViewTools.applyColor(
            ViewTools.RED, "! 강제 종료 시 서버 프로세스(docker)가 유지될 수 있습니다 !")
        );
        System.out.println();
    }

    public void printStoppingServer() {
        System.out.println("서버를 중지하는 중입니다.. (약 2초 소요됨)");
    }
}
