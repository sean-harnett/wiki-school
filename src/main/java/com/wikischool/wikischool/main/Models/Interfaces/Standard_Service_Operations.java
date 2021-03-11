package com.wikischool.wikischool.main.Models.Interfaces;

import com.wikischool.wikischool.main.utilities.StudentAttributeIndex;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Standard_Service_Operations<ModelType, AttributeType> { //General implementation for services

    public boolean InsertIntoDataBase(ModelType entity);
    public ModelType create(Map<AttributeType, Object> entityAttributes);
    public boolean createAndInsertEntity(Map<AttributeType, Object> entityAttributes);
    public boolean deleteById(UUID id);
    public List<ModelType> retrieveAll();

}
