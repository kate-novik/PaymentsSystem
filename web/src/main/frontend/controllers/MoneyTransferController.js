import angular from 'angular';

class MoneyTransferController {
  /* @ngInject */
  constructor($mdDialog, modalData, $filter) {
    this.$mdDialog = $mdDialog;

    this.destAccounts = [];
    modalData.accounts.forEach(acc => {
      if (acc.id === modalData.current) {
        this.source = acc;
        this.sourceInput = `${acc.id} (${$filter('currency')(acc.balance)})`;
      } else {
        this.destAccounts.push(acc);
      }
    })
  }

  cancel() {
    this.$mdDialog.cancel();
  }

  ok() {
    this.$mdDialog.hide({
      accountSource: this.source.id,
      accountDestination: this.destination.id,
      amount: this.amount
    });
  }
}

angular.module('payments').controller('MoneyTransferController', MoneyTransferController);