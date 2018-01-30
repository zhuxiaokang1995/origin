(function() {
    'use strict';
    angular
        .module('holleyImsApp')
        .factory('ScanningRegistration', ScanningRegistration);

    ScanningRegistration.$inject = ['$resource'];

    function ScanningRegistration ($resource) {
        var resourceUrl =  'api/scanning-registrations/:id';

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
