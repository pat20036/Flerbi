package com.pat.flerbi.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.pat.flerbi.view.main.ChatActivity
import com.pat.flerbi.R
import com.pat.flerbi.helpers.QueueInfo.location
import com.pat.flerbi.helpers.QueueInfo.nick
import com.pat.flerbi.helpers.QueueInfo.roomNr
import com.pat.flerbi.helpers.QueueInfo.searchSecurity
import com.pat.flerbi.model.FirstUser
import com.pat.flerbi.model.SecondUser
import com.pat.flerbi.model.QueueData
import com.pat.flerbi.view.main.MainActivity


class QueueService : Service() {

    private val uid: String = FirebaseAuth.getInstance().uid.toString()

    companion object {
        val matchTags = arrayListOf<String>()
        lateinit var roomKey: String
    }

    private lateinit var oppNick: String
    private lateinit var oppUid: String

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }
        queue()

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
        val pendingIntent = NavDeepLinkBuilder(applicationContext)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.main_nav_graph)
            .setDestination(R.id.queueFragment)
            .createPendingIntent()

        val notification = notificationBuilder.setOngoing(true)
            .setContentTitle("Searching")
            .setContentText("Searching active")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(101, notification)

        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    private fun queue() {

        val ref = FirebaseDatabase.getInstance().getReference("queue")
            .child(location + roomNr)


        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    val ref2 = FirebaseDatabase.getInstance()
                        .getReference("queue/${location + roomNr}/$uid")

                    val count = snapshot.childrenCount.toInt()

                    if (count == 1) {

                        ref2.setValue(SecondUser(nick, uid, location))

                        ref.addChildEventListener(object : ChildEventListener {
                            override fun onChildAdded(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {
                                val dane = snapshot.getValue(QueueData::class.java)

                                ref.addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val usersCount = snapshot.childrenCount

                                        if (usersCount == 2L) {
                                            if (dane?.uid != uid) {

                                                ref.removeEventListener(this)
                                                oppUid = dane?.uid.toString()
                                                oppNick = dane?.username.toString()
                                                val getKeyReference = FirebaseDatabase.getInstance()
                                                    .getReference("queue")
                                                    .child("${location + roomNr}/$oppUid")
                                                    .child("roomKey")
                                                getKeyReference.addListenerForSingleValueEvent(
                                                    object : ValueEventListener {
                                                        override fun onDataChange(snapshot: DataSnapshot) {
                                                            val key = snapshot.value.toString()
                                                            getTags()
                                                            val intent = Intent(
                                                                applicationContext,
                                                                ChatActivity::class.java
                                                            )
                                                            intent.putExtra("ROOM_KEY", key)
                                                            intent.putExtra("OPP_USERNAME", oppNick)
                                                            intent.putExtra("OPP_UID", oppUid)
                                                            intent.flags =
                                                                Intent.FLAG_ACTIVITY_NEW_TASK
                                                            searchSecurity = 0
                                                            startActivity(intent)
                                                            stopSelf()
                                                        }

                                                        override fun onCancelled(error: DatabaseError) {

                                                        }
                                                    })


                                            }
                                        } else if (usersCount > 2) {
                                            val databaseReference = FirebaseDatabase.getInstance()
                                                .getReference("queue/$roomNr/$uid")
                                            databaseReference.removeValue()
                                            //roomNr = +1 if you want to jump into next room/to fix
                                            return queue()
                                        }

                                    }

                                    override fun onCancelled(error: DatabaseError) {

                                    }

                                })

                            }

                            override fun onChildChanged(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {

                            }

                            override fun onChildRemoved(snapshot: DataSnapshot) {
                                ref.removeEventListener(this)
                            }

                            override fun onChildMoved(
                                snapshot: DataSnapshot,
                                previousChildName: String?
                            ) {

                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.d("Main", error.toString())
                            }
                        })
                    }
                    if (count == 2) {
                        roomNr += 1
                        return queue()

                    } else {
                        //error
                    }

                } else {
                    // Don't exist! Do something.
                    roomKey = getKey()

                    val ref2 = FirebaseDatabase.getInstance()
                        .getReference("queue/${location + roomNr}/$uid")
                    ref2.setValue(
                        FirstUser(
                            nick,
                            uid,
                            location,
                            roomKey
                        )
                    )

                    ref.addChildEventListener(object : ChildEventListener {
                        override fun onChildAdded(
                            snapshot: DataSnapshot,
                            previousChildName: String?
                        ) {
                            val dane = snapshot.getValue(QueueData::class.java)

                            ref.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val usersCount = snapshot.childrenCount

                                    if (usersCount == 2L) {
                                        if (dane?.uid != uid) {
                                            ref.removeEventListener(this)
                                            oppUid = dane?.uid.toString()
                                            oppNick = dane?.username.toString()
                                            getTags()
                                            val intent = Intent(
                                                applicationContext,
                                                ChatActivity::class.java
                                            )
                                            intent.putExtra("OPP_USERNAME", oppNick)
                                            intent.putExtra("OPP_UID", oppUid)
                                            intent.putExtra("ROOM_KEY", roomKey)
                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                            searchSecurity = 0
                                            startActivity(intent)
                                            stopSelf()
                                        }

                                    } else if (usersCount > 2) {
                                        val databaseReference = FirebaseDatabase.getInstance()
                                            .getReference("queue/$roomNr/$uid")
                                        databaseReference.removeValue()
                                        //roomNr = +1
                                        return queue()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {

                                }
                            })
                        }

                        override fun onChildChanged(
                            snapshot: DataSnapshot,
                            previousChildName: String?
                        ) {

                        }

                        override fun onChildRemoved(snapshot: DataSnapshot) {
                            ref.removeEventListener(this)
                        }

                        override fun onChildMoved(
                            snapshot: DataSnapshot,
                            previousChildName: String?
                        ) {

                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.d("Main", error.toString())

                        }

                    })


                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed, how to handle?
                Log.d("Main", error.toString())
            }
        })

    }

    private fun getTags() {

        val ref =
            FirebaseDatabase.getInstance().getReference("user-tags/$oppUid/tags")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val count = snapshot.childrenCount

                for (i in 0 until count) {
                    val ref2 = FirebaseDatabase.getInstance()
                        .getReference("user-tags/$oppUid/tags/$i")
                    ref2.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val dane = snapshot.value.toString()
                            matchTags.add(dane)
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun getKey(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..40)
            .map { allowedChars.random() }
            .joinToString("")
    }
}




