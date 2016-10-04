import angular from 'angular';

class PayController {
  /* @ngInject */
  constructor($http) {
    this.$http = $http;

    this.getServicesAccounts();
    this.getOwnAccounts();
  }

  getServicesAccounts() {
    this.$http.get('/paymentsSystem/api/accounts', {
      params: {
        type: 'business'
      }
    })
      .then(resp => {
        this.services = resp.data;
      });
  }

  getOwnAccounts() {
    this.$http.get('/paymentsSystem/api/accounts')
      .then(resp => {
        this.accounts = resp.data;
      });
  }

  ok() {
    this.$http.post(`/paymentsSystem/api/payments`, {
      accountSource: this.source.id,
      accountDestination: this.destination.id,
      amount: this.amount,
      title: 'Pay service'
    });
  }
}

angular.module('payments').controller('PayController', PayController);