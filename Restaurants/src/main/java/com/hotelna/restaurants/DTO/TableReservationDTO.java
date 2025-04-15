package com.hotelna.restaurants.DTO;

public class TableReservationDTO {
    private int tableId;
    private int userId;

    // Constructeurs, getters et setters

    public TableReservationDTO(int tableId, int userId) {
        this.tableId = tableId;
        this.userId = userId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

