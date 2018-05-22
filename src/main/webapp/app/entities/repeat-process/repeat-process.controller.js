(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('RepeatProcessController', RepeatProcessController);

    RepeatProcessController.$inject = ['RepeatProcess'];

    function RepeatProcessController(RepeatProcess) {
        var vm = this;

        vm.repeatProcesses = [];

        loadAll();

        function loadAll() {
            RepeatProcess.query(function(result) {
                vm.repeatProcesses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
