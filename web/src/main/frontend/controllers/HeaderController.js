import angular from 'angular';

class HeaderController {
  /* @ngInject */
  constructor($scope, $rootScope) {
    $scope.title = 'Payment system';
    $rootScope.maxDate = new Date();
  }
}

angular.module('payments').controller('HeaderController', HeaderController);