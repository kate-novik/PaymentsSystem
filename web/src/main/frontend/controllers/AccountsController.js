import angular from 'angular';

class AccountsController {
    /* @ngInject */
    constructor($http, $scope) {

        this.$http = $http;
        this.query = {
            order: 'id',
            limit: 5,
            page: 1
        };

        this.fetch = this.fetch.bind(this);

        this.fetch();
    }

    fetch() {
        this.loading = this.$http.get('/api/accounts')
            .then(resp => {
                this.accounts = resp.data;
            })
    }
}

angular.module('payments').controller('AccountsController', AccountsController);