import angular from 'angular';
import angularAnimate from 'angular-animate';
import ngMaterial from 'angular-material';
import 'angular-aria';
import angularMaterialTable from 'angular-material-data-table';

import 'babel-polyfill';

// Styles
import 'angular-material/angular-material.css';
import 'angular-material-data-table/dist/md-data-table.min.css'

import './styles/style.css';

import 'material-design-icons';

angular.module('payments', [
    angularAnimate,
    ngMaterial,
    angularMaterialTable
]).controller('Controller', ['$scope', $scope => {
    $scope.title = 'Payment system';
}]);