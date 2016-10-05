import angular from 'angular';

class PayController {
  /* @ngInject */
  constructor($http, $scope, $filter, $mdToast) {
    this.$http = $http;
    this.$scope = $scope;
    this.$mdToast = $mdToast;

    this.sourceInput = `${$scope.selectedAccount.id} - ${$scope.selectedAccount.title} (${$filter('currency')($scope.selectedAccount.balance)})`;

    this.getServicesAccounts();
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

  ok() {
    this.$http.post(`/paymentsSystem/api/payments`, {
      accountSource: this.$scope.selectedAccount.id,
      accountDestination: this.destination.id,
      amount: this.amount,
      title: 'Pay service'
    }).then(() => {
      this.$mdToast.show(
        this.$mdToast.simple()
          .textContent('Successfull payment')
          .position('right bottom')
          .hideDelay(3000)
      );
    });
  }
}

angular.module('payments').controller('PayController', PayController);