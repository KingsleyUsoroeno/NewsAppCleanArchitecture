package com.techkingsley.data.mapper

interface EntityMapper<T, V> {

    fun mapFromDomain(type: T): V

    fun mapToDomain(type: V): T

    fun mapToDomainList(models: List<V>): List<T> {
        return models.mapTo(mutableListOf(), ::mapToDomain)
    }

    fun mapFromDomainList(entities: List<T>): List<V> {
        return entities.mapTo(mutableListOf(), ::mapFromDomain)
    }
}