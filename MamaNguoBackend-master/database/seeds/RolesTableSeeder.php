<?php

use Illuminate\Database\Seeder;

class RolesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('roles')->insert([
            [
                'roleId' => 1,
                'description' => 'Admin'
            ],
            [
                'roleId' => 2,
                'description' => 'User'
            ],
            [
                'roleId' => 3,
                'description' => 'MamaNguo'
            ]
        ]);
    }
}
