const { createApp } = Vue;

createApp({
  data() {
    return {
      toAccount: "",
      fromAccount: "",
      amount: "",
      transactionType: "",
      description: "",
      accounts: [],
      client: [],
      transferType: 'own'
    };
  },
  created() {
    this.getClient();
  },
  methods: {
    getClient() {
      axios.get("/api/clients/current")
        .then((response) => {
          console.log(response)
          this.client = response.data;
          this.accounts = response.data.accounts;
          setTimeout(() => (this.loading = false), 800);
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    logOut() {
      axios.post("/api/logout").then((response) => {
        console.log("Signed out");
        location.pathname = "/web/index.html";
      });
    },
    createTransfer() {
      // Muestra el modal de confirmación
      new bootstrap.Modal(document.getElementById('confirmationModal')).show();
    },
    confirmTransfer() {
      // Realiza la transferencia aquí
      const endpoint = "/api/clients/current/transfers";
      const params = `amount=${this.amount}&description=${this.description}&originNumber=${this.fromAccount}&destinationNumber=${this.toAccount}`;
      axios.post(endpoint, params)
        .then((response) => {
          // Actualiza la información del cliente
          this.getClient();

          // Muestra el modal de éxito
          new bootstrap.Modal(document.getElementById('successModal')).show();

          // Navega a la página de cuentas
          location.pathname = "/web/pages/accounts.html";
        })
        .catch((error) => {
          console.error("Error:", error);
        });
    },
    cancelTransfer() {
      // Muestra el modal de cancelación
      new bootstrap.Modal(document.getElementById('cancelModal')).show();
    }
  },
  computed: {}
}).mount("#app");