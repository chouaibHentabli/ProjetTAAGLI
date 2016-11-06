(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cause', {
            parent: 'entity',
            url: '/cause?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rsdataApp.cause.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cause/causes.html',
                    controller: 'CauseController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cause');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cause-detail', {
            parent: 'entity',
            url: '/cause/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rsdataApp.cause.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cause/cause-detail.html',
                    controller: 'CauseDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cause');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cause', function($stateParams, Cause) {
                    return Cause.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cause',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cause-detail.edit', {
            parent: 'cause-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cause/cause-dialog.html',
                    controller: 'CauseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cause', function(Cause) {
                            return Cause.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cause.new', {
            parent: 'cause',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cause/cause-dialog.html',
                    controller: 'CauseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                causeIntitule: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cause', null, { reload: 'cause' });
                }, function() {
                    $state.go('cause');
                });
            }]
        })
        .state('cause.edit', {
            parent: 'cause',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cause/cause-dialog.html',
                    controller: 'CauseDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cause', function(Cause) {
                            return Cause.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cause', null, { reload: 'cause' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cause.delete', {
            parent: 'cause',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cause/cause-delete-dialog.html',
                    controller: 'CauseDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cause', function(Cause) {
                            return Cause.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cause', null, { reload: 'cause' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
