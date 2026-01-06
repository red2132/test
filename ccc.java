public enum MobinsCategory implements MobinsRateStrategy {

    PET {
        @Override
        public List<elMobinsRate> build(String selectedDate, List<MobinsResult> resultList) {
            return singleCategory(
                    selectedDate,
                    name(),
                    resultList,
                    null
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
                .map(content ->
                        singleCategory(
                                selectedDate,
                                content,
                                resultList,
                                content
                        )
                )
                .flatMap(List::stream)
                .toList();
    }

    /* ================= 공통 메서드 ================= */

    protected List<elMobinsRate> singleCategory(
            String selectedDate,
            String categoryName,
            List<MobinsResult> resultList,
            String contentFilter
    ) {
        return List.of(
                elMobinsRate.of(
                        selectedDate,
                        categoryName,
                        toRates(resultList, contentFilter, selectedDate)
                )
        );
    }

    protected List<elMobinsRate.ElRate> toRates(
            List<MobinsResult> resultList,
            String contentFilter,
            String selectedDate
    ) {
        return resultList.stream()
                .filter(e -> contentFilter == null || contentFilter.equals(e.getContent()))
                .map(e -> elMobinsRate.ElRate.of(
                        contentFilter == null ? e.getBasDt() : selectedDate,
                        e.getPageStep(),
                        e.getBounceCount()
                ))
                .toList();
    }
}
