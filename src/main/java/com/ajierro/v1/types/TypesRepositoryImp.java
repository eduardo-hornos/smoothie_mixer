package com.ajierro.v1.types;

import com.ajierro.domain.Types;
import com.ajierro.v1.utils.ApplicationConfiguration;
import com.ajierro.v1.utils.SortingAndOrderArguments;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Singleton
public class TypesRepositoryImp implements TypesRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final ApplicationConfiguration applicationConfiguration;

    public TypesRepositoryImp(@CurrentSession EntityManager entityManager,
                               ApplicationConfiguration applicationConfiguration) {
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Types> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Types.class, id));
    }

    @Override
    @Transactional
    public Types save(@NotBlank String name, @NotBlank String description) {
        Types type = new Types(name, description, new Date(), new Date());
        entityManager.persist(type);
        return type;
    }

    @Override
    @Transactional
    public void deleteById(@NotNull Long id) {
        findById(id).ifPresent(genre -> entityManager.remove(genre));
    }

    private final static List<String> VALID_PROPERTY_NAMES = Arrays.asList("id", "name");

    @Transactional(readOnly = true)
    public List<Types> findAll(@NotNull SortingAndOrderArguments args) {
        String qlString = "SELECT g FROM Types as g";
        if (args != null && args.getOrder().isPresent() && args.getSort().isPresent() && VALID_PROPERTY_NAMES.contains(args.getSort().get())) {
            qlString += " ORDER BY g." + args.getSort().get() + " " + args.getOrder().get().toLowerCase();
        }
        TypedQuery<Types> query = entityManager.createQuery(qlString, Types.class);
        if (args != null) {
            query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
            args.getOffset().ifPresent(query::setFirstResult);
        }

        return query.getResultList();
    }

    @Override
    @Transactional
    public int update(@NotNull Long id, String name, String description) {
        if (name != null && description != null) {
            return entityManager.createQuery("UPDATE Types g SET name = :name, description = :description, updated = NOW() where id = :id AND deleted IS NULL")
                    .setParameter("name", name)
                    .setParameter("description", description)
                    .setParameter("id", id)
                    .executeUpdate();
        } else if (name != null) {
            return entityManager.createQuery("UPDATE Types g SET name = :name, updated = NOW() where id = :id AND deleted IS NULL")
                    .setParameter("name", name)
                    .setParameter("id", id)
                    .executeUpdate();
        } else {
            return entityManager.createQuery("UPDATE Types g SET description = :description, updated = NOW() where id = :id AND deleted IS NULL")
                    .setParameter("description", description)
                    .setParameter("id", id)
                    .executeUpdate();
        }
    }
}
