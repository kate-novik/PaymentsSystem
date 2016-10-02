import angular from 'angular';

import moneyTransferTmpl from '../templates/money-transfer.html';

class AccountsController {
  /* @ngInject */
  constructor($scope, $http, $mdDialog, $mdToast) {

    this.$http = $http;
    this.$mdDialog = $mdDialog;
    this.$mdToast = $mdToast;
    this.$scope = $scope;

    $scope.fetch = (page, limit) => {
      let params = {};

      $http.get('/api/accounts', {params})
        .then(resp => {
          this.accounts = resp.data;
        });
    };

    this.fetch();
  }

  fetch() {
    this.$http.get('/api/accounts')
      .then(resp => {
        this.accounts = resp.data;
      });
  }

  create() {
    return this.$http.post(`/api/accounts`).then(() => {
      this.$mdToast.show(
        this.$mdToast.simple()
          .textContent('Account was created!')
          .position('right bottom')
          .hideDelay(3000)
      );
      this.fetch();
    });
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

  isLocked (account) {
    return account.state === 'LOCKED';
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

  unblock(ev, idAccount, index) {
    var confirm = this.$mdDialog.confirm()
        .title('Unlock account')
        .textContent('Are you sure?')
        .targetEvent(ev)
        .ok('Unlock')
        .cancel('Cancel');

    this.$mdDialog.show(confirm).then(() => {
      return this.$http.get(`/api/accounts/${idAccount}/unlock`);
    })
        .then(() => {
          this.fetch();
        });
  }
}

angular.module('payments').controller('AccountsController', AccountsController);