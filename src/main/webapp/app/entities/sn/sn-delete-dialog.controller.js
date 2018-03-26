(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('SnDeleteController',SnDeleteController);

    SnDeleteController.$inject = ['$uibModalInstance', 'entity', 'Sn'];

    function SnDeleteController($uibModalInstance, entity, Sn) {
        var vm = this;

        vm.sn = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Sn.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
