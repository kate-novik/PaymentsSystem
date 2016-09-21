import angular from 'angular';

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
      return this.$http.get(`/api/accounts/${idAccount}/refill`, {
        params: {amount}
      });
    }).then(result => {
      this.accounts[index] = result.data;
    });
  }

  block(ev, id) {
      // Appending dialog to document.body to cover sidenav in docs app
      var confirm = this.$mdDialog.confirm()
        .title('Block account')
        .textContent('Are you sure?')
        .targetEvent(ev)
        .ok('Block')
        .cancel('Cancel');

    this.$mdDialog.show(confirm).then(function() {
        $scope.status = 'You decided to get rid of your debt.';
      }, function() {
        $scope.status = 'You decided to keep your debt.';
      });
  }
}

angular.module('payments').controller('AccountsController', AccountsController);