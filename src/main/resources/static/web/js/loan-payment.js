const { createApp } = Vue;

createApp({
  data() {
    return {
      clientLoans: [],
      clientLoanId: 0,
      accounts: [],
      accountId: 0,
      amount: 0,
      payments: 0,
      description: "",
      interestRate: 0,
    };
  },
  created() {
    this.getClient();
    this.getLoans();
  },
  methods: {
    getClient() {
      axios.get("/api/clients/current")
        .then((response) => {
            console.log(response)
          this.client = response.data;
          this.accounts = response.data.accounts;
          this.clientLoans = response.data.loans;
        })
        .catch((error) => {
          console.error("Error al obtener el cliente:", error);
        });
    },
    getLoans() {
      axios.get("/api/loans")
        .then((response) => {
            console.log(response)
          this.loans = response.data;
          this.interestRate = response.data.interestRate;
        })
        .catch((error) => {
          console.error("Error al obtener los préstamos:", error);
        });
    },
    logOut() {
      axios.post("/api/logout")
        .then((response) => {
          location.pathname = "/web/index.html"; // Redirige al usuario a la página de inicio.
        })
        .catch((error) => {
          console.error("Error al cerrar sesión:", error);
        });
    },
    payLoan() {
      Swal.fire({
        title: "Are you sure you want to make this payment?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, make payment"
      }).then((result) => {
        if (result.isConfirmed) {
        //   const params = new URLSearchParams();
        //   params.append('idLoan', this.clientLoanId);
        //   params.append('idAccount', this.accountId);
        //   params.append('amount', this.amount);

          axios.post("/api/loans/payments", `idLoan=${this.clientLoandId}&idAccount=${this.accountId}&amount=${this.amount}`)
            .then((response) => {
              Swal.fire("Payment successful!", "Your payment has been processed.", "success");
              // Aquí puedes actualizar la información del cliente o redirigir si es necesario
            })
            .catch((error) => {
              Swal.fire("Error", "There was a problem processing your payment.", "error");
              console.error("Error al realizar el pago:", error);
            });
        }
      });
    },
    // Otros métodos que necesites...
  }
}).mount("#app");
