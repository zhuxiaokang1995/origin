(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('OrderInfoController', OrderInfoController);

    OrderInfoController.$inject = ['OrderInfo'];

    function OrderInfoController(OrderInfo) {

        var vm = this;

        vm.orderInfos = [];

        loadAll();

        function loadAll() {
            OrderInfo.query(function(result) {
                vm.orderInfos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
