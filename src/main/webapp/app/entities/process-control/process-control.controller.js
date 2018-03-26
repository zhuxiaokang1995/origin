(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('ProcessControlController', ProcessControlController);

    ProcessControlController.$inject = ['ProcessControl'];

    function ProcessControlController(ProcessControl) {

        var vm = this;

        vm.processControls = [];

        loadAll();

        function loadAll() {
            ProcessControl.query(function(result) {
                vm.processControls = result;
                vm.searchQuery = null;
            });
        }
    }
})();
