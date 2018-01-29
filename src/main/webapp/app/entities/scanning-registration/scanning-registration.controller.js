(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('ScanningRegistrationController', ScanningRegistrationController);

    ScanningRegistrationController.$inject = ['ScanningRegistration'];

    function ScanningRegistrationController(ScanningRegistration) {

        var vm = this;

        vm.scanningRegistrations = [];

        loadAll();

        function loadAll() {
            ScanningRegistration.query(function(result) {
                vm.scanningRegistrations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
