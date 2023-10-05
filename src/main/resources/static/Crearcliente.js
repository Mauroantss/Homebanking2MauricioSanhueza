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
      fetch('http://localhost:8080/api/clients')
        .then(response => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(data => {
          this.clients = data; // Si la respuesta es un array directo de clientes
        })
        .catch(error => {
          console.error('Error al obtener clientes:', error);
        });
    },
    addClient() {
      axios.post('http://localhost:8080/api/clients', this.newClient)
        .then(response => {
          // Reiniciamos los campos del formulario para el próximo ingreso
          this.newClient.firstName = '';
          this.newClient.lastName = '';
          this.newClient.email = '';
          this.fetchClients(); // Actualizamos la lista de clientes
        })
        .catch(error => {
          console.error('Error al añadir cliente:', error);
        });
    }}
}).mount('#create-client-app');
