(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('ProcessesDialogController', ProcessesDialogController);

    ProcessesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Processes', 'OrderInfo'];

    function ProcessesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Processes, OrderInfo) {
        var vm = this;

        vm.processes = entity;
        vm.clear = clear;
        vm.save = save;
        vm.orderinfos = OrderInfo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.processes.id !== null) {
                Processes.update(vm.processes, onSaveSuccess, onSaveError);
            } else {
                Processes.save(vm.processes, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('holleyImsApp:processesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
