(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('OrderInfoDetailController', OrderInfoDetailController);

    OrderInfoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrderInfo'];

    function OrderInfoDetailController($scope, $rootScope, $stateParams, previousState, entity, OrderInfo) {
        var vm = this;

        vm.orderInfo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('holleyImsApp:orderInfoUpdate', function(event, result) {
            vm.orderInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
