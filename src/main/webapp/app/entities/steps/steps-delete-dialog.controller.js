(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('StepsDeleteController',StepsDeleteController);

    StepsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Steps'];

    function StepsDeleteController($uibModalInstance, entity, Steps) {
        var vm = this;

        vm.steps = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Steps.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
