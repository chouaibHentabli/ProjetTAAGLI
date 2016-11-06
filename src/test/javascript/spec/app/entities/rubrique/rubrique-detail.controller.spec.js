'use strict';

describe('Controller Tests', function() {

    describe('Rubrique Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRubrique, MockVariable, MockBaac;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRubrique = jasmine.createSpy('MockRubrique');
            MockVariable = jasmine.createSpy('MockVariable');
            MockBaac = jasmine.createSpy('MockBaac');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Rubrique': MockRubrique,
                'Variable': MockVariable,
                'Baac': MockBaac
            };
            createController = function() {
                $injector.get('$controller')("RubriqueDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rsdataApp:rubriqueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
