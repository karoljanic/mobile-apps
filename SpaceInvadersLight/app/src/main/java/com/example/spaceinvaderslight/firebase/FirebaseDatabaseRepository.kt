package com.example.spaceinvaderslight.firebase

import android.util.Log
import com.example.spaceinvaderslight.Constants
import com.example.spaceinvaderslight.model.Bullet
import com.example.spaceinvaderslight.model.Invader
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

object FirebaseDatabaseRepository {
    val databaseReference = FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://spaceinvaders-48564-default-rtdb.firebaseio.com/")

    fun createGame(gameId: String) {
        databaseReference.child(gameId).child(DatabaseKeys.HOST).child(DatabaseKeys.POSITION_X)
            .setValue(Constants.PLAYER_LEFT_INITIAL_X)
        databaseReference.child(gameId).child(DatabaseKeys.HOST).child(DatabaseKeys.POSITION_Y)
            .setValue(Constants.PLAYER_LEFT_INITIAL_Y)
        databaseReference.child(gameId).child(DatabaseKeys.HOST).child(DatabaseKeys.VISIBLE)
            .setValue(false)
        databaseReference.child(gameId).child(DatabaseKeys.HOST).child(DatabaseKeys.LIVES)
            .setValue(3)
        databaseReference.child(gameId).child(DatabaseKeys.HOST).child(DatabaseKeys.BULLET)
            .child(DatabaseKeys.POSITION_X).setValue(0)
        databaseReference.child(gameId).child(DatabaseKeys.HOST).child(DatabaseKeys.BULLET)
            .child(DatabaseKeys.POSITION_Y).setValue(0)
        databaseReference.child(gameId).child(DatabaseKeys.HOST).child(DatabaseKeys.BULLET)
            .child(DatabaseKeys.VISIBLE).setValue(false)

        databaseReference.child(gameId).child(DatabaseKeys.GUEST).child(DatabaseKeys.POSITION_X)
            .setValue(Constants.PLAYER_RIGHT_INITIAL_X)
        databaseReference.child(gameId).child(DatabaseKeys.GUEST).child(DatabaseKeys.POSITION_Y)
            .setValue(Constants.PLAYER_RIGHT_INITIAL_Y)
        databaseReference.child(gameId).child(DatabaseKeys.GUEST).child(DatabaseKeys.VISIBLE)
            .setValue(false)
        databaseReference.child(gameId).child(DatabaseKeys.GUEST).child(DatabaseKeys.LIVES)
            .setValue(3)
        databaseReference.child(gameId).child(DatabaseKeys.GUEST).child(DatabaseKeys.BULLET)
            .child(DatabaseKeys.POSITION_X).setValue(0)
        databaseReference.child(gameId).child(DatabaseKeys.GUEST).child(DatabaseKeys.BULLET)
            .child(DatabaseKeys.POSITION_Y).setValue(0)
        databaseReference.child(gameId).child(DatabaseKeys.GUEST).child(DatabaseKeys.BULLET)
            .child(DatabaseKeys.VISIBLE).setValue(false)

        databaseReference.child(gameId).child(DatabaseKeys.SCORE).setValue(0)
        databaseReference.child(gameId).child(DatabaseKeys.END_GAME).setValue(false)

        databaseReference.child(gameId).child(DatabaseKeys.INVADERS).setValue(null)
        databaseReference.child(gameId).child(DatabaseKeys.INVADER_BULLETS).setValue(null)
    }

    fun movePlayer(gameId: String, role: String, x: Float, y: Float) {
        databaseReference.child(gameId).child(role).child(DatabaseKeys.POSITION_X)
            .setValue(x)
        databaseReference.child(gameId).child(role).child(DatabaseKeys.POSITION_Y)
            .setValue(y)
    }

