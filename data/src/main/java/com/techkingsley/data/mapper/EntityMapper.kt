package com.techkingsley.data.mapper

interface EntityMapper<T, V> {

    fun mapFromDomain(type: T): V

    fun mapToDomain(type: V): T
}