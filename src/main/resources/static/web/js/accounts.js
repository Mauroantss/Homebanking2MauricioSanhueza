const { createApp } = Vue;

createApp({
  data() {
    return {
      client: {},  // Cambiado de [] a {}
      accounts: [],
      loading: true, // Agregamos una propiedad de carga
    };
  },
  created() {
    axios
      .get("/api/clients/currents")
      .then((response) => {
        console.log(response)
        this.client = response.data;
        this.accounts = response.data.accounts;
        setTimeout(() => (this.loading = false), 800); // Quitamos la pantalla de carga después de 800 ms
      })
      .catch((error) => {
        console.error("Hubo un error:", error); // Mejor manejo del error
      });
  },
  methods: {
    logOut() {
      axios.post("/api/logout").then((response) => {
        console.log("Signed out");
        location.pathname = "/web/index.html"; // Redirige al usuario a la página de inicio.
      });
    },
  },
  computed: {
    totalBalance() {
      return this.accounts.reduce(
        (total, account) => total + account.balance,
        0
      );
    },
  },
}).mount("#create-client-app");  // Cambiado de "#app" a "#create-client-app"
