package com.techkingsley.cache.mappers.base

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer data source layers
 *
 * @param <T> the cached model input type
 * @param <T> the remote model input type
 * @param <V> the model return type
 */
interface NewsEntityMapper<T, V> {

    fun mapFromCached(category: String, type: T): V

    fun mapToCached(category: String, entity: V): T
}