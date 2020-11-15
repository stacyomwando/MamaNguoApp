<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
            $table->bigIncrements('userId');
            $table->integer('roleId'); //MamaNguo or a user
            $table->string('firstName');
            $table->string('lastName');
            $table->string('phoneNumber')->unique();
            $table->string('email')->unique()->nullable();
            $table->string('password');
            $table->string('location')->nullable();
            $table->string('idNumber')->unique()->nullable();
            $table->string('isUserRegistered')->default('0');
            $table->string('isUserVerified')->default('0');
            $table->string('isUserSuspended')->default('0');
            $table->string('isUserLoggedIn')->default('0');
            $table->string('status')->default('0');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('users');
    }
}
