(function() {
    'use strict';
    angular
        .module('holleyImsApp')
        .factory('Processes', Processes);

    Processes.$inject = ['$resource'];

    function Processes ($resource) {
        var resourceUrl =  'api/processes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
