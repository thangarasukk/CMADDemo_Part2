(function(){
	var app = angular.module('CMAD', ['ngRoute']).run(function($rootScope){
		$rootScope.user = {};
		$rootScope.user.name = "default";
		$rootScope.user.isAuthenticated = false;
	});

	


	app.controller('HeaderController',['$http' ,'$location', '$rootScope',  function($http,$location,$rootScope){
		this.user = {};		
		this.searchField = {};

		$http.get("webapi/user/").then(
			function(response){
				//Sucess Callback
				str = JSON.stringify(response);
				console.log("[AniB]: Sucess Callback: " +str);
				
			},
			function(response){
				//failure callback
				str = JSON.stringify(response);
				console.log("[AniB]: Failure Callback: " +response.status);
				if (response.status === 401 || response.status === 404 ) {
					$location.path('/login');
				}
			});
		
		this.search = function(){
			str = JSON.stringify(this.search);
			console.log("[AniB]: search(): " +str);
			this.search = {};
		};
		this.logout = function(){
			console.log("[AniB]: Logout");
			$http.get("LogoutServlet").then(
			function(response){
				//Sucess Callback
				str = JSON.stringify(response);
				console.log("[AniB]: Sucess Callback: " +str);
				if (response.status == 200) {
					$rootScope.user.name = "default";
					$rootScope.user.isAuthenticated = false;
					$location.path('/login');
				};
				
			},
			function(response){
				//failure callback
				str = JSON.stringify(response);
				console.log("[AniB]: Failure Callback: " +response.status);
				if (response.status === 401 || response.status === 404 ) {
					$location.path('/login');
				}
			});
		};


		
	}]);

	app.controller('SignUpController', ['$http', function($http){
		this.signup = {};
		console.log("[AniB]: signupCtrl: " +this.signup);
		this.addUser = function(){
			str = JSON.stringify(this.signup);
			console.log("[AniB]: addUser(): " +str);
		};
	}]);

	app.controller('LoginController', ['$http' ,'$location', '$rootScope', function($http, $location,$rootScope){
		this.login = {};
		console.log("[AniB]: loginCtrl: ");
		this.checkUser = function(){
			str = JSON.stringify(this.login);
			console.log("[AniB]: checkUser(): " +str);
			$http.post("webapi/login", this.login).then(
				function(response){
					//Sucess Callback
					str = JSON.stringify(response);
					console.log("[AniB]: Sucess Callback: " +str);
					
					$rootScope.user.name = response.data.username;
					$rootScope.user.isAuthenticated = true;

					$location.path('/home');
				},
				function(response){
					str = JSON.stringify(response);
					console.log("[AniB]: Failure Callback: " +response.status);
				});
			this.login = {};
		};
	}]);

	app.controller('HomeController', ['$http', function($http){
		console.log("HomeController: ");
	}]);

	
})();
