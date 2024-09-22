package com.lolipop.pos.service;

import java.util.List;
import java.util.Map;

public interface DynamicTableService {
    List<Map<String, String>> getTableColumns(String tableName);

    void generateEntityAndCRUDClasses(String tableName, String columns);

    String capitalize(String str);
}
