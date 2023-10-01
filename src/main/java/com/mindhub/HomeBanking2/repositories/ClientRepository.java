// Importamos las clases y anotaciones necesarias
package com.mindhub.HomeBanking2.repositories;

import com.mindhub.HomeBanking2.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// Anotación para indicar que esta interfaz es un repositorio RESTful
@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Esta interfaz extiende JpaRepository, lo que significa que hereda una serie de métodos
    // para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) en la tabla Client.
    // El primer parámetro indica la entidad para la cual se creará el repositorio (Client) y
    // el segundo parámetro (Long) indica el tipo de la clave primaria (ID) de dicha entidad.

    // Al extender JpaRepository, este repositorio ya tiene muchos métodos útiles para el manejo de
    // datos sin necesidad de que implementemos nada más.
    // La anotación @RepositoryRestResource hace que se publique automáticamente un API REST
    // para acceder al repositorio.
}
