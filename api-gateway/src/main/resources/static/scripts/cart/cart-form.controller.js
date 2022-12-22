angular.module('cartForm')
    .controller('CartFormController', ["$http", '$state', '$stateParams', '$rootScope', '$scope', function ($http, $rootScope, $stateParams,$rootScope, $scope) {

            console.log("Cart Controller: CustomerId")
            console.log($rootScope.customerId);

			$scope.refreshCart = function()
			{
			    console.log("Refresh Cart");
			    console.log($scope.cartId);
				$http.get('api/cart/carts/' + $scope.cartId).then(function(data)
				{
				    $scope.carts = data.data;
				    console.log(data.data);
			    })
			}

			$scope.getCart = function() {
			    $http.get('api/cart/carts/getCartId/' + $rootScope.customerId).then(function (resp) {
                    $scope.cartId = resp.data;
                    console.log("Cart ID erhalten: ");
                    console.log(resp.data);

                    $scope.refreshCart();
                });
			}
			$scope.removeFromCart = function(cartItemId) {
				$http.post("api/cart/carts/removeCartItem/"
								+ cartItemId).then(function() {
					$scope.refreshCart();
				});
			}

			$scope.clearCart = function() {
				$http.post("api/cart/carts/removeAllItems/"
								+ $scope.cartId).then(function() {
					$scope.refreshCart();
				});
			}

			$scope.calculateGrandTotal = function() {
				var grandTotal = 0.0;
		//		for (var i = 0; i < $scope.carts.cartItem.length; i++)
		//			grandTotal = grandTotal + $scope.carts.cartItem[i].price;
				return grandTotal;

			}
		}
    ]);