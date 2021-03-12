package com.wikischool.wikischool.main.Models.Interfaces;

import com.wikischool.wikischool.main.utilities.StudentAttributeIndex;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Interface specifying generic methods services will handle.
 * @author sean-harnett
 * @param <ModelType> Object type interface is implemented to handle
 * @param <AttributesEnum> The type of Enum that will be used to access attributes
 */
public interface Standard_Service_Operations<ModelType, AttributesEnum> {

    public boolean InsertIntoDataBase(ModelType entity);
    public ModelType create(Map<AttributesEnum, Object> entityAttributes);
    public boolean createAndInsertEntity(Map<AttributesEnum, Object> entityAttributes);
    public boolean deleteById(UUID id);
    public List<ModelType> retrieveAll();

}
