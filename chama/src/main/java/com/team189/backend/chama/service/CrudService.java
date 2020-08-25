package com.team189.backend.chama.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

/**
 * Definition of crud service utilities.
 *
 * @author Mohas
 */
@Component
public interface CrudService {

    /**
     * Default query results page when none is specified.
     */
    int DEFAULT_PAGE_SIZE = 1000000;

    /**
     * Retrieve an entity using its primary key.
     *
     * @param <T> Entity type
     * @param primaryKey primary id value
     * @param clazz entity class.
     * @return object instance if found, null if none-found.
     */
    <T> T findEntity(Serializable primaryKey, Class<T> clazz) throws HibernateException;

    /**
     * Execute hibernate query.
     *
     * @param queryString Named query defined on the entity.
     * @param params map pair of param name which is simply the field name of the entity and a
     * search value. The param map maybe empty.
     * @return List of results or empty list if none is found.
     */
    int executeHibernateQuery(String queryString, Map<String, Object> params) throws HibernateException;

    /**
     * Find all results of executing a given named query with given params. Each parameter is
     * substituted in the query.
     *
     * @param <T> Actual return type.
     * @param queryName Named query defined on the entity.
     * @param params map pair of param name which is simply the field name of the entity and a
     * search value. The param map maybe empty.
     * @return List of results or empty list if none is found.
     */
    <T> List<T> fetchWithNamedQuery(String queryName, Map<String, Object> params) throws HibernateException;

    /**
     * Find all results of executing a given hibernate query with given params. Each parameter is
     * substituted in the query.
     *
     * @param <T> Actual return type.
     * @param query Hibernate query constructed to be substituted with the actual params below.
     * @param params map pair of param name which is simply the field name of the entity and a
     * search value. The param map maybe empty.
     * @return List of results or empty list if none is found.
     */
    <T> List<T> fetchWithHibernateQuery(String query, Map<String, Object> params) throws HibernateException;

    /**
     * Find all results of executing a given hibernate query with given params. Each parameter is
     * substituted in the query.
     *
     * @param <T> Actual return type.
     * @param query Hibernate query constructed to be substituted with the actual params below.
     * @param params map pair of param name which is simply the field name of the entity and a
     * search value. The param map maybe empty.
     * @return List of results or empty list if none is found.
     */
    <T> T fetchWithHibernateQuerySingleResult(String query, Map<String, Object> params) throws HibernateException;

    /**
     * Find all results of executing a given hibernate query with given params. Each parameter is
     * substituted in the query.
     *
     * @param <T> Actual return type.
     * @param query Hibernate query constructed to be substituted with the actual params below.
     * @param start
     * @param params map pair of param name which is simply the field name of the entity and a
     * search value. The param map maybe empty.
     * @param end
     * @return List of results or empty list if none is found.
     */
    <T> List<T> fetchWithNativeQuery(String query, Map<String, Object> params, int start, int end) throws HibernateException;

    /**
     * Find paged results of executing a given named query with given params. Each parameter is
     * substituted in the query.
     *
     * @param <T> Actual return type.
     * @param query Hibernate query constructed to be substituted with the actual params below.
     * @param params pair of param name which is simply the field name of the entity and a search
     * value.
     * @param start beginning position, default is 0. First element
     * @param end upto and inclusive
     * @return List of results or empty list if none is found.
     */
    <T> List<T> fetchWithNamedQuery(String query, Map<String, Object> params, int start, int end) throws HibernateException;

    /**
     * Find paged results of executing a given hibernate query with given params. Each parameter is
     * substituted in the query.
     *
     * @param <T> Actual return type.
     * @param query Named query defined on the entity.
     * @param params pair of param name which is simply the field name of the entity and a search
     * value.
     * @param start beginning position, default is 0. First element
     * @param end upto and inclusive
     * @return List of results or empty list if none is found.
     */
    <T> List<T> fetchWithHibernateQuery(String query, Map<String, Object> params, int start, int end) throws HibernateException;

    /**
     * Persists or updates an object to the data store.
     *
     * @param <T> Object type.
     * @param entity value to be added or updated.
     * @throws HibernateException
     */
    <T> void saveOrUpdate(T entity) throws HibernateException;

    /**
     * Persists or updates an object to the data store.
     *
     * @param <T> Object type.
     * @param entity value to be added or updated.
     * @throws HibernateException
     */
    <T> Object save(T entity) throws HibernateException;

    /**
     * Save a list of entities in batch form.
     *
     * @param <T>
     * @param entities
     * @throws HibernateException
     */
    <T> void saveOrUpdate(List<T> entities) throws HibernateException;

    /**
     * Remove an entity permanently from database.
     *
     * @param <T> object type
     * @param entity value to remove
     * @throws HibernateException in case errors occur
     */
    <T> void remove(T entity) throws HibernateException;

    int executeNativeQuery(String queryString, Map<String, Object> params);

    SessionFactory getSessionFactory();
}
