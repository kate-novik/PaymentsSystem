import angular from 'angular';

class MainController {
  /* @ngInject */
  constructor($scope) {
    this.$scope = $scope;
    this.view = 'accounts';
    this.selectedAccount = null;
  }

  makePayment(account) {
    this.$scope.selectedAccount = account;
    this.changeView('payment');
  }

  showPaymentsForAccount(account) {
    this.$scope.selectedAccount = account;
    this.changeView('payments');
  }

  changeView(view) {
    this.view = view;
  }
}

angular.module('payments').controller('MainController', MainController);