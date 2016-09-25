import angular from 'angular';

import moneyTransferTmpl from '../templates/money-transfer.html';

class AccountsController {
  /* @ngInject */
  constructor($http, $mdDialog) {

    this.$http = $http;
    this.$mdDialog = $mdDialog;
    this.query = {
      order: 'id',
      limit: 5,
      page: 1
    };
    this.fetch();
  }

  fetch() {
    this.loading = this.$http.get('/api/accounts')
      .then(resp => {
        this.accounts = resp.data;
      })
  }

  refill(ev, idAccount, index) {
    var confirm = this.$mdDialog.prompt()
      .title('Account refill')
      .textContent('Enter the transfer amount:')
      .initialValue(0)
      .targetEvent(ev)
      .ok('Refill')
      .cancel('Cancel');

    this.$mdDialog.show(confirm).then(amount => {
      return this.$http.post(`/api/accounts/refill`, {amount, idAccount});
    }).then(result => {
      this.accounts[index] = result.data;
    });
  }

  transfer(ev, idAccount, index) {
    this.$mdDialog.show({
      controller: 'MoneyTransferController',
      controllerAs: 'ctrl',
      bindToController: true,
      template: moneyTransferTmpl,
      targetEvent: ev,
      clickOutsideToClose:true,
      locals: {
        modalData: {
          current: idAccount,
          accounts: this.accounts
        }
      }
    })
      .then((result) => {
        return this.$http.post(`/api/accounts/transfer`, result);
      })
      .then(() => {
        this.fetch();
      });
  }

  block(ev, idAccount, index) {
      var confirm = this.$mdDialog.confirm()
        .title('Block account')
        .textContent('Are you sure?')
        .targetEvent(ev)
        .ok('Block')
        .cancel('Cancel');

    this.$mdDialog.show(confirm).then(() => {
      return this.$http.get(`/api/accounts/${idAccount}/lock`);
    })
      .then(() => {
        this.fetch();
      });
  }
}

angular.module('payments').controller('AccountsController', AccountsController);