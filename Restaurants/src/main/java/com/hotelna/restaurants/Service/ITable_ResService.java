package com.hotelna.restaurants.Service;

import com.hotelna.restaurants.Entity.Table_Res;

import java.util.List;

public interface ITable_ResService {
    Table_Res createTableRes(Table_Res tableRes);
    Table_Res updateTableRes(Table_Res tableRes);
    void deleteTableRes(int id);
    List<Table_Res> getAllTableRes();
    Table_Res getTableResById(int id);
    Table_Res reserveTable(int id, int userId);
    List<Table_Res> getTablesByRestaurantId(int restaurantId);
}
