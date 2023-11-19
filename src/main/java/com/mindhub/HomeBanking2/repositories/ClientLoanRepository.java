package com.mindhub.HomeBanking2.repositories;

import com.mindhub.HomeBanking2.models.Client;
import com.mindhub.HomeBanking2.models.ClientLoan;
import com.mindhub.HomeBanking2.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
}

// Esta interfaz extiende JpaRepository<ClientLoan, Long>, lo que significa que hereda las operaciones CRUD
// (Crear, Leer, Actualizar, Eliminar) de Spring Data JPA para la entidad ClientLoan.
//La anotación @RepositoryRestResource se utiliza para exponer este repositorio como un punto final
// REST. Esto significa que las operaciones CRUD para la entidad ClientLoan estarán disponibles a través de servicios web RESTful de forma automática.
//La entidad ClientLoan se especifica como el tipo de entidad manejado por este repositorio, y Long se utiliza como el tipo de la clave primaria de la entidad.
//En resumen, esta interfaz de repositorio permite acceder y gestionar objetos de tipo
// ClientLoan en la base de datos utilizando las operaciones CRUD proporcionadas por
// Spring Data JPA y expone estos recursos a través de un punto final REST para su uso en una API web.