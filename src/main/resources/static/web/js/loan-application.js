const { createApp } = Vue;

createApp({
  data() {
    return {
      toAccount: "",
      paymentOptions: null, // Mantenemos Payment Options
      amount: "",
      loans: [],
      loanType: "",
      client: { accounts: [] },
      selectedLoan: null,
      newLoan: {},
    };
  },
  created() {
    this.getClient();
    this.getLoans();
  },
  methods: {
    getClient() {
      axios.get('/api/clients/current')
        .then(response => {
          this.client = response.data;
          this.accounts = response.data.accounts; 
        })
        .catch(error => {
          console.error('There was an error fetching the client data:', error);
        });
    },
    getLoans() {
      axios.get('/api/loans')
        .then(response => {
          this.loans = response.data;
        })
        .catch(error => {
          console.error('There was an error fetching the loans data:', error);
        });
    },
    createLoan() {
      if (!this.loanType || !this.amount || !this.paymentOptions || !this.toAccount) {
        this.showAlert('Incomplete Fields', 'Please complete all the fields.', 'error');
        return;
      }
      this.newLoan = {
        loanId: this.loanType,
        amount: this.amount,
        payments: this.paymentOptions,
        toAccount: this.toAccount,
      };
      this.showConfirmationDialog('Loan Application Confirmation', 'Do you want to submit the loan application?');
    },
    calculatePayments() {
      let paymentsArray = [];
      if (this.paymentOptions && this.amount) {
        const totalAmount = this.amount * 1.2; // Asumiendo un 20% de interés
        const installmentAmount = totalAmount / this.paymentOptions;

        for (let i = 0; i < this.paymentOptions; i++) {
          paymentsArray.push({
            installmentAmount: installmentAmount,
            remainingAmount: totalAmount - installmentAmount * (i + 1),
          });
        }
      }
      return paymentsArray;
    },
    showAlert(title, text, type) {
      Swal.fire({
        title: title,
        text: text,
        icon: type,
      });
    },
    showConfirmationDialog(title, text) {
      const self = this;
      Swal.fire({
        title: title,
        text: text,
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
      }).then((result) => {
        if (result.isConfirmed) {
          self.onYesClick();
        } else if (result.isDismissed) {
          self.onNoClick();
        }
      });
    },
    onYesClick() {
        axios.post('/api/loans', this.newLoan)
          .then(() => {
            this.showAlert('Loan application submitted!', '', 'success');
            this.getClient();
            // Redirigir al usuario a /web/pages/accounts.html
            window.location.href = '/web/pages/accounts.html';
          })
          .catch(error => {
            this.showAlert('Error submitting loan application.', '', 'error');
            console.error('Error registering loan:', error);
          });
      },
    onNoClick() {
      this.showAlert('Loan Application Canceled', '', 'info');
    },
  },
  watch: {
    loanType(newLoanType) {
      if (newLoanType) {
        this.selectedLoan = this.loans.find(loan => loan.id === newLoanType);
        this.paymentOptions = null;
      }
    },
  },
}).mount("#app");
