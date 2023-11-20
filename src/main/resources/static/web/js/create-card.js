const { createApp } = Vue;

createApp({
  data() {
    return {
      client: [],
      cardType: [],
      cardColor: [],
    };
  },
  created() {},
  methods: {
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
    createCard() {
      axios
        .post(
          "/api/clients/current/cards",
          `color=${this.cardColor}&type=${this.cardType}`
        )
        .then((response) => {
          location.pathname = "/web/pages/card.html";
        })
        .catch((error) => {
          error;
        });
    },
  },
  computed: {},
}).mount("#app");