(function() {
    'use strict';

    angular
        .module('rsdataApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('variable', {
            parent: 'entity',
            url: '/variable?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rsdataApp.variable.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/variable/variables.html',
                    controller: 'VariableController',
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
                    $translatePartialLoader.addPart('variable');
                    $translatePartialLoader.addPart('type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('variable-detail', {
            parent: 'entity',
            url: '/variable/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rsdataApp.variable.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/variable/variable-detail.html',
                    controller: 'VariableDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('variable');
                    $translatePartialLoader.addPart('type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Variable', function($stateParams, Variable) {
                    return Variable.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'variable',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('variable-detail.edit', {
            parent: 'variable-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variable/variable-dialog.html',
                    controller: 'VariableDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Variable', function(Variable) {
                            return Variable.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('variable.new', {
            parent: 'variable',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variable/variable-dialog.html',
                    controller: 'VariableDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                description: null,
                                obligatoire: null,
                                varType: null,
                                varRegex: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('variable', null, { reload: 'variable' });
                }, function() {
                    $state.go('variable');
                });
            }]
        })
        .state('variable.edit', {
            parent: 'variable',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variable/variable-dialog.html',
                    controller: 'VariableDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Variable', function(Variable) {
                            return Variable.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('variable', null, { reload: 'variable' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('variable.delete', {
            parent: 'variable',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variable/variable-delete-dialog.html',
                    controller: 'VariableDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Variable', function(Variable) {
                            return Variable.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('variable', null, { reload: 'variable' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
