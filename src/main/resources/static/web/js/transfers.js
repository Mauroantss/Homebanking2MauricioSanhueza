const { createApp } = Vue;

createApp({
  data() {
    return {
      client: [],
      cards: [],
      creditCards: [],
      debitCards: [],
      transferType: '',
      ownAccount: '',
      thirdPartyAccount: '',
      amount: null,
      description: '',
    };
  },
  created() {
    axios
      .get("/api/clients/currents")
      .then((response) => {
        this.client = response.data;
        this.cards = response.data.cards;
        this.creditCards = this.cards.filter(
          (card) => card.cardType == "CREDIT"
        );
        this.debitCards = this.cards.filter((card) => card.cardType == "DEBIT");
      })
      .catch((error) => {
        console.error(error);
      });
       // Obtener cuentas del cliente
    axios.get("/api/clients/current/accounts")
    .then((response) => {
      this.accounts = response.data;
    })
    .catch((error) => {
      console.error(error);
    });
  },
  methods: {
    logOut() {
      axios.post("/api/logout").then((response) => {
        console.log("Signed out");
        location.pathname = "/web/index.html";
      });
    },
    makeTransfer() {
      const transferData = {
        transferType: this.transferType,
        ownAccount: this.ownAccount,
        thirdPartyAccount: this.thirdPartyAccount,
        amount: this.amount,
        description: this.description,
      };

      axios.post('/api/clients/current/transaction', transferData)
        .then(response => {
          alert('Transferencia realizada con éxito');
          location.reload();
        })
        .catch(error => {
          alert(`Ocurrió un error al realizar la transferencia: ${error}`);
        });
    },
    toggleAccountField() {
      // Aquí va el código para alternar entre las opciones de cuenta
      console.log(`El tipo de transferencia seleccionado es: ${this.transferType}`);
    },
    confirmTransfer() {
      const transferData = {
        transferType: this.transferType,
        ownAccount: this.ownAccount,
        thirdPartyAccount: this.thirdPartyAccount,
        amount: this.amount,
        description: this.description,
      };

      axios.post('/api/clients/current/transaction', transferData)
        .then(response => {
          alert('Transferencia realizada con éxito');
          location.reload();
        })
        .catch(error => {
          alert(`Ocurrió un error al realizar la transferencia: ${error}`);
        });
    }
  }
}).mount("#app");
