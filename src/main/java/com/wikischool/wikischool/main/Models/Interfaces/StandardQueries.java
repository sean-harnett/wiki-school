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

    void insert(SqlQueryInformation<K> queryInformation);

    void update(SqlQueryInformation<K> queryInformation);

    void delete(SqlQueryInformation<K> queryInformation);

    void retrieve(SqlQueryInformation<K> queryInformation);

}
