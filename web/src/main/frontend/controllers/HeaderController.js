import angular from 'angular';

class HeaderController {
  /* @ngInject */
  constructor($scope) {
    $scope.title = 'Payment system';
  }
}

angular.module('payments').controller('HeaderController', HeaderController);