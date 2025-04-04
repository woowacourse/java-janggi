package view;

import domain.unit.Team;
import entity.Room;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readRoomId(List<Room> rooms) {
        System.out.println("0 : 새로운 방 생성하기");
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            System.out.printf("%d : \"%s\"에 참가\n", i + 1, room.roomId());
        }
        return scanner.nextLine();
    }

    public String readRoomIdToCreate() {
        System.out.println("생성할 방의 이름을 정해주세요");
        return scanner.nextLine();
    }

    public String reReadRoomIdToCreate() {
        System.out.println("이미 존재하는 방입니다. 이름을 다시 정해주세요.");
        return scanner.nextLine();
    }

    public String readUnitPosition(Team team) {
        System.out.println(teamToName(team) + ":: 이동할 장기의 위치를 선택해 주세요.");
        return scanner.nextLine();
    }

    public String readDestinationPosition(Team team) {
        System.out.println("도착할 위치를 선택해 주세요.");
        return scanner.nextLine();
    }

    private String teamToName(Team team) {
        if (team == Team.HAN) {
            return "한나라";
        }
        if (team == Team.CHO) {
            return "초나라";
        }
        throw new IllegalStateException("예기치 못한 예외가 발생하였습니다.");
    }
}
