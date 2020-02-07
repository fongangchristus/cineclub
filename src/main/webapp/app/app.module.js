(function() {
    // 'use strict';

    angular
        .module('cineclubApp', [
            'ngStorage',
            'ngAnimate',
            'toaster',
            'ngSanitize',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            'ds.objectDiff',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'ngIntlTelInput',
            'angular-loading-bar',
            'dualmultiselect',
            'ngFileUpload',
            'ui.checkbox',
            'chart.js'
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
    }
})();
