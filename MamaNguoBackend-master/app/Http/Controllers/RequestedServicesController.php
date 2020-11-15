<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class RequestedServicesController extends Controller
{

    public function getRequest($id){

    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        
        

       
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\JsonResponse
     */
    public function show()
    {
        $userId = auth()->user()->userId;

        // $request = DB::table('requested_services')
        //         ->where('status','Incomplete',['userID', '=', $userId])
        //         ->first();

        // $request = DB::table('requested_services')
        //         ->select('users.*', 'requested_services.*')
        //         ->join(DB::raw("(SELECT * FROM users
        //                 WHERE userId = $userId) as users"),
        //                 function($join){
        //                     $join->on("users.userId", "=", "requested_services.requesteeId");
        //                 })
        //         ->get();

        $request = DB::table('requested_services')
                ->select('users.*','requested_services.*')
                ->join('users', 'users.userId', '=', 'requested_services.requesteeId')
                ->where('requested_services.status','=','Incomplete'&&['requested_services.userId','=', $userId])
                ->first();

        // return response()->json($userId, 200);
        if (empty($request)) {
            return response()->json($request, 404);
        }

        return response()->json($request, 200);

         // $users = DB::table('requests')
        //         ->join('users', 'users.userId', '=', $userId)
        //         ->join('orders', 'users.id', '=', 'orders.user_id')
        //         ->select('users.*', 'contacts.phone', 'orders.price')
        //         ->get();
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request)
    {
        
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }

    public function cancelRequest(Request $request)
    {
        $userId = auth()->user()->userId;

        $status = [
            'status' => $request->status
        ];

        $updateQuery = DB::table('requested_services')
            ->where('userId', $userId)
            ->update($status);
        if ($updateQuery) {
            return response()->json(['message' => "Cancelled"], 200);
        }else{
            return response()->json(['message' => "Not Cancelled"], 404);
        }
    }

    public function completeRequest(Request $request)
    {
        $userId = auth()->user()->userId;

        $status = [
            'status' => $request->status
        ];

        $updateQuery = DB::table('requested_services')
            ->where('userId', $userId)
            ->update($status);
        
            // return response()->json($userId, 200);
        if ($updateQuery) {
            return response()->json(['message' => "Completed"], 200);
        }else{
            return response()->json(['message' => "Not Completed"], 404);
        }
    }
}
