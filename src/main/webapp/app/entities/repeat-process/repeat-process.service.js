(function() {
    'use strict';
    angular
        .module('holleyImsApp')
        .factory('RepeatProcess', RepeatProcess);

    RepeatProcess.$inject = ['$resource'];

    function RepeatProcess ($resource) {
        var resourceUrl =  'api/repeat-processes/:id';

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
