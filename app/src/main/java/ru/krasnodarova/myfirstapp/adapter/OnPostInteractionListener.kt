package ru.krasnodarova.myfirstapp.adapter
import ru.krasnodarova.myfirstapp.dto.Post
interface OnPostInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onAvatarClick(post: Post) {}
}
