        List<elMobinsRate> responseList = switch (categoryType) {
            case "PET", "OWNER_KT_AGENCY" -> {
                List<elMobinsRate> dataList = new ArrayList<>();
                List<elMobinsRate.ElRate> statData = resultList.stream().map(m -> elMobinsRate.ElRate.of(
                        m.getBasDt(),
                        m.getPageStep(),
                        m.getBounceCount()
                )).toList();
                dataList.add(elMobinsRate.of(selectedDate, categoryType, statData));
                yield dataList;
            }
            case "PHISHING" -> {
                List<elMobinsRate> dataList = new ArrayList<>();
                List<elMobinsRate.ElRate> phishing = resultList.stream().filter(e -> "PHISHING".equals(e.getContent())).map(e -> elMobinsRate.ElRate.of(selectedDate, e.getPageStep(), e.getBounceCount())).toList();
                List<elMobinsRate.ElRate> phishingPaid = resultList.stream().filter(e -> "PHISHING_PAID".equals(e.getContent())).map(e -> elMobinsRate.ElRate.of(selectedDate, e.getPageStep(), e.getBounceCount())).toList();

                dataList.add(elMobinsRate.of(selectedDate, "PHISHING", phishing));
                dataList.add(elMobinsRate.of(selectedDate, "PHISHING_PAID", phishingPaid));
                yield dataList;
            }
            case "OWNER" -> {
                List<elMobinsRate> dataList = new ArrayList<>();
                List<elMobinsRate.ElRate> owner = resultList.stream().filter(e -> "OWNER".equals(e.getContent())).map(e -> elMobinsRate.ElRate.of(selectedDate, e.getPageStep(), e.getBounceCount())).toList();
                List<elMobinsRate.ElRate> ownerReqInsOnly = resultList.stream().filter(e -> "OWNER_REG_INS_ONLY".equals(e.getContent())).map(e -> elMobinsRate.ElRate.of(selectedDate, e.getPageStep(), e.getBounceCount())).toList();

                dataList.add(elMobinsRate.of(selectedDate, "OWNER", owner));
                dataList.add(elMobinsRate.of(selectedDate, "OWNER_REG_INS_ONLY", ownerReqInsOnly));
                yield dataList;
            }
            case "TRIP" -> {
                List<elMobinsRate> dataList = new ArrayList<>();
                List<elMobinsRate.ElRate> shortSingle = resultList.stream().filter(e -> "TRIP_SHORT_SINGLE".equals(e.getContent())).map(e -> elMobinsRate.ElRate.of(selectedDate, e.getPageStep(), e.getBounceCount())).toList();
                List<elMobinsRate.ElRate> shortMultiple = resultList.stream().filter(e -> "TRIP_SHORT_MULTIPLE".equals(e.getContent())).map(e -> elMobinsRate.ElRate.of(selectedDate, e.getPageStep(), e.getBounceCount())).toList();
                List<elMobinsRate.ElRate> longNew = resultList.stream().filter(e -> "TRIP_LONG_NEW".equals(e.getContent())).map(e -> elMobinsRate.ElRate.of(selectedDate, e.getPageStep(), e.getBounceCount())).toList();
                List<elMobinsRate.ElRate> longExtend = resultList.stream().filter(e -> "TRIP_LONG_EXTEND".equals(e.getContent())).map(e -> elMobinsRate.ElRate.of(selectedDate, e.getPageStep(), e.getBounceCount())).toList();
                List<elMobinsRate.ElRate> care365 = resultList.stream().filter(e -> "TRIP_SHORT_EVT365_25".equals(e.getContent())).map(e -> elMobinsRate.ElRate.of(selectedDate, e.getPageStep(), e.getBounceCount())).toList();

                dataList.add(elMobinsRate.of(selectedDate, "TRIP_SHORT_SINGLE", shortSingle));
                dataList.add(elMobinsRate.of(selectedDate, "TRIP_SHORT_MULTIPLE", shortMultiple));
                dataList.add(elMobinsRate.of(selectedDate, "TRIP_LONG_NEW", longNew));
                dataList.add(elMobinsRate.of(selectedDate, "TRIP_LONG_EXTEND", longExtend));
                dataList.add(elMobinsRate.of(selectedDate, "TRIP_SHORT_EVT365_25", care365));
                yield dataList;
            }
            default -> new ArrayList<>();
