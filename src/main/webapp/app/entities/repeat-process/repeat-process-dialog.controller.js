(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('RepeatProcessDialogController', RepeatProcessDialogController);

    RepeatProcessDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RepeatProcess', 'OrderInfo'];

    function RepeatProcessDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RepeatProcess, OrderInfo) {
        var vm = this;

        vm.repeatProcess = entity;
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
            if (vm.repeatProcess.id !== null) {
                RepeatProcess.update(vm.repeatProcess, onSaveSuccess, onSaveError);
            } else {
                RepeatProcess.save(vm.repeatProcess, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('holleyImsApp:repeatProcessUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
