(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('TransportTaskDetailController', TransportTaskDetailController);

    TransportTaskDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TransportTask'];

    function TransportTaskDetailController($scope, $rootScope, $stateParams, previousState, entity, TransportTask) {
        var vm = this;

        vm.transportTask = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('holleyImsApp:transportTaskUpdate', function(event, result) {
            vm.transportTask = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
