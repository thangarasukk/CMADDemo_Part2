(function(){
	var app = angular.module('Airlines',['ngRoute']);
	
	app.config(['$routeProvider', 
	    function($routeProvider){
			$routeProvider.
                when('/signup',{
					templateUrl:'signup.html',
					controller:'signupController'
				}).
				when('/users',{
					templateUrl:'users.html',
					controller:'UserController'
				}).
				when('/products',{
					templateUrl:'products.html',
					controller:'ProductController'
				}).
				otherwise({
					redirectTo: '/'
				});
		}]);
	
	app.controller('UserController',function($http, $log, $scope){
		var controller = this;
		$scope.users=[];
		$scope.loading = true;
		$log.debug("Getting users...");
		$http.get('rest/user').
		  success(function(data, status, headers, config) {
			  $scope.users = data;
			  $scope.loading = false;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.loading = false;
			  $scope.error = status;
		  });
		
		$scope.addUser2 = function(user){
			$log.debug(user);
			$.post("rest/user",user,function(data){
				$log.debug(data);
				$scope.users.push(user);
				$scope.$digest();
			});
		}
		$scope.addUser = function(user){
			$http.post("rest/user",user)
			.success(function(data){
				$log.debug(data);
				$scope.users.push($scope.user);
			});
		};
		
		$scope.editUser = function(user){
			console.log(user);
			$scope.user = user;
			$scope.showEditForm=true;
			$scope.showAddForm=false;
		}
		
		$scope.updateUser = function(user){
			$log.debug(user);
			$http.put('rest/user',user).
			  success(function(data, status, headers, config) {
				  console.log(data);
				  $scope.showEditForm=false;
			  }).
			  error(function(data, status, headers, config) {
				  $scope.error = status;
				  $scope.showEditForm=false;
			  });

		}
	});
	
	app.controller('AddController',function($http, $log, $scope){
		
	});
	
	app.controller('ProductController',function($http, $log, $scope){
		var controller = this;
		$scope.products=[];
		$log.debug("Getting products...");

		$http.get('product.json').
		  success(function(data, status, headers, config) {
			  $scope.products = data;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.error = status;
		  });
	});
	
})();

