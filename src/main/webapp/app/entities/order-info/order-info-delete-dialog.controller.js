(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('OrderInfoDeleteController',OrderInfoDeleteController);

    OrderInfoDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrderInfo'];

    function OrderInfoDeleteController($uibModalInstance, entity, OrderInfo) {
        var vm = this;

        vm.orderInfo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrderInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
