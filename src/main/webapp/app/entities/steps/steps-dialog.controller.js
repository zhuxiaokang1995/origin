(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('StepsDialogController', StepsDialogController);

    StepsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Steps', 'OrderInfo'];

    function StepsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Steps, OrderInfo) {
        var vm = this;

        vm.steps = entity;
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
            if (vm.steps.id !== null) {
                Steps.update(vm.steps, onSaveSuccess, onSaveError);
            } else {
                Steps.save(vm.steps, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('holleyImsApp:stepsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
