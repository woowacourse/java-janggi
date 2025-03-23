package console.util;

import janggi.position.Position;

public record CommandConverter(
    Position source,
    Position destination,
    boolean abstain
) {
    public static CommandConverter from(String input) {
        String[] split = input.split(" ");

        if (split.length == 1) {
            if (split[0].equalsIgnoreCase("q")) {
                return new CommandConverter(null, null, true);
            }
            throw new IllegalArgumentException("[ERROR] 잘못된 입력 형식입니다.");
        }

        try {
//            Position source = new Position(split[0].charAt(0) - 'a', split[0].charAt(1) - '0');
//            Position destination = new Position(split[1].charAt(0) - 'a', split[1].charAt(1) - '0');

            return new CommandConverter(null, null, false);
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 입력 형식입니다.");
        }
    }
}
