package com.codeace.exploreease.workers

import android.os.AsyncTask
import android.util.Log
import com.codeace.exploreease.entities.*
import com.google.firebase.database.DataSnapshot

class PostWorkers(private val onPostExecuteComplete: (List<UserPost>) -> Unit) :
    AsyncTask<DataSnapshot, Void, List<UserPost>>() {
    override fun doInBackground(vararg p0: DataSnapshot?): List<UserPost> {
        val listUserPost: MutableList<UserPost> = mutableListOf()
        val listLikes: MutableList<Like> = mutableListOf()
        val listUser: MutableList<User> = mutableListOf()
        val listPost: MutableList<Post> = mutableListOf()
        val listComment: MutableList<Comment> = mutableListOf()

        var dataCount = 0
        var userPostInt = 0
        var userPost = 0
        var userC: Int

        /*
*/

        p0[0]?.children?.forEach { it0 ->
            when (dataCount) {
                0 -> {
                    Log.d("it0", it0.toString().plus("\n"))
                    it0.children.forEach { userUploadsR ->
                        Log.d(
                            "userUploadsR",
                            userUploadsR.toString().plus(" : ${userUploadsR.key}")
                        )
                        userUploadsR.children.forEach { userUploads ->
                            Log.d(
                                "userUploads",
                                userUploads.toString().plus(" : ${userUploads.key}")
                            )
                            when (userPostInt) {
                                0 -> {
                                    val _comment = Comment()
                                    _comment.key = userUploads.key!!
                                    userUploads.children.forEach { uid ->
                                        _comment.uid = uid.key!!
                                        uid.children.forEach { key ->
                                            key.children.forEach { comment ->
                                                _comment.comment =
                                                    comment.getValue<String>(String::class.java)!!
                                                listComment.add(_comment)
                                            }
                                        }

                                    }
                                }
                                1 -> {
                                    val _like = Like()
                                    _like.key = userUploads.key!!
                                    userUploads.children.forEach { uid ->
                                        _like.uid = uid.key!!
                                        listLikes.add(_like)
                                    }
                                }
                                2 -> {
                                    val _post = Post()
                                    _post.key = userUploads.key!!
                                    userUploads.children.forEach { postD ->
                                        Log.d("userUploadspostD", postD.toString())
                                        when (userPost) {
                                            0 -> {
                                                _post.dateTime =
                                                    postD.getValue<String>(String::class.java)!!
                                            }
                                            1 -> {
                                                _post.description =
                                                    postD.getValue<String>(String::class.java)!!
                                            }
                                            2 -> {
                                                _post.id =
                                                    postD.getValue<String>(String::class.java)!!
                                            }
                                            3 -> {
                                                _post.imageUri =
                                                    postD.getValue<String>(String::class.java)!!
                                            }
                                            5 -> {
                                                _post.locationId =
                                                    postD.getValue<String>(String::class.java)!!
                                            }
                                            6 -> {
                                                _post.locationName =
                                                    postD.getValue<String>(String::class.java)!!
                                            }
                                        }
                                        userPost++
                                    }
                                    listPost.add(_post)
                                }
                            }
                            /*userUploads.children.forEach { userUploadsC ->
                                when (userPostInt) {
                                    0 -> {
                                        Log.d("userUploadsC0",userUploadsC.toString().plus("\n"))
                                        userUploadsC.children.forEach { comment ->
                                            Log.d("comment", comment.toString())
                                            comment.children.forEach { commentC ->

                                                userCommentsPostC++
                                            }
                                        }
                                        userComments++
                                    }
                                    1 -> {
                                        Log.d("userUploadsC1",userUploadsC.toString().plus("\n"))
                                        userUploadsC.children.forEach { likes ->
                                            Log.d("likes", likes.toString())
                                        }
                                        userLikes++
                                    }
                                    2 -> {
                                        Log.d("userUploadsC2",userUploadsC.toString().plus("\n"))
                                        val _post = Post()
                                        userUploadsC.children.forEach { post ->
                                            Log.d("post", post.getValue<String>(String::class.java)!!)
                                            when (userPost) {
                                                0 -> {
                                                    _post.dateTime = post.getValue<String>(String::class.java)!!
                                                }
                                                1 -> {
                                                    _post.description = post.getValue<String>(String::class.java)!!
                                                }
                                                2 -> {
                                                    _post.id = post.getValue<String>(String::class.java)!!
                                                }
                                                3 -> {
                                                    _post.imageUri = post.getValue<String>(String::class.java)!!
                                                }
                                                4 -> {
                                                    _post.key = post.getValue<String>(String::class.java)!!
                                                }
                                                5 -> {
                                                    _post.locationId = post.getValue<String>(String::class.java)!!
                                                }
                                                6 -> {
                                                    _post.locationName = post.getValue<String>(String::class.java)!!
                                                }
                                            }
                                            userPost++
                                        }
                                    }
                                }
                            }*/
                        }
                        userPostInt++
                    }
                }
                1 -> {
                    it0.children.forEach { usersR ->
                        val _user = User()
                        _user.uid = usersR.key!!
                        userC = 0
                        usersR.children.forEach { users ->
                            when (userC) {
                                2 -> {
                                    _user.points = users.getValue<Int>(Int::class.java)!!
                                }
                                3 -> {
                                    _user.userAvatar = users.getValue<String>(String::class.java)!!
                                }
                                4 -> {
                                    _user.userName = users.getValue<String>(String::class.java)!!
                                }
                            }
                            userC++
                        }
                        listUser.add(_user)
                    }
                }
            }
            dataCount++
        }



        return listUserPost
    }

    override fun onPostExecute(result: List<UserPost>) {
        super.onPostExecute(result)
        onPostExecuteComplete(result)
    }
}