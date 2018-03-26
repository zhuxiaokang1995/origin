(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('TransportTaskDeleteController',TransportTaskDeleteController);

    TransportTaskDeleteController.$inject = ['$uibModalInstance', 'entity', 'TransportTask'];

    function TransportTaskDeleteController($uibModalInstance, entity, TransportTask) {
        var vm = this;

        vm.transportTask = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TransportTask.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
