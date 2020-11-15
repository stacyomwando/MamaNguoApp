<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
 */

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

//Users
Route::get('allUsers', 'UsersController@allUsers');
Route::get('getAvailableMamaNguo', 'UsersController@getAvailableMamaNguo');
Route::get('getHistory/{userId}', 'UsersController@getHistory');
Route::post('addUser', 'UsersController@storeUser');
Route::post('addMamaNguo', 'UsersController@storeMamaNguo');
Route::post('userLogin','UsersController@userLogin');
Route::post('makeRequest','UsersController@makeRequest');
Route::put('updateProfile','UsersController@updateProfile');

//Ratings
Route::post('addRating', 'RatingsController@addRating');

//Role
Route::post('addRole', 'RolesController@addRole');

//MamaNguo
Route::post('login', 'PassportController@login');
Route::post('register', 'PassportController@register');

Route::middleware('auth:api')->group(function () {
    Route::get('user','PassportController@details');
    Route::post('logout', 'PassportController@logout');
    Route::get('request', 'RequestedServicesController@show');
    Route::post('cancelRequest', 'RequestedServicesController@cancelRequest');
    Route::post('completeRequest', 'RequestedServicesController@completeRequest');
 
});