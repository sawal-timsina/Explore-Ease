package com.codeace.exploreease.workers

import android.os.AsyncTask
import android.util.Log
import com.codeace.exploreease.entities.*
import com.google.firebase.database.DataSnapshot

class PostWorkers(
    private val onPostExecuteComplete: (List<UserPost>) -> Unit,
    private val onComplete: (List<Comment>, List<Like>, List<Post>, List<User>) -> Unit
) :
    AsyncTask<DataSnapshot, Void, List<UserPost>>() {
    override fun doInBackground(vararg p0: DataSnapshot?): List<UserPost> {
        val listPlaceLocation: MutableList<PlaceLocation> = mutableListOf()
        val listUserPost: MutableList<UserPost> = mutableListOf()
        val listLikes: MutableList<Like> = mutableListOf()
        val listUser: MutableList<User> = mutableListOf()
        val listPost: MutableList<Post> = mutableListOf()
        val listComment: MutableList<Comment> = mutableListOf()

        var dataCount = 0
        var userPostInt = 0
        var userPost = 0
        var userC: Int
        var locationIndex = 0

        p0[0]?.children?.forEach { it0 ->
            when (dataCount) {
                0 -> {
                    Log.d("it0", it0.toString().plus("\n"))
                    it0.children.forEach { location ->

                        locationIndex++
                    }
                }
                1 -> {
                    it0.children.forEach { userUploadsR ->
                        userUploadsR.children.forEach { userUploads ->
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
                        }
                        userPostInt++
                    }
                }
                2 -> {
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

        listPost.forEach { post ->
            val _userPost = UserPost()
            val _likeList: MutableList<Like> = mutableListOf()
            listLikes.forEach { like ->
                if (post.key == like.key) {
                    _likeList.add(like)
                }
            }
            _userPost.likes = _likeList

            val _commentList: MutableList<Comment> = mutableListOf()
            listComment.forEach { comment ->
                if (post.key == comment.key) {
                    _commentList.add(comment)
                }
            }
            _userPost.comments = _commentList

            listUser.forEach { user ->
                if (post.id == user.uid) {
                    _userPost.user = user
                    _userPost.post = post
                }
            }

            listUserPost.add(_userPost)
        }

        onComplete(listComment, listLikes, listPost, listUser)

        return listUserPost
    }

    override fun onPostExecute(result: List<UserPost>) {
        super.onPostExecute(result)
        onPostExecuteComplete(result)
    }
}