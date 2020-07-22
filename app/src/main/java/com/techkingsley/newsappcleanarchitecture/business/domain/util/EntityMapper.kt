package com.techkingsley.newsappcleanarchitecture.business.domain.util

interface EntityMapper<Entity, DomainModel> {
    /* An Domain is what we save to the Db and Entity is what we get from the server*/

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity
}