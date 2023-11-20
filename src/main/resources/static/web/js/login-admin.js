const { createApp } = Vue;

createApp({
  data() {
    return {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      errorMessage: "", // Mensaje de error para mostrar al usuario
    };
  },
  created() {
    console.log("Aplicación Vue creada");
  },
  methods: {
    login() {
      if (this.email.includes("homebanking.com")) {
        console.log("Intentando iniciar sesión con", this.email, this.password);
        axios
        .post("/api/login", `email=${this.email}&password=${this.password}`)
        .then((response) => {
          console.log("Respuesta del servidor al iniciar sesión:", response);
          console.log("Signed in");
          window.location = "/web/pages/manager.html";
        })
          .catch((error) => {
            console.log("Error al intentar iniciar sesión:", error);
          });
      } else {
        console.log("El correo electrónico no pertenece al dominio 'homebanking.com'.");
        this.errorMessage = "El correo electrónico no es válido. Por favor, utilice un correo de 'homebanking.com'.";
        // No se envía el formulario y se muestra un mensaje de error.
      }
    },

  },
}).mount("#app");

