package com.wikischool.wikischool.main.Models.Interfaces;


import com.wikischool.wikischool.main.Queries.SqlQueryInformation;

import java.util.Map;
import java.util.UUID;

/**
 * Interface to implement standard queries used to interact with records in a database.
 * For examples of AttributeIndexEnum types:
 * @see com.wikischool.wikischool.main.utilities.StudentAttributeIndex
 * @see com.wikischool.wikischool.main.utilities.NoteAttributeIndex
 *
 *
 * @author sean-harnett
 */
public interface StandardQueries<K> {

   public void insert(SqlQueryInformation<K> queryInformation);

    public void update(SqlQueryInformation<K> queryInformation);

    public void delete(SqlQueryInformation<K> queryInformation);

    public void retrieve(SqlQueryInformation<K> queryInformation);

}
