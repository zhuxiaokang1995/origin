(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('ProcessControlDetailController', ProcessControlDetailController);

    ProcessControlDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProcessControl'];

    function ProcessControlDetailController($scope, $rootScope, $stateParams, previousState, entity, ProcessControl) {
        var vm = this;

        vm.processControl = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('holleyImsApp:processControlUpdate', function(event, result) {
            vm.processControl = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
