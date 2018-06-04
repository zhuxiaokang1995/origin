(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('AbnormalInformationDetailController', AbnormalInformationDetailController);

    AbnormalInformationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AbnormalInformation'];

    function AbnormalInformationDetailController($scope, $rootScope, $stateParams, previousState, entity, AbnormalInformation) {
        var vm = this;

        vm.abnormalInformation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('holleyImsApp:abnormalInformationUpdate', function(event, result) {
            vm.abnormalInformation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
