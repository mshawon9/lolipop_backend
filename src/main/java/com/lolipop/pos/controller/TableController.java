package com.lolipop.pos.controller;

import com.lolipop.pos.service.DynamicTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TableController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DynamicTableService dynamicTableService;

    @GetMapping("/dynamic-table")
    public String getForm() {
        return "dynamic-table-form";
    }

    @GetMapping("/product-list")
    public String getProductList() {
        return "product-list";
    }

    @PostMapping("/createTable")
    public String createTable(@RequestParam String tableName, @RequestParam String columns) {
        String idColumn = "id BIGINT AUTO_INCREMENT PRIMARY KEY";
        String allColumns = idColumn + ", " + columns;
        String createTableQuery = String.format("CREATE TABLE %s (%s)", tableName, allColumns);
        jdbcTemplate.execute(createTableQuery);

        // Generate entity and CRUD classes
        dynamicTableService.generateEntityAndCRUDClasses(tableName, columns);

        return "redirect:/dynamic-table";
    }

    @GetMapping("/dynamicTable")
    public String getDynamicTable(@RequestParam String tableName, Model model) {
        model.addAttribute("entityName", dynamicTableService.capitalize(tableName));
        // You might also want to pass the columns info to dynamically generate fields in the template
        return "dynamic-table";
    }

}
