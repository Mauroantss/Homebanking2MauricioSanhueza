const { createApp } = Vue;

createApp({
  data() {
    return {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
    };
  },
  created() {
    console.log("Aplicación Vue creada");
  },
  methods: {
    logn() {
      console.log("Intentando iniciar sesión con", this.email, this.password);
      axios
        .post("/api/login", `email=${this.email}&password=${this.password}`)
        .then((response) => {
          console.log("Respuesta del servidor al iniciar sesión:", response);
          console.log("Signed in");
          window.location.href = "/web/pages/accounts.html";
        })
        .catch((error) => {
          console.log("Error al intentar iniciar sesión:", error);
        });
    },
    register() {
      console.log("Intentando registrar con", this.firstName, this.lastName, this.email, this.password);
      axios
        .post(
          "/api/clients",
          `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`
        )
        .then((response) => {
          console.log("Respuesta del servidor al registrar:", response);
          console.log("User registered");
          axios
            .post("/api/login", `email=${this.email}&password=${this.password}`)
            .then((response) => {
              console.log("Respuesta del servidor al iniciar sesión después de registrarse:", response);
              console.log("Signed in");
              window.location = "web/pages/accounts.html";
            })
            .catch((error) => {
              console.log("Error al intentar iniciar sesión después de registrarse:", error);
            });
        })
        .catch((error) => {
          console.log("Error al intentar registrar usuario:", error);
        });
    },
  },
}).mount("#app");