const createClientApp = Vue.createApp({
  data() {
    return {
      clients: [],
      newClient: {
        firstName: '',
        lastName: '',
        email: ''
      }
    };
  },
  mounted() {
    this.fetchClients(); // Inicialmente cargamos la lista de clientes
  },
  methods: {
    fetchClients() {
      fetch('http://localhost:8080/rest/clients/')
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          // Accedemos a la clave '_embedded' y luego a la clave 'clients'
          this.clients = data._embedded.clients;
        })
        .catch(error => {
          console.error('Error al obtener clientes:', error);
        });
    },
    
    addClient() {
      axios.post('http://localhost:8080/rest/clients/', this.newClient)
        .then(response => {
          // Reiniciamos los campos del formulario para el próximo ingreso
          this.newClient.firstName = '';
          this.newClient.lastName = '';
          this.newClient.email = '';
        })
        .then(() => {
          // Luego de reiniciar los campos, actualizamos la lista de clientes
          return this.fetchClients();
        })
        .catch(error => {
          console.error('Error al añadir cliente:', error);
        });
    }
    
  } 
}).mount('#app'); 
