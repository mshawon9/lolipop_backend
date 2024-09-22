package com.lolipop.pos.service.impl;

import com.lolipop.pos.service.DynamicTableService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamicTableServiceImpl implements DynamicTableService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, String>> getTableColumns(String tableName) {
        String query = String.format("SHOW COLUMNS FROM %s", tableName);
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Map<String, String> columnInfo = new HashMap<>();
            columnInfo.put("name", rs.getString("Field"));
            columnInfo.put("type", convertSQLTypeToJsGridType(rs.getString("Type")));
            return columnInfo;
        });
    }

    private String convertSQLTypeToJsGridType(String sqlType) {
        if (sqlType.startsWith("varchar") || sqlType.startsWith("text")) {
            return "text";
        } else if (sqlType.startsWith("int") || sqlType.startsWith("bigint")) {
            return "number";
        }
        // Add more SQL to jsGrid type conversions as needed
        return "text";
    }

    @Transactional
    public void generateEntityAndCRUDClasses(String tableName, String columns) {
        // Split columns to get field names and types
        String[] columnArray = columns.split(",");
        StringBuilder fieldsBuilder = new StringBuilder();
        for (String column : columnArray) {
            String[] parts = column.trim().split(" ");
            fieldsBuilder.append("private ").append(convertSQLTypeToJavaType(parts[1])).append(" ").append(parts[0]).append(";\n");
        }
        String fields = fieldsBuilder.toString();
        File file = new File("/src/main/java/com/lolipop/pos/");

        Path projectBaseDir = Paths.get("").toAbsolutePath();
        String baseDir = projectBaseDir + "/pos/src/main/java/com/lolipop/pos/";
        // Generate entity class
        //String baseDir = "/src/main/java/com/lolipop/pos/";
        // Generate entity class
        String entityTemplate = "package com.lolipop.pos.entity;\n" +
                "import jakarta.persistence.*;\n" +
                "import lombok.Data;\n" +
                "@Entity\n" +
                "@Table(name = \""+ tableName.toLowerCase() + "\")\n" +
                "@Data\n" +
                "public class " + capitalize(tableName) + "Entity {\n" +
                "@Id\n" +
                "@GeneratedValue(strategy = GenerationType.IDENTITY)\n" +
                "private Long id;\n" +
                fields +
                "}";
        writeFile(baseDir + "entity/" + capitalize(tableName) + "Entity.java", entityTemplate);

        // Generate repository class
        String repositoryTemplate = "package com.lolipop.pos.repository;\n" +
                "import com.lolipop.pos.entity." + capitalize(tableName) + "Entity;\n" +
                "import org.springframework.data.jpa.repository.JpaRepository;\n" +
                "public interface " + capitalize(tableName) + "Repository extends JpaRepository<" + capitalize(tableName) + "Entity, Long> {}";
        writeFile(baseDir + "repository/" + capitalize(tableName) + "Repository.java", repositoryTemplate);

        // Generate service interface
        String serviceTemplate = "package com.lolipop.pos.service;\n" +
                "import com.lolipop.pos.entity." + capitalize(tableName) + "Entity;\n" +
                "import java.util.List;\n\n" +
                "public interface " + capitalize(tableName) + "Service {\n" +
                "public List<" + capitalize(tableName) + "Entity> getAll(); \n" +
                "public " + capitalize(tableName) + "Entity save(" + capitalize(tableName) + "Entity entity);\n" +
                "public void delete(Long id);\n" +
                "}";
        writeFile(baseDir + "service/" + capitalize(tableName) + "Service.java", serviceTemplate);

        // Generate service Impl class
        String serviceImplTemplate = "package com.lolipop.pos.service.impl;\n" +
                "import com.lolipop.pos.entity." + capitalize(tableName) + "Entity;\n" +
                "import com.lolipop.pos.repository." + capitalize(tableName) + "Repository;\n" +
                "import com.lolipop.pos.service." + capitalize(tableName) + "Service;\n" +
                "import lombok.RequiredArgsConstructor;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "import java.util.List;\n\n\n" +
                "@Service\n" +
                "@RequiredArgsConstructor\n" +
                "public class " + capitalize(tableName) + "ServiceImpl implements " + capitalize(tableName) + "Service {\n" +
                "private final " + capitalize(tableName) + "Repository repository;\n\n" +
                "@Override\n"+
                "public List<" + capitalize(tableName) + "Entity> getAll() { return repository.findAll(); }\n\n" +
                "@Override\n"+
                "public " + capitalize(tableName) + "Entity save(" + capitalize(tableName) + "Entity entity) { return repository.save(entity); }\n\n" +
                "@Override\n"+
                "public void delete(Long id) { repository.deleteById(id); }\n" +
                "}";
        writeFile(baseDir + "service/impl/" + capitalize(tableName) + "ServiceImpl.java", serviceImplTemplate);

        // Generate controller class
        String controllerTemplate = "package com.lolipop.pos.controller;\n" +
                "import com.lolipop.pos.entity." + capitalize(tableName) + "Entity;\n" +
                "import com.lolipop.pos.service." + capitalize(tableName) + "Service;\n" +
                "import lombok.RequiredArgsConstructor;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "import java.util.List;\n\n" +
                "@RestController\n" +
                "@RequiredArgsConstructor\n" +
                "@RequestMapping(\"/api/" + tableName.toLowerCase() + "\")\n" +
                "public class " + capitalize(tableName) + "Controller {\n" +
                "private final " + capitalize(tableName) + "Service service;\n\n" +
                "@GetMapping\n" +
                "public List<" + capitalize(tableName) + "Entity> getAll() { return service.getAll(); }\n\n" +
                "@PostMapping\n" +
                "public " + capitalize(tableName) + "Entity create(@RequestBody " + capitalize(tableName) + "Entity entity) { return service.save(entity); }\n\n" +
                "@PutMapping(\"/{id}\")\n" +
                "public " + capitalize(tableName) + "Entity update(@PathVariable Long id, @RequestBody " + capitalize(tableName) + "Entity entity) { entity.setId(id); return service.save(entity); }\n\n" +
                "@DeleteMapping(\"/{id}\")\n" +
                "public void delete(@PathVariable Long id) { service.delete(id); }\n" +
                "}";
        writeFile(baseDir + "controller/" + capitalize(tableName) + "Controller.java", controllerTemplate);

    }

    public String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private String convertSQLTypeToJavaType(String sqlType) {
        sqlType = sqlType.toLowerCase().split("\\(")[0];
        return switch (sqlType.toLowerCase()) {
            case "varchar", "text" -> "String";
            case "int", "integer" -> "Integer";
            case "bigint" -> "Long";
            case "boolean" -> "Boolean";
            case "float" -> "Float";
            case "double" -> "Double";
            default -> throw new IllegalArgumentException("Unknown SQL type: " + sqlType);
        };
    }

    /*private void writeFile(String filePath, String content) {
        File file = new File(filePath);
        file.getParentFile().mkdirs(); // Create directories if they don't exist

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void writeFile(String filePath, String content) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
