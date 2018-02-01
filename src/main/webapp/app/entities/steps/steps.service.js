(function() {
    'use strict';
    angular
        .module('holleyImsApp')
        .factory('Steps', Steps);

    Steps.$inject = ['$resource'];

    function Steps ($resource) {
        var resourceUrl =  'api/steps/:id';

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
