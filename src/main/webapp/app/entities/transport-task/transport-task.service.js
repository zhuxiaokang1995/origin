(function() {
    'use strict';
    angular
        .module('holleyImsApp')
        .factory('TransportTask', TransportTask);

    TransportTask.$inject = ['$resource', 'DateUtils'];

    function TransportTask ($resource, DateUtils) {
        var resourceUrl =  'api/transport-tasks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.issuedTaskTime = DateUtils.convertDateTimeFromServer(data.issuedTaskTime);
                        data.completionTime = DateUtils.convertDateTimeFromServer(data.completionTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
