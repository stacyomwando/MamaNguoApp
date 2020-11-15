<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class RequestedService extends Model
{
    protected $table = 'requested_services';

    public function user()
    {
        return $this->belongsTo('App\User', 'userId', 'userId');
    }
}
