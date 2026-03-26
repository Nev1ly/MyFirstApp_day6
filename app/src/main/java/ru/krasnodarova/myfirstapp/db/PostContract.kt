package ru.krasnodarova.myfirstapp.db
import android.provider.BaseColumns
import android.provider.BaseColumns._ID

object PostContract {
    const val TABLE_NAME = "posts"

    // Убираем : BaseColumns из объекта и добавляем его к Companion
    object Columns {
        // Убираем объявление val _ID: String
        const val AUTHOR = "author"
        const val AUTHOR_ID = "author_id"
        const val CONTENT = "content"
        const val PUBLISHED = "published"
        const val LIKED_BY_ME = "liked_by_me"
        const val LIKES = "likes"
        const val SHARES = "shares"
        const val VIEWS = "views"
        const val VIDEO = "video"

        // Теперь используем константу из BaseColumns напрямую
        val ALL_COLUMNS = arrayOf(
            BaseColumns._ID, AUTHOR, AUTHOR_ID, CONTENT, PUBLISHED,
            LIKED_BY_ME, LIKES, SHARES, VIEWS, VIDEO
        )
    }
}
