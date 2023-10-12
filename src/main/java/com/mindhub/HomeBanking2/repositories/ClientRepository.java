package com.mindhub.HomeBanking2.repositories;

import com.mindhub.HomeBanking2.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
    }
    //La interfaz creada hereda con "extends" los metodos de JpaRepository, entre los diamantes tengo que
    // poner la clase y el tipo de dato del id
//Esta interfaz extiende JpaRepository<Client, Long>, lo que significa que hereda las operaciones
// CRUD (Crear, Leer, Actualizar, Eliminar) de Spring Data JPA para la entidad Client.
//La anotación @RepositoryRestResource se utiliza para exponer este repositorio como un punto final REST.
// Esto significa que las operaciones CRUD para la entidad Client estarán disponibles a través de servicios web RESTful de forma automática.
//La entidad Client se especifica como el tipo de entidad manejado por este repositorio, y Long se utiliza como el tipo de la clave primaria de la entidad.
//En resumen, esta interfaz de repositorio permite acceder y gestionar objetos de tipo Client en la base de datos utilizando las operaciones
// CRUD proporcionadas por Spring Data JPA y expone estos recursos a través de un punto final REST para su uso en una API web.