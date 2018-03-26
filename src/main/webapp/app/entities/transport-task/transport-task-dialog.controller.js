(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('TransportTaskDialogController', TransportTaskDialogController);

    TransportTaskDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TransportTask'];

    function TransportTaskDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TransportTask) {
        var vm = this;

        vm.transportTask = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.transportTask.id !== null) {
                TransportTask.update(vm.transportTask, onSaveSuccess, onSaveError);
            } else {
                TransportTask.save(vm.transportTask, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('holleyImsApp:transportTaskUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
