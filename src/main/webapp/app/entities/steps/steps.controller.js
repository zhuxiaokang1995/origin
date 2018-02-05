(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('StepsController', StepsController);

    StepsController.$inject = ['Steps'];

    function StepsController(Steps) {

        var vm = this;

        vm.steps = [];

        loadAll();

        function loadAll() {
            Steps.query(function(result) {
                vm.steps = result;
                vm.searchQuery = null;
            });
        }
    }
})();
