package com.ajierro.v1.ingredients;

import com.ajierro.domain.Ingredients;
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
public class IngredientsRepositoryImp implements IngredientsRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final ApplicationConfiguration applicationConfiguration;

    public IngredientsRepositoryImp(@CurrentSession EntityManager entityManager,
                                    ApplicationConfiguration applicationConfiguration) {
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ingredients> findById(@NotNull Long id) {
        return Optional.ofNullable(entityManager.find(Ingredients.class, id));
    }

    @Override
    @Transactional
    public Ingredients save(@NotBlank String name, @NotBlank String type) {
        Ingredients ingredient = new Ingredients(name, type, new Date(), new Date());
        entityManager.persist(ingredient);
        return ingredient;
    }

    @Override
    @Transactional
    public void deleteById(@NotNull Long id) {
        findById(id).ifPresent(genre -> entityManager.remove(genre));
    }

    private final static List<String> VALID_PROPERTY_NAMES = Arrays.asList("id", "name");

    @Transactional(readOnly = true)
    public List<Ingredients> findAll(@NotNull SortingAndOrderArguments args) {
        String qlString = "SELECT g FROM Ingredients as g";
        if (args != null && args.getOrder().isPresent() && args.getSort().isPresent() && VALID_PROPERTY_NAMES.contains(args.getSort().get())) {
            qlString += " ORDER BY g." + args.getSort().get() + " " + args.getOrder().get().toLowerCase();
        }
        TypedQuery<Ingredients> query = entityManager.createQuery(qlString, Ingredients.class);
        if (args != null) {
            query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
            args.getOffset().ifPresent(query::setFirstResult);
        }

        return query.getResultList();
    }

    @Override
    @Transactional
    public int update(@NotNull Long id, String name, String type) {
        if (name != null && type != null) {
            return entityManager.createQuery("UPDATE Ingredients g SET name = :name, type = :type, updated = NOW() where id = :id AND deleted IS NULL")
                    .setParameter("name", name)
                    .setParameter("type", type)
                    .setParameter("id", id)
                    .executeUpdate();
        } else if (name != null) {
            return entityManager.createQuery("UPDATE Ingredients g SET name = :name, updated = NOW() where id = :id AND deleted IS NULL")
                    .setParameter("name", name)
                    .setParameter("id", id)
                    .executeUpdate();
        } else {
            return entityManager.createQuery("UPDATE Ingredients g SET type = :type, updated = NOW() where id = :id AND deleted IS NULL")
                    .setParameter("type", type)
                    .setParameter("id", id)
                    .executeUpdate();
        }
    }
}
