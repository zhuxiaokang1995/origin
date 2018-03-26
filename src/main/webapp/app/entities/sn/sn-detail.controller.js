(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('SnDetailController', SnDetailController);

    SnDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sn'];

    function SnDetailController($scope, $rootScope, $stateParams, previousState, entity, Sn) {
        var vm = this;

        vm.sn = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('holleyImsApp:snUpdate', function(event, result) {
            vm.sn = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
