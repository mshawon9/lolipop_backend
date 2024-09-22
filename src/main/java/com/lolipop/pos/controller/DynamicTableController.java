package com.lolipop.pos.controller;

import com.lolipop.pos.service.DynamicTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DynamicTableController {

    @Autowired
    private DynamicTableService dynamicTableService;

    @GetMapping("/tableColumns")
    public List<Map<String, String>> getTableColumns(@RequestParam String tableName) {
        return dynamicTableService.getTableColumns(tableName);
    }
}
