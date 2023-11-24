const { createApp } = Vue;

createApp({
  data() {
    return {
      inputValue: 0,
      name: '',
      maxAmount: null,
      payments: [],
      interestPercentage: null
      
    };
  },
  created() {},

  watch: {},
  methods: {
    logOut() {
      axios.post("/api/logout").then((response) => {
        console.log("Signed out");
        location.pathname = "/web/index.html"; // Redirige al usuario a la página de inicio.
      });
    },
    createLoan() {
      Swal.fire({
        title: "Are you sure you want to create the loan?",
        icon: "warning",
        iconColor: "#fff",
        showCancelButton: true,
        background: "#0056b3",
        confirmButtonColor: "#003f80",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, create loan",
        cancelButtonText: "Cancel",
      }).then((result) => {
        if (result.isConfirmed) {
          // Construye el objeto de datos para enviar al servidor
          
          
          

          axios
          .post("/api/loans/create",`name=${this.name}&maxAmount=${this.maxAmount}&payments=${this.payments}&interestPercentage=${this.interestPercentage}`
          )
            .then(() => {
              Swal.fire({
                icon: "success",
                title: "Loan created",
                background: "#0056b3",
                iconColor: "#fff",
                text: "Loan created successfully.",
                confirmButtonColor: "#003f80",
              }).then(() => {
                location.pathname = "web/pages/manager.html";
              });
            })
            .catch((error) => {
              console.log(error);
              this.errorMessage(error.response.data);
            });
        }
      });
    },
    errorMessage(message) {
      Swal.fire({
        icon: "error",
        iconColor: "#fff",
        title: "An error has occurred",
        text: message,
        color: "#fff",
        background: "#0056b3",
        confirmButtonColor: "#17acc9",
      });
    },
    addValueToArray() {
      if (this.inputValue !== 0) {
        this.payments.push(this.inputValue);
        this.inputValue = 0;
      }
    },
    resetPayments() {
      this.payments = [];
    },
  },
}).mount("#app");
