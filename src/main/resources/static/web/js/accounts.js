const { createApp } = Vue;

createApp({
  data() {
    return {
      client: [],
      accounts: [],
      accountType: "",
    };
  },
  created() {
    this.getClient(); // Es util tener el metodo a la mano, para poder actualizar datos.
  },
  methods: {
    deleteAccount(accountId) {
      Swal.fire({
        title: "Are you sure you want to delete the account?",
        icon: "warning",
        iconColor: "#fff",
        showCancelButton: true,
        background: "#0056b3",
        confirmButtonColor: "#003f80",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete account",
        cancelButtonText: "Cancel",
      }).then((result) => {
        if (result.isConfirmed) {
          axios
            .put("/api/clients/current/accounts", `id=${accountId}`)
            .then(() => {
              Swal.fire({
                icon: "success",
                title: "Account deleted",
                background: "#0056b3",
                iconColor: "#fff",
                text: "Account deleted successfully.",
                confirmButtonColor: "#003f80",
              }).then(() => {
                location.pathname = "web/pages/accounts.html";
              });
            })
            .catch((error) => {
              console.log(error);
              this.errorMessage(error.response.data);
            });
        }
      });
    },
     createAccount(account){
        axios.post("/api/clients/current/accounts",`accountType=${account}`)
          .then((response) => {
            this.client = response.data;
            this.accounts = response.data.accounts;
            setTimeout(() => (this.loading = false), 800);
          })
        .catch(error => console.log(error))
      },
    getClient() {
      axios
        .get("/api/clients/current")
        .then((response) => {
          this.client = response.data;
          this.accounts = response.data.accounts;
          setTimeout(() => (this.loading = false), 800);
        })
        .catch((error) => {
          error;
        });
    },
    logOut() {
      axios.post("/api/logout").then((response) => {
        console.log("Signed out");
        location.pathname = "/web/index.html"; // Redirige al usuario a la página de inicio.
      });
    },
    errorMessage(message) {
      Swal.fire({
        icon: "error",
        iconColor: "#fff",
        title: "An error has occurred",
        text: message,
        confirmButtonColor: "#17acc9",
        background: "#0056b3",
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
}).mount("#create-client-app");
