package com.techkingsley.newsappcleanarchitecture.business.domain.util

interface EntityMapper<Entity, DomainModel> {
    /* An Domain is what we save to the Db and Entity is what we get from the server*/

    fun mapFromEntity(category: String, entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity
}

interface ModelMapper<Entity, Model> {

    fun mapFromEntity(entity: Entity): Model

    fun mapToEntity(model: Model): Entity
}