const { createApp } = Vue;

createApp({
  data() {
    return {
      cardType: "",  // DEBIT o CREDIT
      cardColor: "", // SILVER, GOLD, PLATINUM
      error: "",     // Para almacenar cualquier mensaje de error
    };
  },
  created() {
    console.log("Aplicación Vue para Create Card creada");
  },
  methods: {
    createCard() {
      console.log("Intentando crear tarjeta con tipo", this.cardType, "y color", this.cardColor);
      
      axios.post('/api/clients/current/cards', `cardType=${this.typeCard}&cardColor=${this.colorCard}`)
        .then((response) => {
          console.log("Respuesta del servidor al crear la tarjeta:", response);
          // Redirigir a cards.html después de crear la tarjeta
          window.location = "/web/pages/cards.html";
        })
        .catch((error) => {
          console.log("Error al intentar crear la tarjeta:", error);
          this.error = "Hubo un error al intentar crear la tarjeta.";
        });
    }
  }
}).mount("#app");
