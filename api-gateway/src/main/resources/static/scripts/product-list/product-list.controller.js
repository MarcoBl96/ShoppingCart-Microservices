'use strict';

angular.module('productList')
    .controller('ProductListController', ['$http', '$scope', '$stateParams','$state', function ($http, $scope,$stateParams,$state) 
	{
		var self = this;

        $http.get('api/product/products/getAllProducts').then(function (resp) {
            self.products = resp.data;
        });
		
        $scope.delete = function(id) 
		{
            $http.post('/api/product/products/admin/delete/' + id).then(function () 
			{
				$state.go("productList",{},{reload: "productList"})
			});
        };
        
		$scope.addToCart = function(product) 
		{
          	console.log(product);
          	console.log(JSON.stringify(product))

          	$http.get('/users/GetCustomerId').then(function(customerresponse) 
			{
                if (customerresponse.data) 
				{
                    console.log(customerresponse.data);
                    $http.put('api/cart/carts/addProductToCart/' + customerresponse.data, JSON.stringify(product))
                         .then(function() 
						 {
                            alert("Added Successfully");
                         })
                } 
				else 
				{
                    alert("Please log in!");
                    console.log('Can not get username');
                }
            });
        }
    }
]);
