import angular from 'angular';

class PaymentsController {
  /* @ngInject */
  constructor($scope, $http) {
    this.$scope = $scope;
    this.$http = $http;
    console.log(this);
    this.fetch();
  }

  fetch() {
    this.loading = this.$http.get(`/api/accounts/${this.$scope.selectedAccount}/payments`)
      .then(resp => {
        this.payments = resp.data.payments;
      });
  }
}

angular.module('payments').controller('PaymentsController', PaymentsController);