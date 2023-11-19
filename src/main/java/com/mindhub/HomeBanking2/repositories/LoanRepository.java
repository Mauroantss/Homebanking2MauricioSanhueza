package com.mindhub.HomeBanking2.repositories;

import com.mindhub.HomeBanking2.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {

    // Método que encuentra un préstamo por su ID.
    Loan findById(long id);

    // Método que verifica si existe un préstamo con el nombre proporcionado.
    boolean existsByName(String name);
}

//Esta interfaz extiende JpaRepository<Loan, Long>,
// lo que significa que hereda las operaciones CRUD (Crear, Leer, Actualizar, Eliminar) de Spring Data JPA para la entidad Loan.
//La anotación @RepositoryRestResource se utiliza para exponer este repositorio como un punto final REST.
// Esto significa que las operaciones CRUD para la entidad Loan estarán disponibles a través de servicios web RESTful de forma automática.
//La entidad Loan se especifica como el tipo de entidad manejado por este repositorio, y Long se utiliza como el tipo de la clave primaria de la entidad.
//En resumen, esta interfaz de repositorio permite acceder y gestionar objetos de tipo
// Loan en la base de datos utilizando las operaciones CRUD proporcionadas por
// Spring Data JPA y expone estos recursos a través de un punto final REST para su uso en una API web.