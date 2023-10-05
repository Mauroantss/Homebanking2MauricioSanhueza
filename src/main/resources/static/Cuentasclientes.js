// Definir la URL de la API
const apiUrl = 'http://localhost:8080/api/clients';

// Función para crear una tarjeta de cuenta
function createAccountCard(clientId, account) {
    const card = document.createElement('div');
    card.className = 'col-md-6 mb-4';

    card.innerHTML = `
        <div class="card">
            <div class="card-body">
                <h2 class="card-title">Cuenta de ${account.tipo}</h2>
                <p class="card-text">Número de Cuenta: ${account.numero}</p>
                <p class="card-text">Saldo Disponible: $${account.saldo}</p>
            </div>
        </div>
    `;

    return card;
}

// Realizar la solicitud GET utilizando fetch
fetch(apiUrl)
  .then(response => {
    // Verificar si la respuesta es exitosa (código de estado 200)
    if (response.status === 200) {
      return response.json(); // Parsear la respuesta JSON
    } else {
      throw new Error('No se pudo obtener los datos de la API');
    }
  })
  .then(data => {
    // Manejar los datos obtenidos de la API
    console.log('Datos de clientes:', data);

    // Obtener el contenedor principal en el HTML
    const mainContainer = document.getElementById('main-container');

    // Iterar a través de los clientes y sus cuentas
    data.forEach(client => {
        client.account.forEach(account => {
            // Crear una tarjeta de cuenta asociada al cliente
            const card = createAccountCard(client.id, account);

            // Agregar la tarjeta al contenedor principal
            mainContainer.appendChild(card);
        });
    });
  })
  .catch(error => {
    // Manejar errores de la solicitud
    console.error('Error:', error);
  });
