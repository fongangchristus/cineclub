'use strict';

describe('Controller Tests', function() {

    describe('Salle Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSalle, MockAdresse, MockVille, MockCinema;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSalle = jasmine.createSpy('MockSalle');
            MockAdresse = jasmine.createSpy('MockAdresse');
            MockVille = jasmine.createSpy('MockVille');
            MockCinema = jasmine.createSpy('MockCinema');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Salle': MockSalle,
                'Adresse': MockAdresse,
                'Ville': MockVille,
                'Cinema': MockCinema
            };
            createController = function() {
                $injector.get('$controller')("SalleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cineclubApp:salleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
