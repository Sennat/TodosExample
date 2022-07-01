package com.example.todoappexample;

import java.util.List;

public interface DataRepository {
    boolean isConnected();
    List<Item> getItems();
    Item getItem(String name);
}
