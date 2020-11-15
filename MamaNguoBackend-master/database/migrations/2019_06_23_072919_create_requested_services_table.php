<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateRequestedServicesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('requested_services', function (Blueprint $table) {
            $table->bigIncrements('requestId');
            $table->bigInteger('userId')->unsigned();;//MamaNguo Id
            $table->bigInteger('requesteeId')->unsigned();;
            $table->string('description');
            $table->string('quantity');
            $table->string('cost');
            $table->double('longitude');
            $table->double('latitude');
            $table->string('location');
            $table->integer('totalCost');
            $table->string('status')->default('Incomplete');
            $table->timestamps();
            $table->foreign('userId')
                  ->references('userId')
                  ->on('users');
            $table->foreign('requesteeId')
                  ->references('userId')
                  ->on('users');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('requested_services');
    }
}
