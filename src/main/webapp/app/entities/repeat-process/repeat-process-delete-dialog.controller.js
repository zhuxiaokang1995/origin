(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('RepeatProcessDeleteController',RepeatProcessDeleteController);

    RepeatProcessDeleteController.$inject = ['$uibModalInstance', 'entity', 'RepeatProcess'];

    function RepeatProcessDeleteController($uibModalInstance, entity, RepeatProcess) {
        var vm = this;

        vm.repeatProcess = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RepeatProcess.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
