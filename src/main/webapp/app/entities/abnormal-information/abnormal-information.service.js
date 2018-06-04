(function() {
    'use strict';
    angular
        .module('holleyImsApp')
        .factory('AbnormalInformation', AbnormalInformation);

    AbnormalInformation.$inject = ['$resource', 'DateUtils'];

    function AbnormalInformation ($resource, DateUtils) {
        var resourceUrl =  'api/abnormal-informations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.abnormalTime = DateUtils.convertDateTimeFromServer(data.abnormalTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
