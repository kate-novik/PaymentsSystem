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

    $scope.fetch = (page, limit) => {
      let params = {};
      params.pageNumber = page || 1;
      params.pageSize = limit || 10;

      $http.get(`/api/accounts/${this.$scope.selectedAccount}/payments`, {params})
        .then(resp => {
          this.payments = resp.data.payments;
          this.total = resp.data.totalCountItems;
        });
    };

    $scope.fetch();
  }
}

angular.module('payments').controller('PaymentsController', PaymentsController);