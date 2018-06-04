(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('AbnormalInformationDialogController', AbnormalInformationDialogController);

    AbnormalInformationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AbnormalInformation'];

    function AbnormalInformationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AbnormalInformation) {
        var vm = this;

        vm.abnormalInformation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.abnormalInformation.id !== null) {
                AbnormalInformation.update(vm.abnormalInformation, onSaveSuccess, onSaveError);
            } else {
                AbnormalInformation.save(vm.abnormalInformation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('holleyImsApp:abnormalInformationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.abnormalTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
