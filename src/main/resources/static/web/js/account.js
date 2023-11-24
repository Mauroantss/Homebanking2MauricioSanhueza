const app = Vue.createApp({
    data() {
      return {
        client: {},
        account: {},
        transactions: [],
        messageError: "",
        totalBalance: 0, // Saldo total calculado a partir de las transacciones
      };
    },
  
    created() {
      let urlParams = new URLSearchParams(location.search);
      let id = urlParams.get('id');
      
      axios.get(`/api/accounts/${id}`)
        .then(response => {
          this.account = response.data;
          this.transactions = this.account.transactions;
          this.transactions.sort((a, b) => b.id - a.id);
          this.calculateTotalBalance(); // Calcular el saldo total después de recibir las transacciones
        })
        .catch(error => {
          this.messageError = error.response.data;
        });
      
      axios.get("/api/clients/current")
        .then(response => {
          this.client = response.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
  
    methods: {
      logout() {
        axios.post(`/api/logout`)
          .then(response => {
            console.log("SingOut");
            location.href = `http://localhost:8080/index.html`;
          })
          .catch(error => {
            console.log(error);
          });
      },
  
      formatNumber(number) {
        return number.toLocaleString("De-DE", {
          minimumFractionDigits: 2,
          maximumFractionDigits: 2,
        });
      },
  
      dateFormat(dateString) {
        const date = new Date(dateString);
        const formatOptions = { year: 'numeric', month: '2-digit', day: '2-digit' };
        return date.toLocaleDateString('es-ES', formatOptions);
      },
  
      calculateTotalBalance() {
        this.totalBalance = this.transactions.reduce((total, transaction) => {
          return transaction.type === 'CREDIT' ? total + transaction.amount : total - transaction.amount;
        }, 0);
      },
    },
  });
  
  app.mount('#app');
  