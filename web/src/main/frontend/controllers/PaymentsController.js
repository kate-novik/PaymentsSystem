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

    this.maxDate = new Date();

    this.filter = {};

    $scope.fetch = (page, limit) => {
      let params = {};
      let filter = Object.assign({}, this.filter);
      params.pageNumber = page || 1;
      params.pageSize = limit || 10;

      if (filter.payDate) {
        let date = filter.payDate;
        let day = date.getDate();
        let monthIndex = date.getMonth();
        let year = date.getFullYear();

        filter.payDate = day + '/' + (monthIndex + 1) + '/' + year;
      }

      params = Object.assign(params, filter);

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