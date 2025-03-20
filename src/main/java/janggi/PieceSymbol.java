package janggi;

public enum PieceSymbol {

    CANNON() {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("포");
            }
            return colorHanAttribute("포");
        }
    },
    CHARIOT() {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("차");
            }
            return colorHanAttribute("차");
        }
    },
    ELEPHANT() {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("상");
            }
            return colorHanAttribute("상");
        }
    },
    GENERAL() {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("왕");
            }
            return colorHanAttribute("왕");
        }
    },
    GUARD() {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("사");
            }
            return colorHanAttribute("사");
        }
    },
    HORSE() {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("마");
            }
            return colorHanAttribute("마");
        }
    },
    SOLDIER() {
        @Override
        public String getDisplayAttributes(Camp camp) {
            if (camp == Camp.CHU) {
                return colorChuAttribute("졸");
            }
            return colorHanAttribute("병");
        }
    },
    ;

    private static final String RED_COLOR_CODE = "\u001B[31m";
    private static final String GREEN_COLOR_CODE = "\u001B[32m";
    private static final String EXIT_CODE = "\u001B[0m";

    public abstract String getDisplayAttributes(Camp camp);

    public String colorChuAttribute(String value) {
        return GREEN_COLOR_CODE + value + EXIT_CODE;
    }

    public String colorHanAttribute(String value) {
        return RED_COLOR_CODE + value + EXIT_CODE;
    }
}
