(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('ProcessesController', ProcessesController);

    ProcessesController.$inject = ['Processes'];

    function ProcessesController(Processes) {

        var vm = this;

        vm.processes = [];

        loadAll();

        function loadAll() {
            Processes.query(function(result) {
                vm.processes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
