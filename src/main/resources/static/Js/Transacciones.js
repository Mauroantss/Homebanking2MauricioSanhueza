const { createApp } = Vue;

createApp({
  data() {
    return {
      account: {},
      transactions: [],
    };
  },
  created() {
    const urlParams = new URLSearchParams(window.location.search);
    const accountId = urlParams.get('id');

    if(accountId) {
      axios
        .get(`http://localhost:8080/api/accounts/${accountId}`)
        .then((response) => {
          this.account = response.data;
          this.transactions = response.data.transactions || [];
          this.transactions.sort((a, b) => b.id - a.id);
        })
        .catch((error) => {
          console.error("Error al obtener los datos:", error);
        });
    } else {
      console.error("ID de cuenta no proporcionado en la URL.");
    }
  },
  methods: {
    formatToUSD(value) {
      return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
      }).format(value);
    }
  },
}).mount("#app");
