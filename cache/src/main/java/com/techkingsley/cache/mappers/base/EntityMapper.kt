package com.techkingsley.cache.mappers.base

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 *
 * @param <T> the cached model input type
 * @param <V> the data Entity model input type
 */
interface EntityMapper<T, V> {

    fun mapFromCached(cache: T): V

    fun mapToCached(entity: V): T

    fun mapToCacheList(models: List<V>): List<T> {
        return models.mapTo(mutableListOf(), ::mapToCached)
    }

    fun mapToEntityList(entities: List<T>): List<V> {
        return entities.mapTo(mutableListOf(), ::mapFromCached)
    }

}