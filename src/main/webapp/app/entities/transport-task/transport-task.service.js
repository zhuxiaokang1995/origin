(function() {
    'use strict';
    angular
        .module('holleyImsApp')
        .factory('TransportTask', TransportTask);

    TransportTask.$inject = ['$resource'];

    function TransportTask ($resource) {
        var resourceUrl =  'api/transport-tasks/:id';

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
