'use strict';

describe('Controller Tests', function() {

    describe('Projection Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProjection, MockPrixTicket, MockFilm, MockSeance, MockSalle;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProjection = jasmine.createSpy('MockProjection');
            MockPrixTicket = jasmine.createSpy('MockPrixTicket');
            MockFilm = jasmine.createSpy('MockFilm');
            MockSeance = jasmine.createSpy('MockSeance');
            MockSalle = jasmine.createSpy('MockSalle');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Projection': MockProjection,
                'PrixTicket': MockPrixTicket,
                'Film': MockFilm,
                'Seance': MockSeance,
                'Salle': MockSalle
            };
            createController = function() {
                $injector.get('$controller')("ProjectionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cineclubApp:projectionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
