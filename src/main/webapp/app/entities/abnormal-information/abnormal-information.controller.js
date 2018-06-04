(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('AbnormalInformationController', AbnormalInformationController);

    AbnormalInformationController.$inject = ['AbnormalInformation'];

    function AbnormalInformationController(AbnormalInformation) {

        var vm = this;

        vm.abnormalInformations = [];

        loadAll();

        function loadAll() {
            AbnormalInformation.query(function(result) {
                vm.abnormalInformations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
