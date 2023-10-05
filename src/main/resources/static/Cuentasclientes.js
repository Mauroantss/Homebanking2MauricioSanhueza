const app = Vue.createApp({
    data() {
        return {
            clients: [], // Almacenará los datos de los clientes
            selectedClientId: null, // Almacenará el ID del cliente seleccionado
            accounts: [] // Almacenará las cuentas del cliente seleccionado
        };
    },
    methods: {
        loadAccounts() {
            // Verificamos si se ha seleccionado un cliente
            if (!this.selectedClientId) {
                return;
            }

            // Realizar una solicitud para obtener las cuentas del cliente seleccionado
            const accountsUrl = `http://localhost:8080/accounts?clientId=${this.selectedClientId}`;
            
            fetch(accountsUrl)
              .then(response => {
                if (response.status === 200) {
                  return response.json();
                } else {
                  throw new Error('No se pudo obtener los datos de las cuentas');
                }
              })
              .then(accounts => {
                this.accounts = accounts; // Actualizar las cuentas en Vue
              })
              .catch(error => {
                console.error('Error al obtener las cuentas:', error);
              });
        }
    },
    mounted() {
        // Realizar una solicitud para obtener los IDs de los clientes
        const clientsUrl = 'http://localhost:8080/api/clients';

        fetch(clientsUrl)
          .then(response => {
            if (response.status === 200) {
              return response.json();
            } else {
              throw new Error('No se pudo obtener los IDs de los clientes');
            }
          })
          .then(clients => {
            this.clients = clients; // Actualizar los clientes en Vue
          })
          .catch(error => {
            console.error('Error al obtener los IDs de los clientes:', error);
          });
    }
});

// Montamos la aplicación en el elemento con id "app"
app.mount('#app');