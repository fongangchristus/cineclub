(function() {
    'use strict';

    angular
        .module('cineclubApp')
        .controller('SidebarController', SidebarController);

    SidebarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService'];

    function SidebarController ($state, Auth, Principal, ProfileService, LoginService) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.account = null;

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.menuClick = menuClick;
        vm.setContentHeight = setContentHeight;
        vm.$state = $state;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });
        
        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                console.log('Acount :' , vm.account);
            });
            
            console.log('Principal.isAuthenticated : ' , Principal.isAuthenticated());
            
            if(!Principal.isAuthenticated()){
            	vm.login();
            }
        }
        


        function login() {
            collapseNavbar();
            //LoginService.open();
            $state.go('login');
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
            console.log($state);
        }
        
        var $BODY = $('body'),
        $MENU_TOGGLE = $('#menu_toggle'),
        $SIDEBAR_MENU = $('#sidebar-menu'),
        $SIDEBAR_FOOTER = $('.sidebar-footer'),
        $LEFT_COL = $('.left_col'),
        $RIGHT_COL = $('.right_col'),
        $NAV_MENU = $('.nav_menu'),
        $FOOTER = $('footer');
        
        
        
        function setContentHeight() {
        	//console.log('$(window).height() : ', $(window).height());
        	
        	//console.log('.right_col', $('.right_col'));
        	// reset height
        	$RIGHT_COL.css('min-height', $(window).height()-40);
        	var $BODY = $('body');
        	var $FOOTER = $('footer');
        	var $SIDEBAR_FOOTER = $('.sidebar-footer');
        	
        	var bodyHeight = $BODY.outerHeight(),
        		footerHeight = $BODY.hasClass('footer_fixed') ? -10 : $FOOTER.height(),
        		leftColHeight = $LEFT_COL.eq(1).height() + $SIDEBAR_FOOTER.height(),
        		contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;

        	// normalize content
        	contentHeight -= $NAV_MENU.height() + footerHeight;

        	$RIGHT_COL.css('min-height', contentHeight);
        }
        setContentHeight();
        
       
        function menuClick(e){
        	
        	console.log('clicked - sidebar_menu');
            console.log( e.currentTarget);
            
//            var $current = angular.element(e.currentTarget);
//            if($current.is('.active')){
//            	
//            }else{
//            	$current.addClass('active')
//            }
            var $li = angular.element(e.currentTarget).parent();
           
            console.log($li );
            
            if ($li.is('.active')) {
                $li.removeClass('active active-sm');
                $('ul:first', $li).slideUp(function() {
                    vm.setContentHeight();
                });
            } else {
                // prevent closing menu if we are on child menu
                if (!$li.parent().is('.child_menu')) {
                    $SIDEBAR_MENU.find('li').removeClass('active active-sm');
                    $SIDEBAR_MENU.find('li ul').slideUp();
                }else
                {
    				if ( $BODY.is( ".nav-sm" ) )
    				{
    					$SIDEBAR_MENU.find( "li" ).removeClass( "active active-sm" );
    					$SIDEBAR_MENU.find( "li ul" ).slideUp();
    				}
    			}
                $li.addClass('active');

                $('ul:first', $li).slideDown(function() {
                	 vm.setContentHeight();
                });
            }          
        	
            setContentHeight();
        }
        
    }     

})();
