import angular from 'angular';
import angularAnimate from 'angular-animate';
import ngMaterial from 'angular-material';
import ngMessages from 'angular-messages';
import 'angular-aria';
import angularMaterialTable from 'angular-material-data-table';

import 'babel-polyfill';

// Styles
import 'angular-material/angular-material.css';
import 'angular-material-data-table/dist/md-data-table.min.css'

import './styles/admin.css';

import 'material-design-icons';

angular.module('adminPayments', [
    angularAnimate,
    ngMaterial,
    ngMessages,
    angularMaterialTable
]).controller('Controller', ['$scope', $scope => {
    $scope.title = 'Payment system';
}]);