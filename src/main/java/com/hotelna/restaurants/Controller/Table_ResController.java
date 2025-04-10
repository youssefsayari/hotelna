package com.hotelna.restaurants.Controller;

import com.hotelna.restaurants.Entity.Table_Res;
import com.hotelna.restaurants.Service.Table_ResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/tables")
public class Table_ResController {
    @Autowired
    Table_ResService tableResService;

    @PostMapping("/add-table")
    public ResponseEntity<Table_Res> addTable(@RequestBody Table_Res tableRes) {
        Table_Res savedTable = tableResService.createTableRes(tableRes);
        return ResponseEntity.ok(savedTable);
    }

    @GetMapping("/retrieve-all-tables")
    public ResponseEntity<List<Table_Res>> getAllTables() {
        List<Table_Res> tables = tableResService.getAllTableRes();
        if (tables.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tables);
    }

    @PutMapping("/update-table")
    public ResponseEntity<Table_Res> updateTable(@RequestBody Table_Res tableRes) {
        Table_Res updatedTable = tableResService.updateTableRes(tableRes);
        if (updatedTable == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedTable);
    }

    @DeleteMapping("/remove-table/{table-id}")
    public ResponseEntity<Void> deleteTable(@PathVariable("table-id") int id) {
        try {
            tableResService.deleteTableRes(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/retrieve-table/{table-id}")
    public ResponseEntity<Table_Res> getTable(@PathVariable("table-id") int id) {
        try {
            Table_Res tableRes = tableResService.getTableResById(id);
            return ResponseEntity.ok(tableRes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reserve-table/{table-id}")
    public ResponseEntity<Table_Res> reserveTable(@PathVariable("table-id") int id) {
        try {
            Table_Res reservedTable = tableResService.reserveTable(id);
            return ResponseEntity.ok(reservedTable);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
