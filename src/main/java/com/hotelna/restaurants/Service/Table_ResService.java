package com.hotelna.restaurants.Service;

import com.hotelna.restaurants.Entity.Status;
import com.hotelna.restaurants.Entity.Table_Res;
import com.hotelna.restaurants.Repository.Table_ResRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Table_ResService implements ITable_ResService {
    @Autowired
    private Table_ResRepository repository;

    @Override
    public Table_Res createTableRes(Table_Res tableRes) {
        try {
            if (tableRes == null) {
                throw new IllegalArgumentException("Table_Res object is null");
            }
            return repository.save(tableRes);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error while creating Table_Res: " + e.getMessage());
            throw new RuntimeException("Failed to create Table_Res", e);
        }
    }

    @Override
    public Table_Res updateTableRes(Table_Res tableRes) {
        try {
            if (tableRes == null) {
                throw new IllegalArgumentException("Table_Res object is null");
            }
            if (!repository.existsById(tableRes.getId())) {
                throw new RuntimeException("Table_Res not found with ID: " + tableRes.getId());
            }
            return repository.save(tableRes);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update Table_Res", e);
        }
    }

    @Override
    public void deleteTableRes(int id) {
        try {
            if (!repository.existsById(id)) {
                throw new RuntimeException("Table_Res not found with ID: " + id);
            }
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete Table_Res", e);
        }
    }

    @Override
    public Table_Res getTableResById(int id) {
        try {
            if (!repository.existsById(id)) {
                throw new RuntimeException("Table_Res not found with ID: " + id);
            }
            return repository.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve Table_Res", e);
        }
    }

    @Override
    public List<Table_Res> getAllTableRes() {
        return repository.findAll();
    }

    @Override
    public Table_Res reserveTable(int id) {
        try {
            Table_Res tableRes = repository.findById(id).orElseThrow(() ->
                    new RuntimeException("Table_Res not found with ID: " + id));
            if (tableRes.getRestaurant().getStatut() == Status.CLOSED) {
                throw new RuntimeException("Restaurant is closed. Cannot reserve the table.");
            }
            if (!tableRes.getDisponibilite()) {
                throw new RuntimeException("Table is already reserved");
            }
            tableRes.setDisponibilite(false);
            return repository.save(tableRes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reserve table", e);
        }
    }
}
