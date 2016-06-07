(function(){
	var app = angular.module('CMAD', ['ngRoute','angularUtils.directives.dirPagination']).run(function($rootScope){
		$rootScope.user = {};
		$rootScope.user.name = "default";
		$rootScope.user.isAuthenticated = true;
	});

    app.controller('SignUpController',function($http, $log, $scope){
		var controller = this;
		$scope.user=[];
		$log.debug("Getting blogs...");
		console.log("[AniB]: blog.js :: SignupController");

 		$scope.addUser = function(user){
     		$log.debug("SignupController...in addUser");
 		    $log.debug(user);

            var password_confirm = user.password.localeCompare(user.confirmpassword);

            $log.debug("user.password   " + "user.confirmpassword");
            
            if(password_confirm == 0){
                $log.debug("pass word matched");
                var data = JSON.stringify(
                    {
                      name: user.name,
                      email:user.email,
                      password: user.password,
                      age:user.age
                    });
                $log.debug(data);
                $log.debug("before rest/user");
                $http.post("rest/user",data)
        			.success(function(data){
        				$log.debug(data);
                        $scope.showEditForm=true;
                        $scope.showAddForm=false;
        			})
                    .error(function(data, status, headers, config) {
                          $log.debug("error occured");
        				  $scope.error = status;
        				  $scope.showEditForm=false;
        			});
            }
            else{
                console.log("passwords are not matched");
            }

		};
	});
    
	app.controller('AllBlogsController',function($http, $log, $scope, GlobalStroage){
		var controller = this;
		$scope.blogs=[];
		console.log("[AniB]: blog.js :: AllBlogsController");
        this.selectedBlog;

		$http.get('rest/blog').
		  success(function(data, status, headers, config) {
			  $scope.blogs = data;
		  }).
		  error(function(data, status, headers, config) {
			  $scope.error = status;
		  });

        $scope.updateSelectedBlog = function(blog){
    		this.selectedBlog = blog;
    		$log.debug("inside updateSelectedBlog");
            $log.debug(blog);
            GlobalStroage.setSelectedBlogId(blog.id);
            $log.debug(GlobalStroage.getSelectedBlogId());
    	}
        
        $scope.convertJSONDateToJavascriptDate = function(jsonDate){
        	return new Date(jsonDate).toUTCString();
        }
	});

	app.controller('SingleBlogController',function($http, $log, $scope, GlobalStroage){
		var controller = this;
        var selectedBlogId = GlobalStroage.getSelectedBlogId();
		$scope.blog=[];
        $scope.postedDate="";
		$scope.loading = true;
		console.log("[AniB]: blog.js :: SingleBlogController");
		$http.get('rest/blog/'+selectedBlogId).
		  success(function(data, status, headers, config) {
			  $scope.blog = data;
			  $scope.loading = false;
              $scope.postedDate = $scope.convertJSONDateToJavascriptDate($scope.blog.postedDate);
              $log.debug($scope.postedDate);
		  }).
		  error(function(data, status, headers, config) {
			  $scope.loading = false;
			  $scope.error = status;
		  });

        $scope.convertJSONDateToJavascriptDate = function(jsonDate){
        	/* var dateStr = JSON.parse(jsonDate); */
        	return new Date(jsonDate).toUTCString();
        };

            
	});

    app.service('GlobalStroage', function($log) {
        this.selectedBlogId = "defaultblogID";
        
        this.getSelectedBlogId = function(){
                $log.debug("Inside getSelectedBlogId..");
                return this.selectedBlogId;
            }        
        this.setSelectedBlogId = function(selectedBlogId){
                $log.debug("Inside setSelectedBlogId..");
                this.selectedBlogId = selectedBlogId;
            }   
    });

	app.controller('SingleBlogPostController',function($http, $log, $scope, GlobalStroage){
		var controller = this;
		$scope.blog=[];
		$scope.loading = true;
		console.log("[AniB]: blog.js :: SingleBlogPostController");
        $scope.showEditForm=false;
        $scope.showAddForm=true;
        $scope.blog.title="";
        $scope.blog.synopsis="";
        $scope.blog.content="";
        $scope.blog.posterUrl="images/content_image/default.jpg";
        $scope.blog.tags="";
        $scope.blog.postedUserName="test_name";
        $scope.blog.postedUserId="5722ffa441fbd6d042b07202";

        $log.debug(GlobalStroage.getSelectedBlogId());

 		$scope.addBlog = function(blog){
     		$log.debug("SingleBlogPostController...in addBlog");
 		    $log.debug(blog);

            var data = JSON.stringify(
                    {
                      title: blog.title,
                      synopsis:blog.synopsis,
                      content: blog.content,
                      posterUrl:blog.posterUrl,
                      tags: blog.tags,
                      postedUserName : blog.postedUserName,
                      postedUserId : blog.postedUserId
                    });
                  alert( data); 

            $log.debug(data);
            $log.debug("rest/blog");
            $http.post("rest/blog",data)
    			.success(function(data){
    				$log.debug(data);
                    $scope.showEditForm=true;
                    $scope.showAddForm=false;
    			})
                .error(function(data, status, headers, config) {
                      $log.debug("error occured");
    				  $scope.error = status;
    				  $scope.showEditForm=false;
    			});
		};
	});


	app.controller('LoginController', ['$http' ,'$location', '$rootScope', function($http, $location,$rootScope){
		this.login = {};
		console.log("[AniB]: blog.js :: LoginController");
		this.checkUser = function(){
			str = JSON.stringify(this.login);
			console.log("[AniB]: checkUser(): " +str);
			$http.post("rest/login", this.login).then(
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
    
	app.controller('HeaderController',['$http' ,'$location', '$rootScope',  function($http,$location,$rootScope){
		this.user = {};		
		this.searchField = {};
		console.log("[AniB]: blog.js :: HeaderController");
		$http.get("rest/user/").then(
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
    
})();

