(function() {
    'use strict';
    angular
        .module('holleyImsApp')
        .factory('ProcessControl', ProcessControl);

    ProcessControl.$inject = ['$resource', 'DateUtils'];

    function ProcessControl ($resource, DateUtils) {
        var resourceUrl =  'api/process-controls/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.mountGuardTime = DateUtils.convertDateTimeFromServer(data.mountGuardTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
