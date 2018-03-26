(function() {
    'use strict';
    angular
        .module('holleyImsApp')
        .factory('Sn', Sn);

    Sn.$inject = ['$resource'];

    function Sn ($resource) {
        var resourceUrl =  'api/sns/:id';

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
