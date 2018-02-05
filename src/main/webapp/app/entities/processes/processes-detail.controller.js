(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('ProcessesDetailController', ProcessesDetailController);

    ProcessesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Processes', 'OrderInfo'];

    function ProcessesDetailController($scope, $rootScope, $stateParams, previousState, entity, Processes, OrderInfo) {
        var vm = this;

        vm.processes = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('holleyImsApp:processesUpdate', function(event, result) {
            vm.processes = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
