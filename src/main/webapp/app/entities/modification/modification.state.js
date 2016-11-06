(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('modification', {
            parent: 'entity',
            url: '/modification?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rsdataApp.modification.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/modification/modifications.html',
                    controller: 'ModificationController',
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
                    $translatePartialLoader.addPart('modification');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('modification-detail', {
            parent: 'entity',
            url: '/modification/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rsdataApp.modification.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/modification/modification-detail.html',
                    controller: 'ModificationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('modification');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Modification', function($stateParams, Modification) {
                    return Modification.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'modification',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('modification-detail.edit', {
            parent: 'modification-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/modification/modification-dialog.html',
                    controller: 'ModificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Modification', function(Modification) {
                            return Modification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('modification.new', {
            parent: 'modification',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/modification/modification-dialog.html',
                    controller: 'ModificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dateMod: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('modification', null, { reload: 'modification' });
                }, function() {
                    $state.go('modification');
                });
            }]
        })
        .state('modification.edit', {
            parent: 'modification',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/modification/modification-dialog.html',
                    controller: 'ModificationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Modification', function(Modification) {
                            return Modification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('modification', null, { reload: 'modification' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('modification.delete', {
            parent: 'modification',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/modification/modification-delete-dialog.html',
                    controller: 'ModificationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Modification', function(Modification) {
                            return Modification.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('modification', null, { reload: 'modification' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
