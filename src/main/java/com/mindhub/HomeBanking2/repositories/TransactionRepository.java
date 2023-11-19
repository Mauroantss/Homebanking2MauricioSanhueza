package com.mindhub.HomeBanking2.repositories;

import com.mindhub.HomeBanking2.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

//En este código:
//
//Estamos creando una interfaz llamada TransactionRepository.
//Extiende JpaRepository<Transaction, Long>, lo que significa que hereda las operaciones estándar de un repositorio de
// Spring Data JPA para la entidad Transaction. Estas operaciones incluyen la creación, lectura, actualización y eliminación de registros en la base de datos.
//La entidad Transaction se especifica como el tipo de entidad que este repositorio manejará, y Long se utiliza como el tipo de clave primaria para las transacciones.
//En resumen, esta interfaz de repositorio proporciona métodos para acceder y gestionar objetos de tipo
// Transaction en la base de datos utilizando las operaciones CRUD proporcionadas por
// Spring Data JPA. Sin embargo, este repositorio no se expone automáticamente como un punto final REST;
// eso debería implementarse en un controlador personalizado si se necesita una API web para las transacciones.