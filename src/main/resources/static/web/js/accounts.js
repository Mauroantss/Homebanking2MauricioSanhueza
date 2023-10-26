const { createApp } = Vue;

createApp({
  data() {
    return {
      client: {},
      accounts: [],
      loading: true,
    };
  },
  created() {
    axios
      .get("/api/clients/currents")
      .then((response) => {
        this.client = response.data;
        this.accounts = response.data.accounts;
        setTimeout(() => (this.loading = false), 800);
      })
      .catch((error) => {
        console.error("Hubo un error:", error);
      });
  },
  methods: {
    createAccount() {
      window.location.href = "/web/pages/create-card.html";
    },
    logOut() {
      axios.post("/api/logout").then((response) => {
        console.log("Signed out");
        location.pathname = "/web/index.html";
      });
    },
  },
  computed: {
    canCreateAccount() {
      return this.client.accounts.length < 3;
    },
    totalBalance() {
      return this.accounts.reduce(
        (total, account) => total + account.balance,
        0
      );
    },
  },
}).mount("#create-client-app");
