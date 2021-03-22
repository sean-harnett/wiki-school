package com.wikischool.wikischool.main.Models.Interfaces;


import com.wikischool.wikischool.main.Queries.SqlQueryInformation;
import com.wikischool.wikischool.main.utilities.EnumIndices.NoteAttributeIndex;
import com.wikischool.wikischool.main.utilities.EnumIndices.StudentAttributeIndex;

/**
 * Interface to implement standard queries used to interact with records in a database.
 * For examples of AttributeIndexEnum types:
 * @see StudentAttributeIndex
 * @see NoteAttributeIndex
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
