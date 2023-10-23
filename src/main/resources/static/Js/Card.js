const cardApp = Vue.createApp({
    data() {
        return {
            cardData: [],
            accounts: [],
            selectedAccountId: null,
        };
    },
    mounted() {
        this.selectedAccountId = this.getAccountIdFromUrl(); // Asigna el ID de la cuenta desde la URL
        this.fetchCards();
        this.fetchAccounts(); 
    },
    computed: {
        filteredCards() {
            if (this.selectedAccountId) {
                return this.cardData.filter(card => card.accountId == this.selectedAccountId);
            }
            return [];
        }
    },
    methods: {
        getAccountIdFromUrl() {
            const urlParams = new URLSearchParams(window.location.search);
            return urlParams.get('id');
        },
        fetchCards() {
            axios.get('http://localhost:8080/rest/cards')
                .then(response => {
                    this.cardData = response.data._embedded.cards;
                })
                .catch(error => {
                    console.error('Error al obtener las tarjetas:', error);
                });
        },
        fetchAccounts() {
            axios.get('http://localhost:8080/api/accounts')
                .then(response => {
                    this.accounts = response.data;
                })
                .catch(error => {
                    console.error('Error al obtener las cuentas:', error);
                });
        },
    }
}).mount('#card-app');
