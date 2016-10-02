import angular from 'angular';

class MainController {
  /* @ngInject */
  constructor($scope) {
    this.$scope = $scope;
    this.view = 'accounts';
    this.selectedAccount = null;
  }

  showPaymentsForAccount(accountId) {
    this.$scope.selectedAccount = accountId;
    this.changeView('payments');
  }

  changeView(view) {
    this.view = view;
  }
}

angular.module('payments').controller('MainController', MainController);