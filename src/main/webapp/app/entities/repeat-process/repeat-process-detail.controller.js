(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('RepeatProcessDetailController', RepeatProcessDetailController);

    RepeatProcessDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RepeatProcess', 'OrderInfo'];

    function RepeatProcessDetailController($scope, $rootScope, $stateParams, previousState, entity, RepeatProcess, OrderInfo) {
        var vm = this;

        vm.repeatProcess = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('holleyImsApp:repeatProcessUpdate', function(event, result) {
            vm.repeatProcess = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
