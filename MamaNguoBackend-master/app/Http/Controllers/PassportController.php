<?php

namespace App\Http\Controllers;


use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Hash;
use App\User;

class PassportController extends Controller
{
    /**
     * Handles Registration Request
     *
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function register(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'firstName' => ['required','string','max:255'],
            'lastName' => ['required','string','max:255'],
            'phoneNumber' => ['required','string','max:10'],
            'email' => ['required','email','unique:users'],
            'password' => ['required'],
            'idNumber' => ['required','min:8'],
            'location' => ['required'],
        ]);

        if ($validator->fails()) {          
            return response()->json(['error'=>$validator->errors()], 422);  

        }
        
        $user = User::create([
            'roleId' => 3,
            'firstName' => $request->firstName,
            'lastName' => $request->lastName,
            'phoneNumber' => $request->phoneNumber,
            'email' => $request->email,
            'password' => Hash::make($request->password),
            'idNumber' => $request->idNumber,
            'location' => $request->location,
        ]);
        
         $token = $user->createToken('userToken')->accessToken;
 
        return response()->json(['token' => $token], 200);
    }

    /**
     * Handles Login Request
     *
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function login(Request $request)
    {
        $credentials = [
            'phoneNumber' => $request->phoneNumber,
            'password' => $request->password
        ];
 
        if (auth()->attempt($credentials)) {
            $token = auth()->user()->createToken('userToken')->accessToken;
            return response()->json(['token' => $token], 200);
        } else {
            return response()->json(['error' => 'UnAuthorised'], 401);
        }
    }

    /**
     * Handles Logout Request
     *
     * @param Request $request
     * @return \Illuminate\Http\JsonResponse
     */

     public function logout(Request $request)
     {
        $token = $request->user()->token();
        $token->revoke();
    
        $response = 'You have been succesfully logged out!';
        return response()->json($response, 200);
     }

     /**
     * Returns Authenticated User Details
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function details()
    {
        $user = auth()->user();
        
        return response()->json($user, 200);
    }
}
