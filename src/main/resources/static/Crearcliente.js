// Creamos una nueva instancia de la aplicación Vue
const app = Vue.createApp({
  
    // El estado de la aplicación se define en la función 'data'
    data() {
      return {
        clients: [],  // Almacenamos la lista de clientes aquí
        clientIds: [], // Almacenamos los IDs de los clientes aquí
        newClient: {  // Objeto para recoger los datos del nuevo cliente del formulario
          firstName: '',
          lastName: '',
          email: ''
        }
      };
    },
    
    // El hook 'mounted' se ejecuta cuando el componente se monta en el DOM
    mounted() {
      this.fetchClients();  // Llamamos a 'fetchClients' para cargar la lista de clientes
    },
    
    // Métodos que vamos a utilizar en la aplicación
    methods: {
      
      // Método para obtener la lista de clientes del servidor
      fetchClients() {
        fetch('http://localhost:8080/clients')
          .then(response => response.json())
          .then(data => {
            this.clients = data._embedded.clients; // Actualizamos el estado 'clients' con los datos obtenidos
            
            // Extraemos los IDs y los almacenamos en clientIds
            this.clientIds = this.clients.map(client => client.id);
            console.log('IDs de los clientes:', this.clientIds);
          })
          .catch(error => {
            console.error('Error al obtener clientes:', error);
          });
      },
      
      // Método para añadir un nuevo cliente
      addClient() {
        axios.post('http://localhost:8080/clients', this.newClient)
          .then(response => {
            // Reiniciamos los campos del formulario para el próximo ingreso
            this.newClient.firstName = '';
            this.newClient.lastName = '';
            this.newClient.email = '';
            this.fetchClients();  // Actualizamos la lista de clientes
          })
          .catch(error => {
            console.error('Error al añadir cliente:', error);
          });
      }
    }
    
  // Indicamos en qué elemento del DOM se montará la aplicación
  }).mount('#app');
  