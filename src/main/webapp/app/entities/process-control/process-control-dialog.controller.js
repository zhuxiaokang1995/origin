(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('ProcessControlDialogController', ProcessControlDialogController);

    ProcessControlDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProcessControl'];

    function ProcessControlDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProcessControl) {
        var vm = this;

        vm.processControl = entity;
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
            if (vm.processControl.id !== null) {
                ProcessControl.update(vm.processControl, onSaveSuccess, onSaveError);
            } else {
                ProcessControl.save(vm.processControl, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('holleyImsApp:processControlUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
