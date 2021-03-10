package com.wikischool.wikischool.main.Models.Interfaces;

import java.util.List;
import java.util.UUID;

public interface Standard_Service_Operations<T> { // Each implementation decides about cache

    //No create, because that is dependant on T values as arguments

    public boolean deleteById(UUID id);
    public boolean updateById(UUID id, T entity); // what is better way to do this..?
    public List<T> retrieveAll();

} //Will be more added, findById - doesEntryExist etc...
