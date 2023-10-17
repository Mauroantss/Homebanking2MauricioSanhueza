// Definición de la aplicación Vue
const cardApp = Vue.createApp({
    // Definición de las propiedades reactivas
    data() {
        return {
            cardData: null,  // Almacena los datos de las tarjetas
            clientId: ""     // Almacena el ID del cliente seleccionado
        };
    },
    // Hook de ciclo de vida que se ejecuta cuando el componente se monta
    mounted() {
        // Llamada al método para obtener los datos de las tarjetas
        this.fetchCards();
    },
    // Definición de métodos de la aplicación
    methods: {
        // Método para obtener los datos de las tarjetas
        fetchCards() {
            // Realiza una petición HTTP para obtener datos de tarjetas
            fetch('http://localhost:8080/rest/cards')
                .then(response => {
                    // Verifica si la respuesta del servidor es exitosa
                    if (!response.ok) {
                        throw new Error('Respuesta no exitosa del servidor');
                    }
                    // Parsea la respuesta a JSON
                    return response.json();
                })
                .then(data => {
                    // Asigna los datos a la propiedad reactiva cardData
                    this.cardData = data._embedded.cards;
                })
                .catch(error => {
                    // Manejo de errores
                    console.error('Error al obtener las tarjetas:', error);
                });
        },
        // Método para obtener información de un cliente en específico
        selectClient(clientId) {
            // Realiza una petición HTTP para obtener datos del cliente
            fetch(`http://localhost:8080/api/clients/${clientId}`)
                .then(response => {
                    // Verifica si la respuesta del servidor es exitosa
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    // Parsea la respuesta a JSON
                    return response.json();
                })
                .then(data => {
                    // Asigna las cuentas del cliente a la propiedad selectedClientAccounts
                    this.selectedClientAccounts = data.accounts;
                    // Encuentra el cliente en la lista y obtiene su nombre completo
                    const client = this.clients.find(c => c.id === clientId);
                    this.selectedClientName = `${client.firstName} ${client.lastName}`;
                    // Llamada al método para obtener los préstamos del cliente
                    this.fetchClientLoans(clientId);
                })
                .catch(error => {
                    // Manejo de errores
                    console.error('Error al obtener cuentas:', error);
                });
        }
    }
}).mount('#card-app');  // Monta la aplicación en el elemento con id 'card-app'
