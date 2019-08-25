package com.codeace.exploreease.helpers

import android.content.Context
import android.text.TextUtils
import android.util.DisplayMetrics
import android.widget.Toast
import com.codeace.exploreease.entities.Comment
import com.codeace.exploreease.entities.PlaceLocation
import com.codeace.exploreease.entities.UserPost
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern


val EMAIL_REGEX =
    "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

val imagePickCode = 1000

val TAG = "DataTest"

var comments: List<Comment> = listOf()

var placeList: MutableList<PlaceLocation> = mutableListOf()

var userPost: UserPost? = null

var listImage: MutableList<String> = mutableListOf(
    "https://firebasestorage.googleapis.com/v0/b/visit-nepal-2020-ba341.appspot.com/o/location%2FGarden%20of%20Dreams.jpg?alt=media&token=eb93231a-1816-4551-86c9-e67711aceecc",
    "https://firebasestorage.googleapis.com/v0/b/visit-nepal-2020-ba341.appspot.com/o/location%2FNagarjun%20Forest%20Reserve.jpg?alt=media&token=0e74953c-b91b-4f3f-b92f-32ef1efe4418",
    "https://firebasestorage.googleapis.com/v0/b/visit-nepal-2020-ba341.appspot.com/o/location%2FPokhara.png?alt=media&token=55352497-543f-4c38-8c6f-09bcfe74181f",
    "https://firebasestorage.googleapis.com/v0/b/visit-nepal-2020-ba341.appspot.com/o/location%2FPashupatinath%20Temple.png?alt=media&token=6742a5fc-90c7-4eb0-993b-6e7c8121f510"
)


fun convertDpToPixel(dp: Float, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}


fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

inline fun checkMail(
    email: String,
    editText: TextInputEditText,
    errorMessage: String,
    func: () -> Unit
) {
    if (Pattern.matches(EMAIL_REGEX, email)) {
        func()
    } else {
        editText.error = errorMessage
    }
}

inline fun checkPassword(
    password: String,
    editText: TextInputEditText,
    errorMessage: String,
    func: () -> Unit
) {
    if (TextUtils.isEmpty(password)) {
        editText.error = errorMessage
    } else {
        func()
    }
}

//fun sort(list: MutableList<FoodData>, data: FoodData) {
//    var i = list.size
//    do {
//        if (i <= 0) {
//            list.add(0, data)
//            break
//        } else if (list[i - 1].food_name.compareTo(data.food_name) < 1) {
//            list.add(i, data)
//            break
//        }
//        i--
//    } while (list[i].food_name.compareTo(data.food_name) > -1)
//}
