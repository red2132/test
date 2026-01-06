public enum MobinsCategory implements MobinsRateStrategy {

    PET {
        @Override
        public List<elMobinsRate> build(String selectedDate, List<MobinsResult> resultList) {
            return List.of(
                    elMobinsRate.of(
                            selectedDate,
                            name(),
                            resultList.stream()
                                    .map(e -> elMobinsRate.ElRate.of(
                                            e.getBasDt(),
                                            e.getPageStep(),
                                            e.getBounceCount()
                                    ))
                                    .toList()
                    )
            );
        }
    },

    OWNER_KT_AGENCY {
        @Override
        public List<elMobinsRate> build(String selectedDate, List<MobinsResult> resultList) {
            return PET.build(selectedDate, resultList);
        }
    },

    PHISHING("PHISHING", "PHISHING_PAID"),
    OWNER("OWNER", "OWNER_REG_INS_ONLY"),
    TRIP(
            "TRIP_SHORT_SINGLE",
            "TRIP_SHORT_MULTIPLE",
            "TRIP_LONG_NEW",
            "TRIP_LONG_EXTEND",
            "TRIP_SHORT_EVT365_25"
    );

    private final List<String> contents;

    MobinsCategory(String... contents) {
        this.contents = List.of(contents);
    }

    MobinsCategory() {
        this.contents = List.of();
    }

    @Override
    public List<elMobinsRate> build(String selectedDate, List<MobinsResult> resultList) {
        return contents.stream()
                .map(content -> elMobinsRate.of(
                        selectedDate,
                        content,
                        resultList.stream()
                                .filter(e -> content.equals(e.getContent()))
                                .map(e -> elMobinsRate.ElRate.of(
                                        selectedDate,
                                        e.getPageStep(),
                                        e.getBounceCount()
                                ))
                                .toList()
                ))
                .toList();
    }
}
