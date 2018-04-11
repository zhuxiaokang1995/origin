(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('TransportTaskController', TransportTaskController);

    TransportTaskController.$inject = ['TransportTask'];

    function TransportTaskController(TransportTask) {
        var vm = this;

        vm.transportTasks = [];

        loadAll();

        function loadAll() {
            TransportTask.query(function(result) {
                vm.transportTasks = result;
                vm.searchQuery = null;
            });
        }
    }
})();
