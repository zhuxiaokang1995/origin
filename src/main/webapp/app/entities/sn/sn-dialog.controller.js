(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('SnDialogController', SnDialogController);

    SnDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Sn'];

    function SnDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Sn) {
        var vm = this;

        vm.sn = entity;
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
            if (vm.sn.id !== null) {
                Sn.update(vm.sn, onSaveSuccess, onSaveError);
            } else {
                Sn.save(vm.sn, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('holleyImsApp:snUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
