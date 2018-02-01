(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('ScanningRegistrationDialogController', ScanningRegistrationDialogController);

    ScanningRegistrationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ScanningRegistration'];

    function ScanningRegistrationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ScanningRegistration) {
        var vm = this;

        vm.scanningRegistration = entity;
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
            if (vm.scanningRegistration.id !== null) {
                ScanningRegistration.update(vm.scanningRegistration, onSaveSuccess, onSaveError);
            } else {
                ScanningRegistration.save(vm.scanningRegistration, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('holleyImsApp:scanningRegistrationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
