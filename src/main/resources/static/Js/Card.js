const cardApp = Vue.createApp({
    data() {
        return {
            cardData: null,
            clientId:""
        };
    },
    mounted() {
        this.fetchCards();
    },
    methods: {
        fetchCards() {
            fetch('http://localhost:8080/rest/cards')
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Respuesta no exitosa del servidor');
                    }
                    return response.json();
                })
                .then(data => {
                    this.cardData = data._embedded.cards;
                })
                .catch(error => {
                    console.error('Error al obtener las tarjetas:', error);
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
}).mount('#card-app');

