package com.mindhub.HomeBanking2.repositories;

import com.mindhub.HomeBanking2.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByNumber(String number);
    Account findByNumber(String number);
}

//Esta interfaz extiende JpaRepository<Account, Long>, lo que significa que hereda las operaciones CRUD
// (Crear, Leer, Actualizar, Eliminar) de Spring Data JPA para la entidad Account.
//La anotación @RepositoryRestResource se utiliza para exponer este repositorio como un punto final REST.
// Esto significa que las operaciones CRUD para la entidad Account estarán disponibles a través de servicios web RESTful de forma automática.
//La entidad Account se especifica como el tipo de entidad manejado por este repositorio, y Long se utiliza como el tipo de la clave primaria de la entidad.
//En resumen, esta interfaz de repositorio permite acceder y gestionar objetos de tipo
// Account en la base de datos utilizando las operaciones CRUD proporcionadas por
// Spring Data JPA y expone estos recursos a través de un punto final REST para su uso en una API web.