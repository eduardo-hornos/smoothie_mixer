package com.ajierro.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "Ingredients")
@Where(clause = "deleted IS NULL")
@SQLDelete(sql = "UPDATE Ingredients " +
                "SET deleted = NOW() " +
                "WHERE id = ?")
public class Ingredients {

    public Ingredients() {}

    public Ingredients(@NotNull String name, @NotNull String description, @NotNull Date created, @NotNull Date updated) {
        this.name = name;
        this.description = description;
        this.created = created;
        this.updated = updated;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "updated", nullable = false)
    private Date updated;

    @Column(name = "deleted")
    private Date deleted;

    //@JsonIgnore
    //@OneToMany(mappedBy = "genre")
    //private Set<Book> books = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", deleted='" + deleted + '\'' +
                '}';
    }

}
