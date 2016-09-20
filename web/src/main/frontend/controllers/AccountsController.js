import angular from 'angular';

class AccountsController {
    /* @ngInject */
    constructor() {

        this.selected = [];

        this.query = {
            order: 'name',
            limit: 5,
            page: 1
        };

        this.accounts = [
            {
                id: 1,
                amount: 123,
                state: 'In Progress'
            },
            {
                id: 2,
                amount: 342,
                state: 'In Progress'
            },
            {
                id: 3,
                amount: 446,
                state: 'In Progress'
            }
        ];
    }
}

angular.module('payments').controller('AccountsController', AccountsController);