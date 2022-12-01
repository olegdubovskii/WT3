package com.poit.archive.criteria;

import java.util.HashMap;
import java.util.Map;

public class Criteria {
    private final Map<String, Object> criteriaMap = new HashMap<>();

    public Map<String, Object> getCriteriaMap() {
        return criteriaMap;
    }

    public void add(String searchCriteria, Object value) {
        criteriaMap.put(searchCriteria, value);
    }
}
