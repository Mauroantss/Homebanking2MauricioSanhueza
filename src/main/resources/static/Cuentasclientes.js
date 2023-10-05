const listClientsApp = Vue.createApp({
  data() {
      return {
          clients: [],
          selectedClientAccounts: [],
          selectedClientName: ''
      };
  },
  mounted() {
      this.fetchClients();
  },
  methods: {
      fetchClients() {
          fetch('http://localhost:8080/api/clients')
              .then(response => response.json())
              .then(data => {
                  this.clients = data;
              })
              .catch(error => {
                  console.error('Error al obtener clientes:', error);
              });
      },
      selectClient(clientId) {
        fetch(`http://localhost:8080/api/clients/${clientId}`) // Asume que esta es la URL correcta
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                this.selectedClientAccounts = data.accounts; // Asume que "accounts" es un campo directo en la respuesta
                const client = this.clients.find(c => c.id === clientId);
                this.selectedClientName = `${client.firstName} ${client.lastName}`;
            })
            .catch(error => {
                console.error('Error al obtener cuentas:', error);
              });
      }
  }
}).mount('#create-client-app');
