(function() {
    'use strict';

    angular
        .module('holleyImsApp')
        .controller('SnController', SnController);

    SnController.$inject = ['Sn'];

    function SnController(Sn) {

        var vm = this;

        vm.sns = [];

        loadAll();

        function loadAll() {
            Sn.query(function(result) {
                vm.sns = result;
                vm.searchQuery = null;
            });
        }
    }
})();
