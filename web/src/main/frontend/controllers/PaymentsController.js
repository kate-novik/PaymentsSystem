import angular from 'angular';

class PaymentsController {
  /* @ngInject */
  constructor($scope, $http) {
    this.$scope = $scope;
    this.$http = $http;
    this.query = {
      order: 'id',
      limit: 10,
      page: 1
    };

    this.filter = {};

    $scope.fetch = (page, limit) => {
      let params = {};
      params.pageNumber = page || 1;
      params.pageSize = limit || 10;

      params = Object.assign(params, this.filter);

      $http.get(`/paymentsSystem/api/accounts/${this.$scope.selectedAccount.id}/payments`, {params})
        .then(resp => {
          this.payments = resp.data.payments;
          this.total = resp.data.totalCountItems;
        });
    };

    $scope.fetch();
  }

  reset() {
    this.filter = {};
    this.$scope.fetch();
  }

  isUp (payment) {
    return payment.accountDestination.id === this.$scope.selectedAccount.id;
  }
}

angular.module('payments').controller('PaymentsController', PaymentsController);