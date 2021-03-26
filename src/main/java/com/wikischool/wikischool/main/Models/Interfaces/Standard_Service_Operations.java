package com.wikischool.wikischool.main.Models.Interfaces;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Interface specifying generic methods services will handle.
 * @author sean-harnett
 * @param <ModelType> The type the interface is implemented to handle
 * @param <AttributesEnum> The type of Enum that will be used to access attributes
 */
public interface Standard_Service_Operations<ModelType, AttributesEnum> {

     boolean InsertIntoDataBase(ModelType entity);
     ModelType create(Map<AttributesEnum, Object> entityAttributes);
     boolean createAndInsertEntity(Map<AttributesEnum, Object> entityAttributes);
     boolean deleteById(UUID id);
     List<ModelType> retrieveAll();

}
