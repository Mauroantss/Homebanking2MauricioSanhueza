const listClientsApp = Vue.createApp({
    data() {
        return {
            clients: [],
            selectedClientAccounts: [],
            selectedClientName: '',
            selectedClientLoans: [],
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
        
        fetchClientLoans(clientId) {
            fetch(`http://localhost:8080/rest/clients/${clientId}/clientLoans`)
                .then(response => response.json())
                .then(data => {
                    this.selectedClientLoans = data["_embedded"]["clientLoans"];
                })
                .catch(error => {
                    console.error('Error al obtener préstamos del cliente:', error);
                });
        },
        
        selectClient(clientId) {
          fetch(`http://localhost:8080/api/clients/${clientId}`) 
              .then(response => {
                  if (!response.ok) {
                      throw new Error('Network response was not ok');
                  }
                  return response.json();
              })
              .then(data => {
                  this.selectedClientAccounts = data.accounts; 
                  const client = this.clients.find(c => c.id === clientId);
                  this.selectedClientName = `${client.firstName} ${client.lastName}`;
                  
                  // Llama al método para obtener los préstamos del cliente
                  this.fetchClientLoans(clientId);
              })
              .catch(error => {
                  console.error('Error al obtener cuentas:', error);
              });
        }
    }
  }).mount('#create-client-app');
  