    fun onPlayerXPositionUpdate(gameId: String, role: String, onUpdate: (x: Float) -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Float::class.java)
                if (value != null) {
                    onUpdate(value)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("SpaceInvaders", "$databaseError")
            }
        }

        databaseReference.child(gameId).child(role).child(DatabaseKeys.POSITION_X)
            .addValueEventListener(valueEventListener)
    }

    fun enablePlayer(gameId: String, role: String) {
        databaseReference.child(gameId).child(role).child(DatabaseKeys.VISIBLE)
            .setValue(true)
    }

    fun disablePlayer(gameId: String, role: String) {
        databaseReference.child(gameId).child(role).child(DatabaseKeys.VISIBLE)
            .setValue(false)
    }

    fun onPlayerVisibleUpdate(gameId: String, role: String, onUpdate: (visible: Boolean) -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Boolean::class.java)
                if (value != null) {
                    onUpdate(value)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("SpaceInvaders", "$databaseError")
            }
        }

        databaseReference.child(gameId).child(role).child(DatabaseKeys.VISIBLE)
            .addValueEventListener(valueEventListener)
    }

    fun movePlayerBullet(gameId: String, role: String, x: Float, y: Float) {
        databaseReference.child(gameId).child(role).child(DatabaseKeys.BULLET)
            .child(DatabaseKeys.POSITION_X).setValue(x)
        databaseReference.child(gameId).child(role).child(DatabaseKeys.BULLET)
            .child(DatabaseKeys.POSITION_Y).setValue(y)
    }

    fun setPlayerBulletVisibility(gameId: String, role: String, isVisible: Boolean) {
        databaseReference.child(gameId).child(role).child(DatabaseKeys.BULLET)
            .child(DatabaseKeys.VISIBLE).setValue(isVisible)
    }

    fun onPlayerBulletXUpdate(gameId: String, role: String, onUpdate: (x: Float) -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                onUpdate(
                    dataSnapshot.getValue(Float::class.java)!!,
                )
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("SpaceInvaders", "$databaseError")
            }
        }

        databaseReference.child(gameId).child(role).child(DatabaseKeys.BULLET).child(DatabaseKeys.POSITION_X)
            .addValueEventListener(valueEventListener)
    }

    fun onPlayerBulletYUpdate(gameId: String, role: String, onUpdate: (y: Float) -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                onUpdate(
                    dataSnapshot.getValue(Float::class.java)!!,
                )
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("SpaceInvaders", "$databaseError")
            }
        }

        databaseReference.child(gameId).child(role).child(DatabaseKeys.BULLET).child(DatabaseKeys.POSITION_Y)
            .addValueEventListener(valueEventListener)
    }

    fun onPlayerBulletVisibilityUpdate(gameId: String, role: String, onUpdate: (visibility: Boolean) -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                onUpdate(
                    dataSnapshot.getValue(Boolean::class.java)!!
                )
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("SpaceInvaders", "$databaseError")
            }
        }

        databaseReference.child(gameId).child(role).child(DatabaseKeys.BULLET).child(DatabaseKeys.VISIBLE)
            .addValueEventListener(valueEventListener)
    }

    fun updateInvaderArray(gameId: String, invaders: ArrayList<Invader>) {
        databaseReference.child(gameId).child(DatabaseKeys.INVADERS).setValue(invaders)
    }

    fun onInvadersUpdate(gameId: String, onUpdate: (invaders: ArrayList<Invader>) -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val invaders = dataSnapshot.getValue<ArrayList<Invader>>()

                if(invaders != null)
                    onUpdate(invaders)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("SpaceInvaders", "$databaseError")
            }
        }

        databaseReference.child(gameId).child(DatabaseKeys.INVADERS).addValueEventListener(valueEventListener)
    }

    fun updateInvaderBulletsArray(gameId: String, invaderBullets: ArrayList<Bullet>) {
        databaseReference.child(gameId).child(DatabaseKeys.INVADER_BULLETS).setValue(invaderBullets)
    }

    fun onInvaderBulletsUpdate(gameId: String, onUpdate: (invaderBullets: ArrayList<Bullet>) -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val invaderBullets = dataSnapshot.getValue<ArrayList<Bullet>>()

                if(invaderBullets != null)
                    onUpdate(invaderBullets)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("SpaceInvaders", "$databaseError")
            }
        }

        databaseReference.child(gameId).child(DatabaseKeys.INVADER_BULLETS).addValueEventListener(valueEventListener)
    }

    fun updateLives(gameId: String, role: String, lives: Int) {
        databaseReference.child(gameId).child(role).child(DatabaseKeys.LIVES).setValue(lives)
    }

    fun onLivesUpdate(gameId: String, role: String, onUpdate: (lives: Int) -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val lives = dataSnapshot.getValue(Int::class.java)
                if(lives!= null)
                    onUpdate(lives)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("SpaceInvaders", "$databaseError")
            }
        }

        databaseReference.child(gameId).child(role).child(DatabaseKeys.LIVES).addValueEventListener(valueEventListener)
    }

    fun endGame(gameId: String) {
        databaseReference.child(gameId).child(DatabaseKeys.END_GAME).setValue(true)
    }

    fun onGameEnd(gameId: String, callback: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val endGame = dataSnapshot.getValue(Boolean::class.java)
                if(endGame!= null && endGame == true)
                    callback()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("SpaceInvaders", "$databaseError")
            }
        }

        databaseReference.child(gameId).child(DatabaseKeys.END_GAME).addValueEventListener(valueEventListener)
    }
}
