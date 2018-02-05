(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('StepsDetailController', StepsDetailController);

    StepsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Steps'];

    function StepsDetailController($scope, $rootScope, $stateParams, previousState, entity, Steps) {
        var vm = this;

        vm.steps = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('holleyImsApp:stepsUpdate', function(event, result) {
            vm.steps = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
